package com.xinqing.util;

import java.util.List;
import java.util.Map;

import com.sun.swing.internal.plaf.metal.resources.metal_zh_TW;
import com.xinqing.entity.TableColumn;

public class MySqlDialect extends DataBaseDailect{
	

	public String selectTables(String dbName){
		return "select table_name from information_schema.tables where table_schema='" + dbName + "'";
		
	}
	public String selectTableColumns(String dbName, String tableName){
		return "SELECT TABLE_NAME,COLUMN_NAME,DATA_TYPE,COLUMN_COMMENT,COLUMN_KEY FROM information_schema.columns WHERE table_name = '"
				+ tableName + "' and TABLE_SCHEMA='" + dbName + "'";
	}
	
	public TableColumn getPkColumn(List<TableColumn> columns) {
		for (TableColumn column : columns) {
			if ("PRI".equalsIgnoreCase(column.getKeyType())) {
				return column;
			}
		}
		return null;
	}

}
