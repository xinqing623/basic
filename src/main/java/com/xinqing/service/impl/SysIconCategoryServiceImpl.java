package com.xinqing.service.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinqing.entity.SysIconCategory;
import com.xinqing.mapping.BaseMapper;
import com.xinqing.mapping.SysIconCategoryMapper;
import com.xinqing.service.SysIconCategoryService;

@Service("sysIconCategoryService")
@Transactional
public class SysIconCategoryServiceImpl extends GenericServiceImpl<SysIconCategory> implements SysIconCategoryService{
	
	private static Logger logger = LoggerFactory.getLogger(SysIconCategoryServiceImpl.class);
	
	@Autowired
	private SysIconCategoryMapper sysIconCategoryMapper;

	@Override
	protected BaseMapper<SysIconCategory> getMapper() {
		return sysIconCategoryMapper;
	}	

}
