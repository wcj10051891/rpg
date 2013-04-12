package com.wcj.dao.core;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;

import com.wcj.util.Config;

public class Context {
    private static Context ctx;

    private DataSource dataSource;
    public QueryRunner jdbc;
    public DaoFactory daoFactory;

    private Context() {
	dataSource = initDataSource();
	jdbc = new QueryRunner(dataSource);
	daoFactory = new DaoFactory();
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

    public static Context instance() {
	if(ctx == null)
	    ctx = new Context();
	return ctx;
    }
}
