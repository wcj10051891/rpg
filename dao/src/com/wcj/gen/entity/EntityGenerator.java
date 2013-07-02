package com.wcj.gen.entity;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.wcj.gen.ColumnMetaData;
import com.wcj.gen.TableMetaData;
import com.wcj.gen.VelocityRuner;
import com.wcj.util.Utils;

/**
 * @author chengjie.wang
 *
 */
public class EntityGenerator {
	
	private static final String mark = "_";
	
	private String tplPath;
	private TableMetaData tableData;
	private String packageName;
	private String outputPath;
	
	public EntityGenerator(String tplPath, TableMetaData tableData, String packageName, String outputPath){
		this.packageName = packageName;
		this.tplPath = tplPath;
		this.tableData = tableData;
		this.outputPath = outputPath;
	}

	/*
	 * input var:
		columns	-> list<columnMetaData>
		packageName
		tableName
		tableComment
	*/

	public String[] generate() throws Exception{
        if (tableData == null)
            throw new Exception("generate table data can not null.");
		Map<String, Object> ctx = new HashMap<String, Object>();
		ctx.put("packageName", packageName);
		
		String tableName = tableData.getTableName();	
		String capTableName = Utils.firstUpperCase(tableName);
		ctx.put("tableName", capTableName);
		
		String entityName = tableNameProcess(capTableName);
		ctx.put("entityName", entityName);
		
		String tableComment = tableData.getTableComment();		
		ctx.put("tableComment", tableComment.replaceAll("InnoDB free.*", ""));

		ctx.put("columns", tableData.getColumns());
		
		Map<String, String> columnData = new HashMap<String, String>();
		
		Iterator<ColumnMetaData> it = tableData.getColumns().iterator();
		
		while(it.hasNext()){
			ColumnMetaData en = it.next();
			String columnName = en.getColumnName();
			columnData.put(columnName, Utils.firstUpperCase(columnName));
		}
		
		ctx.put("columnData", columnData);

		File o = new File(outputPath, entityName +".java");
		
		VelocityRuner.run(ctx, tplPath, o.getAbsolutePath());
		
		String clazz = packageName + "." + entityName;
		
		return new String[]{clazz, tableName};
	}
	
	private String tableNameProcess(String tableName){
		String[] strs = tableName.split(mark);
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<strs.length;i++){
			sb.append(Utils.firstUpperCase(strs[i]));
		}
		return sb.toString();
	}
}
