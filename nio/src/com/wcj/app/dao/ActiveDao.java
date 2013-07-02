package com.wcj.app.dao;

import java.util.List;

import com.wcj.dao.annotation.Dao;
import com.wcj.dao.annotation.Sql;
import com.wcj.dao.test.Active;

@Dao
public interface ActiveDao {
	@Sql("select * from active")
	List<Active> getAll();
}
