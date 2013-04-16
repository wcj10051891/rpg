package com.wcj.dao.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wcj.dao.annotation.Arg;
import com.wcj.dao.annotation.Dao;
import com.wcj.dao.annotation.Page;
import com.wcj.dao.annotation.Sql;
import com.wcj.dao.core.page.PageResult;
import com.wcj.dao.core.page.dialect.Dialect;
import com.wcj.dao.core.page.dialect.MySqlDialect;
import com.wcj.dao.core.page.dialect.Oracle10gDialect;
import com.wcj.dao.core.page.dialect.Oracle8iDialect;
import com.wcj.dao.core.page.dialect.Oracle9iDialect;
import com.wcj.dao.core.page.dialect.SqlServerDialect;
import com.wcj.util.BeanUtils;
import com.wcj.util.PageUtils;
import com.wcj.util.StringUtils;

public class DaoFactory {
	private static Logger logger = LoggerFactory.getLogger(DaoFactory.class);
	private static final String comma = ",";
	private static final String argPrefix = "#";
	private static final String quote = "'";

	private static String dateFormat = "yyyy-MM-dd hh:mm:ss";

	private static final String select = "select";
	private static final String insert = "insert";
	private static final String delete = "delete";
	private static final String update = "update";

	private static Map<Class<?>, BeanHandler<?>> beanHandlers = new ConcurrentHashMap<>();
	private static Map<Class<?>, BeanListHandler<?>> beanListHandlers = new ConcurrentHashMap<>();
	private static MapHandler mapHandler = new MapHandler();
	private static ColumnListHandler<?> columnListHandler = new ColumnListHandler<>();
	private static MapListHandler mapListHandler = new MapListHandler();
	private static ScalarHandler<?> scalarHandler = new ScalarHandler<>();
	private static BasicRowProcessor basicRowProcessor = new BasicRowProcessor();
	

	private static final Map<Class<?>, Class<?>> buildInTypes = new HashMap<Class<?>, Class<?>>();
	static {
		buildInTypes.put(byte.class, Byte.class);
		buildInTypes.put(boolean.class, Boolean.class);
		buildInTypes.put(char.class, Character.class);
		buildInTypes.put(short.class, Short.class);
		buildInTypes.put(int.class, Integer.class);
		buildInTypes.put(long.class, Long.class);
		buildInTypes.put(float.class, Float.class);
		buildInTypes.put(double.class, Double.class);
		buildInTypes.put(Byte.class, Byte.class);
		buildInTypes.put(Boolean.class, Boolean.class);
		buildInTypes.put(Character.class, Character.class);
		buildInTypes.put(Short.class, Short.class);
		buildInTypes.put(Integer.class, Integer.class);
		buildInTypes.put(Long.class, Long.class);
		buildInTypes.put(Float.class, Float.class);
		buildInTypes.put(Double.class, Double.class);
		buildInTypes.put(String.class, String.class);
	}
	
	private static final Map<String, Dialect> dialects = new HashMap<String, Dialect>();
	static {
		dialects.put( "MySQL", new MySqlDialect());
		dialects.put( "Microsoft SQL Server", new SqlServerDialect());
		dialects.put( "Oracle8", new Oracle8iDialect());
		dialects.put( "Oracle9", new Oracle9iDialect());
		dialects.put( "Oracle10", new Oracle10gDialect());
	}

	private Map<Class<?>, Object> proxyCache = new ConcurrentHashMap<Class<?>, Object>();
	private QueryRunner jdbc;
	private Dialect currentDialect;
	
	public DaoFactory(QueryRunner queryRunner) {
		this.jdbc = queryRunner;
		try {
			Connection conn = this.jdbc.getDataSource().getConnection();
			try {
				DatabaseMetaData meta = conn.getMetaData();
				String databaseName = meta.getDatabaseProductName();
				currentDialect = dialects.get(databaseName);
				if(currentDialect == null){
					int databaseMajorVersion = 0;
					try {
						Method gdbmvMethod = DatabaseMetaData.class.getMethod("getDatabaseMajorVersion", null);
						databaseMajorVersion = ( (Integer) gdbmvMethod.invoke(meta, null) ).intValue();
					}
					catch (Exception nsme) {
					}
					currentDialect = dialects.get(databaseName + "" + databaseMajorVersion);
				}
				if(currentDialect == null)
					currentDialect = new Dialect();
			}
			finally {
				DbUtils.close(conn);
			}
		}
		catch (Exception e) {
			logger.error("fetch database info error.", e);
		}
	}

