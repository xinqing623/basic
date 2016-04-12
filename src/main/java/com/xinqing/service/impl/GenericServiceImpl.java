package com.xinqing.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xinqing.mapping.BaseMapper;
import com.xinqing.service.GenericService;
import com.xinqing.vo.PageInfo;
import com.xinqing.vo.Pager;

public abstract class GenericServiceImpl<T> implements GenericService<T> {
	
	protected abstract BaseMapper<T> getMapper();

	@Override
	public T get(String id) {
		return getMapper().selectByPK(id);
	}

	@Override
	public List<T> findAll() {
		return getMapper().select(null);
	}

	@Override
	public int save(T entity) {
		return getMapper().insert(entity);
	}

	@Override
	public int update(T entity) {
		return getMapper().update(entity);
	}

	@Override
	public int delete(String[] ids) {
		for(String id : ids){
			getMapper().delete(id);
		}
		return ids.length;
	}

	@Override
	public Pager<T> findPage(PageInfo pageInfo, Map<String, Object> params) {
		Page<T> page = PageHelper.startPage(pageInfo.getPage(), pageInfo.getRows());
		if(pageInfo.getSort() != null){
			page.setOrderBy(pageInfo.getSort() + " " + pageInfo.getOrder());
		}
		getMapper().select(params);
		Pager<T> pager = new Pager<T>(pageInfo, (int)(page.getTotal()), page.getResult());
		return pager;
	}

	@Override
	public List<T> findList(Map<String, Object> params) {
		return getMapper().select(params);
	}

	@Override
	public List<T> findList(Map<String, Object> params, String orderName, String orderDirect) {
		Page<T> page = PageHelper.startPage(1, 1000000);
		if(orderName != null){
			page.setOrderBy(orderName + " " + orderDirect);
		}
		getMapper().select(params);
		return page.getResult();
	}

	@Override
	public List<T> findByField(String fieldName, String value) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(fieldName, value);
		return getMapper().select(params);
	}
	

}
