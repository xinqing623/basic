package com.xinqing.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinqing.entity.DataBaseBean;
import com.xinqing.entity.TableColumn;
import com.xinqing.mapping.DatabaseBeanMapper;
import com.xinqing.service.DatabaseService;

@Service("databaseService")
@Transactional
public class DatabaseServiceImpl implements DatabaseService {
	
	@Autowired
	private DatabaseBeanMapper databaseBeanMapper;

	@Override
	public List<DataBaseBean> selectAllTables(String dbName) {
		return databaseBeanMapper.selectTables(dbName);
	}

	@Override
	public List<TableColumn> selectTableColumns(String tableName) {
		return databaseBeanMapper.selectTableColumns(tableName);
	}

}
