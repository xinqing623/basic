package com.xinqing.util;

import java.util.List;

import com.xinqing.entity.TableColumn;

public class SqlServerDialect extends DataBaseDailect {
	
	static{
//		regist("sqlserver", SqlServerDialect.class);
		System.out.println("11122rrr");
	}

	@Override
	public String selectTables(String dbName) {
		return "Select Name FROM " + dbName + "..SysObjects Where XType='U' order by Name";
	}

	@Override
	public String selectTableColumns(String dbName, String tableName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TableColumn getPkColumn(List<TableColumn> columns) {
		// TODO Auto-generated method stub
		return null;
	}

}
