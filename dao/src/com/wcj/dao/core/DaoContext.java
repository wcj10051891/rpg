package com.wcj.dao.core;

import javax.sql.DataSource;

import com.wcj.dao.core.datasource.BoneCPDataSourceProvider;
import com.wcj.dao.core.datasource.DataSourceProvider;
import com.wcj.util.Config;

public class DaoContext {
	public static final String DataSourceProviderClass = "dataSourceProviderClass";
	
	public TransactionalQueryRunner jdbc;
	public DaoFactory daoFactory;

	public DaoContext(DataSource dataSource) {
		this.init(dataSource);
	}
	public DaoContext() {
		this.init(initDataSource());
	}
	
	private void init(DataSource dataSource) {
		boolean defaultAutoCommit = false;
		try {
			defaultAutoCommit = dataSource.getConnection().getAutoCommit();
		} catch (Exception e) {
		}
		jdbc = new TransactionalQueryRunner(dataSource, defaultAutoCommit);
		daoFactory = new DaoFactory(jdbc);
	}

	private DataSource initDataSource() {
		Config cfg = new Config("jdbc.properties");
		DataSourceProvider provider = null;
		try {
			provider = (DataSourceProvider)Class.forName(cfg.getString(DataSourceProviderClass)).getConstructor(Config.class).newInstance(cfg);
		} catch (Exception e) {
			provider = new BoneCPDataSourceProvider(cfg);
		}
		return provider.getDataSource();
	}
}
