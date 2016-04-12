package com.xinqing.service.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinqing.entity.DicItems;
import com.xinqing.mapping.BaseMapper;
import com.xinqing.mapping.DicItemsMapper;
import com.xinqing.service.DicItemsService;

@Service("dicItemsService")
@Transactional
public class DicItemsServiceImpl extends GenericServiceImpl<DicItems> implements DicItemsService{
	
	private static Logger logger = LoggerFactory.getLogger(DicItemsServiceImpl.class);
	
	@Autowired
	private DicItemsMapper dicItemsMapper;

	@Override
	protected BaseMapper<DicItems> getMapper() {
		return dicItemsMapper;
	}

	

		

}
