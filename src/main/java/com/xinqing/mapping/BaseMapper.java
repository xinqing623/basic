package com.xinqing.mapping;

import java.util.List;
import java.util.Map;

import com.xinqing.entity.SysProject;
import com.xinqing.entity.UserLog;

public interface BaseMapper<T> {
	

	public T selectByPK(String id);

	public int insert(T entity);
	
	public int update(T entity);
	
	public int delete(String id);
	
	public List<T> select(Map map);

}
