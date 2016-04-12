package com.xinqing.service;

import java.util.List;

import com.xinqing.entity.DataBaseBean;
import com.xinqing.entity.TableColumn;

public interface DatabaseService {
	
	List<DataBaseBean> selectAllTables(String dbName);

	List<TableColumn> selectTableColumns(String string);

}
