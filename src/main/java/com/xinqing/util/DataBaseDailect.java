package com.xinqing.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xinqing.entity.TableColumn;

public abstract class DataBaseDailect {
	
	
	private static Map<String, Class<? extends DataBaseDailect>> map = new HashMap<String, Class<? extends DataBaseDailect>>();	
	
	public abstract String selectTables(String dbName);
	
	public abstract String selectTableColumns(String dbName, String tableName);
	
	public abstract TableColumn getPkColumn(List<TableColumn> columns);	
	
}
