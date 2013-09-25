package com.wcj.dao.core.datasource;

import java.util.Properties;

import javax.sql.DataSource;

import com.jolbox.bonecp.BoneCPDataSource;
import com.wcj.dao.core.DaoException;
import com.wcj.util.Config;

public class BoneCPDataSourceProvider implements DataSourceProvider {
	public static final String driverClass = "bonecp.driverClass";
	public static final String jdbcUrl = "bonecp.jdbcUrl";
	public static final String username = "bonecp.username";
	public static final String password = "bonecp.password";
	public static final String partitionCount = "bonecp.partitionCount";
	public static final String minConnectionsPerPartition = "bonecp.minConnectionsPerPartition";
	public static final String maxConnectionsPerPartition = "bonecp.maxConnectionsPerPartition";
	public static final String acquireIncrement = "bonecp.acquireIncrement";
	public static final String acquireRetryAttempts = "bonecp.acquireRetryAttempts";
	public static final String acquireRetryDelayInMs = "bonecp.acquireRetryDelayInMs";
	public static final String closeConnectionWatch = "bonecp.closeConnectionWatch";
	public static final String closeConnectionWatchTimeoutInMs = "bonecp.closeConnectionWatchTimeoutInMs";
	public static final String connectionTestStatement = "bonecp.connectionTestStatement";
	public static final String connectionTimeoutInMs = "bonecp.connectionTimeoutInMs";
	public static final String defaultAutoCommit = "bonecp.defaultAutoCommit";
	public static final String defaultCatalog = "bonecp.defaultCatalog";
	public static final String defaultReadOnly = "bonecp.defaultReadOnly";
	public static final String defaultTransactionIsolation = "bonecp.defaultTransactionIsolation";
	public static final String disableConnectionTracking = "bonecp.disableConnectionTracking";
	public static final String disableJMX = "bonecp.disableJMX";
	public static final String externalAuth = "bonecp.externalAuth";
	public static final String idleConnectionTestPeriodInMinutes = "bonecp.idleConnectionTestPeriodInMinutes";
	public static final String idleConnectionTestPeriodInSeconds = "bonecp.idleConnectionTestPeriodInSeconds";
	public static final String idleMaxAgeInMinutes = "bonecp.idleMaxAgeInMinutes";
	public static final String idleMaxAgeInSeconds = "bonecp.idleMaxAgeInSeconds";
	public static final String initSQL = "bonecp.initSQL";
	public static final String lazyInit = "bonecp.lazyInit";
	public static final String logStatementsEnabled = "bonecp.logStatementsEnabled";
	public static final String maxConnectionAgeInSeconds = "bonecp.maxConnectionAgeInSeconds";
	public static final String poolAvailabilityThreshold = "bonecp.poolAvailabilityThreshold";
	public static final String poolName = "bonecp.poolName";
	public static final String queryExecuteTimeLimitInMs = "bonecp.queryExecuteTimeLimitInMs";
	public static final String releaseHelperThreads = "bonecp.releaseHelperThreads";
	public static final String serviceOrder = "bonecp.serviceOrder";
	public static final String statementReleaseHelperThreads = "bonecp.statementReleaseHelperThreads";
	public static final String statementsCacheSize = "bonecp.statementsCacheSize";
	public static final String statisticsEnabled = "bonecp.statisticsEnabled";
	public static final String transactionRecoveryEnabled = "bonecp.transactionRecoveryEnabled";

