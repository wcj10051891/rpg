package com.wcj.dao.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
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
import com.wcj.dao.annotation.Sql;
import com.wcj.util.BeanUtils;
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

	private Map<Class<?>, Object> proxyCache = new ConcurrentHashMap<Class<?>, Object>();
	private Map<Method, QueryMethod> queryMethodCache = new ConcurrentHashMap<Method, QueryMethod>();
	private QueryRunner jdbc;
	
	public DaoFactory(QueryRunner queryRunner) {
		this.jdbc = queryRunner;
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
					}
			}
			local.remove();
//			logger.debug(sql);
			if (sql.startsWith(select)) {
				if (queryMethodCache.containsKey(method))
					return queryMethodCache.get(method).execute(sql);
				
				Type returnType = method.getGenericReturnType();
				if (returnType instanceof Class<?>) {
					Class<?> c = (Class<?>) returnType;
					// primitive
					if (buildInTypes.containsKey(c)) {
						queryMethodCache.put(method, new QueryMethod(jdbc, scalarHandler));
					} else if (Map.class.isAssignableFrom(c))
						// map<String, Object>
						queryMethodCache.put(method, new QueryMethod(jdbc, mapHandler));
					else if (Collection.class.isAssignableFrom(c))
						// List<Map<String, Object>
						queryMethodCache.put(method, new QueryMethod(jdbc, mapListHandler));
					else
						// Bean
						queryMethodCache.put(method, new QueryMethod(jdbc, getBeanHandler(c)));
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
										queryMethodCache.put(method, new QueryMethod(jdbc, mapListHandler));
								}
							} else {
								Class<?> tt = (Class<?>) t;
								if (tt == Map.class)
									// List<Map<String, Object>
									queryMethodCache.put(method, new QueryMethod(jdbc, mapListHandler));
								else if (buildInTypes.containsKey(tt))
									// primitive list
									queryMethodCache.put(method, new QueryMethod(jdbc, columnListHandler));
								else
									// bean list
									queryMethodCache.put(method, new QueryMethod(jdbc, getBeanListHandler(tt)));
							}
						}
					} else if (rawType == Map.class) {
						// map<String, Object>
						queryMethodCache.put(method, new QueryMethod(jdbc, mapHandler));
					} else {
						// Bean
						queryMethodCache.put(method, new QueryMethod(jdbc, getBeanHandler(rawType)));
					}
				}
				if (queryMethodCache.containsKey(method))
					return queryMethodCache.get(method).execute(sql);
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
