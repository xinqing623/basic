package com.xinqing.service.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinqing.entity.DicCategory;
import com.xinqing.mapping.BaseMapper;
import com.xinqing.mapping.DicCategoryMapper;
import com.xinqing.service.DicCategoryService;

@Service("dicCategoryService")
@Transactional
public class DicCategoryServiceImpl extends GenericServiceImpl<DicCategory> implements DicCategoryService{
	
	private static Logger logger = LoggerFactory.getLogger(DicCategoryServiceImpl.class);
	
	@Autowired
	private DicCategoryMapper dicCategoryMapper;

	@Override
	protected BaseMapper<DicCategory> getMapper() {
		return dicCategoryMapper;
	}


		

}
