package com.xinqing.mapping;

import java.util.List;

import com.xinqing.entity.DataBaseBean;
import com.xinqing.entity.TableColumn;

public interface DatabaseBeanMapper extends BaseMapper<DataBaseBean> {

	
	List<DataBaseBean> selectTables(String dbName);
	
	List<TableColumn> selectTableColumns(String tableName);

}
