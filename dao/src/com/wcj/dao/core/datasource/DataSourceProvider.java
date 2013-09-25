package com.wcj.dao.core.datasource;

import javax.sql.DataSource;

public interface DataSourceProvider {
	DataSource getDataSource();
}
