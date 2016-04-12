package com.xinqing.util;

import java.util.List;

import com.xinqing.entity.TableColumn;

public class OracleDialect extends DataBaseDailect {
	
	static{
//		regist("oracle", OracleDialect.class);
		System.out.println("11122");
	}

	@Override
	public String selectTables(String dbName) {
		return "SELECT * FROM USER_TABLES";
	}

	@Override
	public String selectTableColumns(String dbName, String tableName) {
		return null;
	}

	@Override
	public TableColumn getPkColumn(List<TableColumn> columns) {
		return null;
	}

}
