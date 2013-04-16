package com.wcj.util;

public abstract class PageUtils {
	public static String getTotalSql(String sql){
		return "select count(*) from (" + sql + ") _temp_count_all_";
	}
}
