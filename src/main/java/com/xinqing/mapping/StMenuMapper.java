package com.xinqing.mapping;

import java.util.List;

import com.xinqing.entity.StMenu;

public interface StMenuMapper extends BaseMapper<StMenu>{
	
	public StMenu selectByPK(String id);

	public int insert(StMenu entity);
	
	public int update(StMenu entity);
	
	public int delete(String id);

	public List<StMenu> findUserMenus(String userId);
	
}
