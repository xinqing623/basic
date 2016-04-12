package com.xinqing.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.xinqing.entity.SysConfig;
import com.xinqing.vo.PageInfo;
import com.xinqing.vo.Pager;

public interface GenericService<T> {

	public T get(String id);

	public List<T> findAll();

	public int save(T entity);

	public int update(T entity);
	
	public int delete(String[] ids);

	public Pager<T> findPage(PageInfo pageInfo,Map<String, Object> params);

	public List<T> findList(Map<String, Object> params);	
	
	public List<T> findList(Map<String, Object> params, String orderName, String orderDirect);	
	
	public List<T> findByField(String fieldName, String value);
	
}
