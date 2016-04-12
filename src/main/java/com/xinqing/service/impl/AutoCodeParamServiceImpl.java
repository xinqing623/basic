package com.xinqing.service.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinqing.entity.AutoCodeParam;
import com.xinqing.mapping.AutoCodeParamMapper;
import com.xinqing.mapping.BaseMapper;
import com.xinqing.service.AutoCodeParamService;

@Service("autoCodeParamService")
@Transactional
public class AutoCodeParamServiceImpl extends GenericServiceImpl<AutoCodeParam> implements AutoCodeParamService{
	
	private static Logger logger = LoggerFactory.getLogger(AutoCodeParamServiceImpl.class);
	
	@Autowired
	private AutoCodeParamMapper autoCodeParamMapper;

	@Override
	protected BaseMapper<AutoCodeParam> getMapper() {
		return autoCodeParamMapper;
	}	

}
