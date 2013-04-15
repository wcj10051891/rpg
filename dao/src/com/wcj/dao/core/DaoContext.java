package com.wcj.dao.core;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;

import com.wcj.util.Config;

public class DaoContext {
	public QueryRunner jdbc;
	public DaoFactory daoFactory;

	public DaoContext(DataSource dataSource) {
		jdbc = new QueryRunner(dataSource);
		daoFactory = new DaoFactory(jdbc);
	}
	public DaoContext() {
		jdbc = new QueryRunner(initDataSource());
		daoFactory = new DaoFactory(jdbc);
	}

	private DataSource initDataSource() {
		Config jdbcCfg = new Config("jdbc.properties");
		BasicDataSource datasource = new BasicDataSource();
		datasource.setDriverClassName(jdbcCfg.getString("jdbc.driver"));
		datasource.setUrl(jdbcCfg.getString("jdbc.url"));
		datasource.setUsername(jdbcCfg.getString("jdbc.username"));
		datasource.setPassword(jdbcCfg.getString("jdbc.password"));
		return datasource;
	}
}