	private Config config;
	public BoneCPDataSourceProvider(Config config) {
		this.config = config;
	}
	@Override
	public DataSource getDataSource() {
		try {
			BoneCPDataSource dataSource = new BoneCPDataSource();
			dataSource.setJdbcUrl(config.getString(jdbcUrl));
			dataSource.setUsername(config.getString(username));
			dataSource.setPassword(config.getString(password));
			dataSource.setDriverClass(config.getString(driverClass));
			
			Properties properties = config.getProperties();
			if(properties.containsKey(partitionCount))
				dataSource.setPartitionCount(config.getInt(partitionCount));
			if(properties.containsKey(minConnectionsPerPartition))
				dataSource.setMinConnectionsPerPartition(config.getInt(minConnectionsPerPartition));
			if(properties.containsKey(maxConnectionsPerPartition))
				dataSource.setMaxConnectionsPerPartition(config.getInt(maxConnectionsPerPartition));
			if(properties.containsKey(acquireIncrement))
				dataSource.setAcquireIncrement(config.getInt(acquireIncrement));
			if(properties.containsKey(acquireRetryAttempts))
				dataSource.setAcquireRetryAttempts(config.getInt(acquireRetryAttempts));
			if(properties.containsKey(acquireRetryDelayInMs))
				dataSource.setAcquireRetryDelayInMs(config.getLong(acquireRetryDelayInMs));
			if(properties.containsKey(closeConnectionWatch))
				dataSource.setCloseConnectionWatch(config.getBoolean(closeConnectionWatch));
			if(properties.containsKey(closeConnectionWatchTimeoutInMs))
				dataSource.setCloseConnectionWatchTimeoutInMs(config.getLong(closeConnectionWatchTimeoutInMs));
			if(properties.containsKey(connectionTestStatement))
				dataSource.setConnectionTestStatement(config.getString(connectionTestStatement));
			if(properties.containsKey(connectionTimeoutInMs))
				dataSource.setConnectionTimeoutInMs(config.getLong(connectionTimeoutInMs));
			if(properties.containsKey(defaultAutoCommit))
				dataSource.setDefaultAutoCommit(config.getBoolean(defaultAutoCommit));
			if(properties.containsKey(defaultCatalog))
				dataSource.setDefaultCatalog(config.getString(defaultCatalog));
			if(properties.containsKey(defaultReadOnly))
				dataSource.setDefaultReadOnly(config.getBoolean(defaultReadOnly));
			if(properties.containsKey(defaultTransactionIsolation))
				dataSource.setDefaultTransactionIsolation(config.getString(defaultTransactionIsolation));
			if(properties.containsKey(disableConnectionTracking))
				dataSource.setDisableConnectionTracking(config.getBoolean(disableConnectionTracking));
			if(properties.containsKey(disableJMX))
				dataSource.setDisableJMX(config.getBoolean(disableJMX));
			if(properties.containsKey(externalAuth))
				dataSource.setExternalAuth(config.getBoolean(externalAuth));
			if(properties.containsKey(idleConnectionTestPeriodInMinutes))
				dataSource.setIdleConnectionTestPeriodInMinutes(config.getLong(idleConnectionTestPeriodInMinutes));
			if(properties.containsKey(idleConnectionTestPeriodInSeconds))
				dataSource.setIdleConnectionTestPeriodInSeconds(config.getLong(idleConnectionTestPeriodInSeconds));
			if(properties.containsKey(idleMaxAgeInMinutes))
				dataSource.setIdleMaxAgeInMinutes(config.getLong(idleMaxAgeInMinutes));
			if(properties.containsKey(idleMaxAgeInSeconds))
				dataSource.setIdleMaxAgeInSeconds(config.getLong(idleMaxAgeInSeconds));
			if(properties.containsKey(initSQL))
				dataSource.setInitSQL(config.getString(initSQL));
			if(properties.containsKey(lazyInit))
				dataSource.setLazyInit(config.getBoolean(lazyInit));
			if(properties.containsKey(logStatementsEnabled))
				dataSource.setLogStatementsEnabled(config.getBoolean(logStatementsEnabled));
			if(properties.containsKey(maxConnectionAgeInSeconds))
				dataSource.setMaxConnectionAgeInSeconds(config.getLong(maxConnectionAgeInSeconds));
			if(properties.containsKey(poolAvailabilityThreshold))
				dataSource.setPoolAvailabilityThreshold(config.getInt(poolAvailabilityThreshold));
			if(properties.containsKey(poolName))
				dataSource.setPoolName(config.getString(poolName));
			if(properties.containsKey(queryExecuteTimeLimitInMs))
				dataSource.setQueryExecuteTimeLimitInMs(config.getLong(queryExecuteTimeLimitInMs));
			if(properties.containsKey(releaseHelperThreads))
				dataSource.setReleaseHelperThreads(config.getInt(releaseHelperThreads));
			if(properties.containsKey(serviceOrder))
				dataSource.setServiceOrder(config.getString(serviceOrder));
			if(properties.containsKey(statementReleaseHelperThreads))
				dataSource.setStatementReleaseHelperThreads(config.getInt(statementReleaseHelperThreads));
			if(properties.containsKey(statementsCacheSize))
				dataSource.setStatementsCacheSize(config.getInt(statementsCacheSize));
			if(properties.containsKey(statisticsEnabled))
				dataSource.setStatisticsEnabled(config.getBoolean(statisticsEnabled));
			if(properties.containsKey(transactionRecoveryEnabled))
				dataSource.setTransactionRecoveryEnabled(config.getBoolean(transactionRecoveryEnabled));
			return dataSource;
		} catch (Exception e) {
			throw new DaoException("boneCP dataSource init error.", e);
		}
	}

}