	private InvocationHandler handler = new InvocationHandler() {
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			if (!method.isAnnotationPresent(Sql.class))
				throw new DaoException("dao method must annotationed by Sql.");

			String sql = method.getAnnotation(Sql.class).value().trim();

			if (!(sql.startsWith(select) || sql.startsWith(insert) || sql.startsWith(delete) || sql.startsWith(update)))
				throw new DaoException("unsupport sql operation.");

			if (sql.startsWith(select) && method.getReturnType() == void.class)
				return null;

			Annotation[][] parameterAnnotations = method.getParameterAnnotations();
			Map<String, Object> param = null;
			PageResult pageResult = null;
			Class<?> pageReturnType = null;
			for (int i = 0; i < parameterAnnotations.length; i++) {
				Annotation[] anno = parameterAnnotations[i];
				if (anno.length > 0)
					if (anno[0].annotationType() == Arg.class) {
						if (param == null)
							param = new LinkedHashMap<String, Object>(parameterAnnotations.length);

						for (Entry<String, String> entry : convert(((Arg) anno[0]).value(), args[i]).entrySet()) {
							String key = entry.getKey();
							Matcher matcher = Pattern.compile(argPrefix + key).matcher(sql);
							StringBuffer sqlTemp = new StringBuffer();
							while (matcher.find())
								matcher.appendReplacement(sqlTemp, entry.getValue());

							matcher.appendTail(sqlTemp);
							sql = sqlTemp.toString();
						}
					}else if (anno[0].annotationType() == Page.class && args[i] instanceof PageResult) {
					    pageReturnType = ((Page) anno[0]).value();
					    pageResult = (PageResult)args[i];
					}
			}
			local.remove();
//			logger.debug(sql);
			if (sql.startsWith(select)) {
				//process page
				if(pageResult != null && PageResult.class.isAssignableFrom(method.getReturnType())){
					Long total = (Long) jdbc.query(PageUtils.getTotalSql(sql), scalarHandler);
					if(total == 0)
						return pageResult;
					
					pageResult.init(total.intValue());
					if(pageResult.getPageIndex() > pageResult.getPageCount()){
					    pageResult.setData(Collections.EMPTY_LIST);
					    return pageResult;
					}
					String limitSql = currentDialect.getLimitString(sql, pageResult.getStart(), pageResult.getPageSize());
					if(StringUtils.hasText(limitSql)){
						if (pageReturnType == Map.class)// List<Map<String, Object>
						    pageResult.setData((List) jdbc.query(limitSql, mapListHandler));
						else if (buildInTypes.containsKey(pageReturnType))// primitive list
						    pageResult.setData((List) jdbc.query(limitSql, columnListHandler));
						else// bean list
						    pageResult.setData((List) jdbc.query(limitSql, getBeanListHandler(pageReturnType)));
						return pageResult;
					}else{
					    final PageResult result = pageResult;
					    final Class<?> pageReturnTypeClass = pageReturnType;
					    return jdbc.query(sql, new ResultSetHandler<PageResult>(){

						@Override
						public PageResult handle(ResultSet rs) throws SQLException {
						    
						    if (rs.getType() != ResultSet.TYPE_FORWARD_ONLY) {
							rs.absolute(result.getStart() + 1);
						    } else {
							for (int i = 0; i < result.getStart(); i++) rs.next();
						    }
						    int limit = result.getPageSize();
						    List rows = new ArrayList(); 
						    for (int count = 0; rs.next() && count < limit; count++) {
							if (pageReturnTypeClass == Map.class)// List<Map<String, Object>
							    rows.add(basicRowProcessor.toMap(rs));
							else if (buildInTypes.containsKey(pageReturnTypeClass))// primitive list
							    rows.add(basicRowProcessor.toArray(rs)[0]);
							else// bean list
							    rows.add(basicRowProcessor.toBean(rs, pageReturnTypeClass));
						    }
						    result.setData(rows);
						    return result;
						}
					    });
					}
				}else{
					return query(sql, method.getGenericReturnType());
				}
			}else{
				Type returnType = method.getReturnType();
				Class<?> c = (Class<?>) returnType;
				if (c == void.class) {
					jdbc.update(sql);
				} else {
					if (c.isPrimitive() && buildInTypes.containsKey(c))
						c = buildInTypes.get(c);

					if (Number.class.isAssignableFrom(c)) {
						if (sql.startsWith(insert)) {
							Connection conn = jdbc.getDataSource().getConnection();
							PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
							try {
								stmt.executeUpdate();
								ResultSet keys = stmt.getGeneratedKeys();
								Integer result = keys.next() ? keys.getInt(1) : null;
								DbUtils.close(keys);
								return result;
							} catch (Exception e) {
								throw new DaoException("insert sql execute error, sql:" + sql, e);
							} finally {
								DbUtils.close(stmt);
								DbUtils.close(conn);
							}
						} else {
							return jdbc.update(sql);
						}
					} else {
						jdbc.update(sql);
					}
				}
			}
			return null;
		}
	};
	
	private Object query(String sql, Type returnType) throws Throwable{
		if (returnType instanceof Class<?>) {
			Class<?> c = (Class<?>) returnType;
			// primitive
			if (buildInTypes.containsKey(c)) {
				return jdbc.query(sql, scalarHandler);
			} else if (Map.class.isAssignableFrom(c))
				// map<String, Object>
				return jdbc.query(sql, mapHandler);
			else if (Collection.class.isAssignableFrom(c))
				// List<Map<String, Object>
				return jdbc.query(sql, mapListHandler);
			else
				// Bean
				return jdbc.query(sql, getBeanHandler(c));
		} else if (returnType instanceof ParameterizedType) {
			ParameterizedType paramType = (ParameterizedType) returnType;
			Class<?> rawType = (Class<?>) paramType.getRawType();
			if (Collection.class.isAssignableFrom(rawType)) {
				Type[] actualTypeArguments = paramType.getActualTypeArguments();
				if (actualTypeArguments.length == 1) {
					Type t = actualTypeArguments[0];
					if (t instanceof ParameterizedType) {
						ParameterizedType tt = (ParameterizedType) t;
						if ((Class<?>) tt.getRawType() == Map.class) {
							Type[] ttt = tt.getActualTypeArguments();
							if ((Class<?>) ttt[0] == String.class && (Class<?>) ttt[1] == Object.class)
								return jdbc.query(sql, mapListHandler);
						}
					} else if (t instanceof Class<?>) {
						Class<?> tt = (Class<?>) t;
						if (tt == Map.class)
							// List<Map<String, Object>
							return jdbc.query(sql, mapListHandler);
						else if (buildInTypes.containsKey(tt))
							// primitive list
							return jdbc.query(sql, columnListHandler);
						else
							// bean list
							return jdbc.query(sql, getBeanListHandler(tt));
					} else {
						return jdbc.query(sql, mapListHandler);
					}
				}
			} else if (rawType == Map.class) {
				// map<String, Object>
				return jdbc.query(sql, mapHandler);
			} else {
				// Bean
				return jdbc.query(sql, getBeanHandler(rawType));
			}
		}
		return null;
	}

	private static ThreadLocal<Set<Object>> local = new ThreadLocal<Set<Object>>();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Map<String, String> convert(String key, Object value) {
		if (key == null)
			return Collections.EMPTY_MAP;

		Map<String, String> result = new LinkedHashMap<String, String>();
		if (value == null) {
			result.put(key, "null");
			return result;
		} else if (value instanceof Number || value instanceof Boolean) {
			result.put(key, String.valueOf(value));
			return result;
		}
		if (value.getClass().isArray()) {
			int length = Array.getLength(value);
			Object[] o = new Object[length];
			for (int i = 0; i < length; i++)
				o[i] = Array.get(value, i);
			result.put(key, StringUtils.join(o, comma));
		} else if (value instanceof Collection) {
			result.put(key, StringUtils.join((Collection<?>) value, comma));
		} else if (value instanceof String) {
			result.put(key, quote + String.valueOf(value) + quote);
		} else if (value instanceof Date) {
			result.put(key, quote + new SimpleDateFormat(dateFormat) .format((Date) value) + quote);
		} else {
			checkDeadLoop(value);
			if (value instanceof Map) {
				for (Object en : ((Map) value).entrySet()) {
					Entry entry = (Entry) en;
					Object entryKey = entry.getKey();
					if (!(entryKey instanceof String))
						throw new DaoException("Map param key only support string type.");

					for (Entry<String, String> entry2 : convert(String.valueOf(entryKey), entry.getValue()).entrySet())
						result.put(key + "." + entry2.getKey(), entry2.getValue());
				}
			} else {
				result.putAll(convert(key, BeanUtils.toMap(value)));
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private static <T> BeanHandler<T> getBeanHandler(Class<T> beanClass) {
		if (beanHandlers.containsKey(beanClass))
			return (BeanHandler<T>) beanHandlers.get(beanClass);
		BeanHandler<T> handler = new BeanHandler<>(beanClass);
		beanHandlers.put(beanClass, handler);
		return handler;
	}

	@SuppressWarnings("unchecked")
	private static <T> BeanListHandler<T> getBeanListHandler(Class<T> beanClass) {
		if (beanListHandlers.containsKey(beanClass))
			return (BeanListHandler<T>) beanListHandlers.get(beanClass);
		BeanListHandler<T> handler = new BeanListHandler<>(beanClass);
		beanListHandlers.put(beanClass, handler);
		return handler;
	}

	private static void checkDeadLoop(Object value) {
		Set<Object> cls = local.get();
		if (cls == null) {
			cls = new HashSet<Object>();
			cls.add(value);
			local.set(cls);
		} else if (cls.contains(value)) {
			throw new DaoException("dead loop.");
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> daoClass) {
		if (!daoClass.isAnnotationPresent(Dao.class))
			throw new DaoException("daoClass must annotated by @Dao Annotation.");

		if (proxyCache.containsKey(daoClass))
			return (T) proxyCache.get(daoClass);

		Object proxy = Proxy.newProxyInstance(DaoFactory.class.getClassLoader(), new Class[] { daoClass }, handler);
		proxyCache.put(daoClass, proxy);
		return (T) proxy;
	}
}
