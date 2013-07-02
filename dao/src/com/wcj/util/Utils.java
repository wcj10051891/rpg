package com.wcj.util;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.wcj.gen.ColumnMetaData;
import com.wcj.gen.TableMetaData;

public abstract class Utils {
	
	private static Map<String, TableMetaData> TableInfoCache = new LinkedHashMap<String, TableMetaData>();
	
	public static Map<String, TableMetaData> getTableInfos(){
		if(!TableInfoCache.isEmpty()){
			return TableInfoCache;
		}
		Config cfg = new Config("daoGen.cfg");
		String url = cfg.getString("metadata.infoschema.url");
		String username = cfg.getString("metadata.username");
		String password = cfg.getString("metadata.password");
		String tableSchema = cfg.getString("metadata.target.tableschema");

		Connection conn = null;

		String tableSql = "SELECT table_name, table_comment FROM TABLES WHERE TABLE_SCHEMA='"
				+ tableSchema +"'";

		String columnSql = "SELECT table_name, column_name, column_comment, IS_NULLABLE, "
				+ "DATA_TYPE, CHARACTER_MAXIMUM_LENGTH, column_default FROM COLUMNS WHERE TABLE_SCHEMA='"+tableSchema+"'";

		try {
		    Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, username, password);
			if(conn == null)
				throw new RuntimeException("Connection can not null.");

			PreparedStatement ps = conn.prepareStatement(tableSql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TableMetaData tmd = new TableMetaData();
				tmd.setTableComment(rs.getString(2));
				tmd.setTableName(rs.getString(1));
				TableInfoCache.put(rs.getString(1), tmd);
			}
			rs.close();

			rs = ps.executeQuery(columnSql);

			while (rs.next()) {
				ColumnMetaData cmd = new ColumnMetaData();
				String tableName = rs.getString(1);
				cmd.setTableName(tableName);
				cmd.setColumnName(rs.getString(2));
				cmd.setColumnComment(rs.getString(3));
				cmd.setNullable(rs.getString(4));
				cmd.setColumnType(rs.getString(5));
				cmd.setColumnSize(rs.wasNull() ? Integer.MAX_VALUE : rs
						.getInt((6)));
				cmd.setColumnDefault(rs.getString(7));

				TableMetaData tmd = TableInfoCache.get(tableName);
				if (tmd != null) {
					tmd.addColumns(cmd);
				}
			}

			ps.close();

			return TableInfoCache;

		} catch (Exception e) {
			e.printStackTrace();
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		}
		return null;
	}
	
	public static String firstUpperCase(String arg){
		char first = arg.charAt(0);
		return arg.replaceFirst(String.valueOf(first), String.valueOf(Character.toUpperCase(first)));
	}
	
	public static String firstLowerCase(String arg){
		char first = arg.charAt(0);
		return arg.replaceFirst(String.valueOf(first), String.valueOf(Character.toLowerCase(first)));
	}
	
	public static void writeFile(File o, String content){
		if(StringUtils.hasText(content)){
			try {
				FileOutputStream fout = new FileOutputStream(o);
				fout.write(content.getBytes());
				fout.flush();
				fout.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String genericMark(int size){
		if(size <= 0)
			return "";
		List<String> str = new ArrayList<String>();
		for(int i=0;i<size;i++){
			str.add("?");
		}
		return StringUtils.join(str, ",");
	}
	
	
	public static void main(String[] args){
	    Map<String, TableMetaData> m = getTableInfos();
        List<String> ss = new ArrayList<String>();
	    for(ColumnMetaData cm : m.get("item_wearable").getColumns())
	    {
	        ss.add(cm.getColumnName() + ":\"" + cm.getColumnComment() + "\",");
	    }
	    System.out.println(StringUtils.join(ss, "\n"));
	}
}
