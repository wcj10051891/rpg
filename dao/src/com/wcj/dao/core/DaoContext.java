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
		Config cfg = new Config("jdbc.properties");
		BasicDataSource datasource = new BasicDataSource();
		datasource.setDriverClassName(cfg.getString("jdbc.driver"));
		datasource.setUrl(cfg.getString("jdbc.url"));
		datasource.setUsername(cfg.getString("jdbc.username"));
		datasource.setPassword(cfg.getString("jdbc.password"));
		return datasource;
	}
}
