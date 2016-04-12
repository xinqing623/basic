package com.xinqing.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.xinqing.entity.StMenu;
import com.xinqing.vo.PageInfo;

public interface StMenuService extends GenericService<StMenu> {
	
	public Page<StMenu> findPage1(PageInfo pageInfo,Map<String, Object> params);

	public List<StMenu> findUserMenus(String userId);
	
}
