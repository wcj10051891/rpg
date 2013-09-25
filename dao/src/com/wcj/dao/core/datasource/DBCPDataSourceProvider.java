//package com.wcj.dao.core.datasource;
//
//import java.sql.SQLException;
//import java.util.Properties;
//
//import javax.sql.DataSource;
//
//import org.apache.commons.dbcp.BasicDataSource;
//
//import com.wcj.dao.core.DaoException;
//import com.wcj.util.Config;
//
//public class DBCPDataSourceProvider implements DataSourceProvider {
//	private Config config;
//	public static final String driverClassName = "dbcp.driverClassName";
//	public static final String url = "dbcp.url";
//	public static final String username = "dbcp.username";
//	public static final String password = "dbcp.password";
//	public static final String defaultAutoCommit = "dbcp.defaultAutoCommit";
//	public static final String defalutReadOnly = "dbcp.defalutReadOnly";
//	public static final String defaultTransactionIsolation = "dbcp.defaultTransactionIsolation";
//	public static final String defaultCatalog = "dbcp.defaultCatalog";
//	public static final String initialSize = "dbcp.initialSize";
//	public static final String maxActive = "dbcp.maxActive";
//	public static final String minIdle = "dbcp.minIdle";
//	public static final String maxIdle = "dbcp.maxIdle";
//	public static final String maxWait = "dbcp.maxWait";
//	public static final String validationQuery = "dbcp.validationQuery";
//	public static final String testOnBorrow = "dbcp.testOnBorrow";
//	public static final String testOnReturn = "dbcp.testOnReturn";
//	public static final String testWhileIdle = "dbcp.testWhileIdle";
//	public static final String timeBetweenEvictionRunsMillis = "dbcp.timeBetweenEvictionRunsMillis";
//	public static final String numTestsPerEvictionRun = "dbcp.numTestsPerEvictionRun";
//	public static final String accessToUnderlyingConnectionAllowed = "dbcp.accessToUnderlyingConnectionAllowed";
//	public static final String loginTimeout = "dbcp.loginTimeout";
//	public static final String maxOpenPreparedStatements = "dbcp.maxOpenPreparedStatements";
//	public static final String minEvictableIdleTimeMillis = "dbcp.minEvictableIdleTimeMillis";
//	public static final String poolPreparedStatements = "dbcp.poolPreparedStatements";
//	
//	public DBCPDataSourceProvider(Config config) {
//		this.config = config;
//	}
//
//	@Override
//	public DataSource getDataSource() {
//		BasicDataSource dataSource = new BasicDataSource();
//		Properties properties = config.getProperties();
//		dataSource.setDriverClassName(config.getString(driverClassName));
//		dataSource.setUrl(config.getString(url));
//		dataSource.setUsername(config.getString(username));
//		dataSource.setPassword(config.getString(password));
//		
//		if(properties.containsKey(defaultAutoCommit))
//			dataSource.setDefaultAutoCommit(config.getBoolean(defaultAutoCommit));
//		if(properties.containsKey(defalutReadOnly))
//			dataSource.setDefaultReadOnly(config.getBoolean(defalutReadOnly));
//		if(properties.containsKey(defaultTransactionIsolation))
//			dataSource.setDefaultTransactionIsolation(config.getInt(defaultTransactionIsolation));
//		if(properties.containsKey(defaultCatalog))
//			dataSource.setDefaultCatalog(config.getString(defaultCatalog));
//		if(properties.containsKey(initialSize))
//			dataSource.setInitialSize(config.getInt(initialSize));
//		if(properties.containsKey(maxActive))
//			dataSource.setMaxActive(config.getInt(maxActive));
//		if(properties.containsKey(minIdle))
//			dataSource.setMinIdle(config.getInt(minIdle));
//		if(properties.containsKey(maxIdle))
//			dataSource.setMaxIdle(config.getInt(maxIdle));
//		if(properties.containsKey(maxWait))
//			dataSource.setMaxWait(config.getLong(maxWait));
//		if(properties.containsKey(validationQuery))
//			dataSource.setValidationQuery(config.getString(validationQuery));
//		if(properties.containsKey(testOnBorrow))
//			dataSource.setTestOnBorrow(config.getBoolean(testOnBorrow));
//		if(properties.containsKey(testOnReturn))
//			dataSource.setTestOnReturn(config.getBoolean(testOnReturn));
//		if(properties.containsKey(testWhileIdle))
//			dataSource.setTestWhileIdle(config.getBoolean(testWhileIdle));
//		if(properties.containsKey(timeBetweenEvictionRunsMillis))
//			dataSource.setTimeBetweenEvictionRunsMillis(config.getLong(timeBetweenEvictionRunsMillis));
//		if(properties.containsKey(numTestsPerEvictionRun))
//			dataSource.setNumTestsPerEvictionRun(config.getInt(numTestsPerEvictionRun));
//		if(properties.containsKey(accessToUnderlyingConnectionAllowed))
//			dataSource.setAccessToUnderlyingConnectionAllowed(config.getBoolean(accessToUnderlyingConnectionAllowed));
//		try {
//			if(properties.containsKey(loginTimeout))
//				dataSource.setLoginTimeout(config.getInt(loginTimeout));
//		} catch (SQLException e) {
//			throw new DaoException("DBCP dataSource init error.", e);
//		}
//		if(properties.containsKey(maxOpenPreparedStatements))
//			dataSource.setMaxOpenPreparedStatements(config.getInt(maxOpenPreparedStatements));
//		if(properties.containsKey(minEvictableIdleTimeMillis))
//			dataSource.setMinEvictableIdleTimeMillis(config.getLong(minEvictableIdleTimeMillis));
//		if(properties.containsKey(poolPreparedStatements))
//			dataSource.setPoolPreparedStatements(config.getBoolean(poolPreparedStatements));
//		return dataSource;
//	}
//
//}
