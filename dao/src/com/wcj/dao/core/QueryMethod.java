package com.wcj.dao.core;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

public class QueryMethod {
	private QueryRunner runner;
	private ResultSetHandler<?> handler;
	public QueryMethod(QueryRunner runner, ResultSetHandler<?> handler) {
		this.runner = runner;
		this.handler = handler;
	}

	public Object execute(String sql) throws SQLException {
		return this.runner.query(sql, handler);
	}
}
