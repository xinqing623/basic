package com.xinqing.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xinqing.entity.StMenu;
import com.xinqing.mapping.BaseMapper;
import com.xinqing.mapping.StMenuMapper;
import com.xinqing.service.StMenuService;
import com.xinqing.vo.PageInfo;
import com.xinqing.vo.Pager;

@Service("stMenuService")
@Transactional
public class StMenuServiceImpl extends GenericServiceImpl<StMenu> implements StMenuService{
	
	private static Logger logger = LoggerFactory.getLogger(StMenuServiceImpl.class);
	
	@Autowired
	private StMenuMapper stMenuMapper;
	
	@Override
	protected BaseMapper<StMenu> getMapper() {
		return stMenuMapper;
	}

	@Override
	public Page<StMenu> findPage1(PageInfo pageInfo, Map<String, Object> params) {
		Page<StMenu> page = PageHelper.startPage(pageInfo.getPage(), pageInfo.getRows());
		if(pageInfo.getSort() != null){
			page.setOrderBy(pageInfo.getSort() + " " + pageInfo.getOrder());
		}
		List<StMenu> list = stMenuMapper.select(params);		
		return page;
	}

	@Override
	public List<StMenu> findUserMenus(String userId) {
		return stMenuMapper.findUserMenus(userId);
	}


	

		

}
