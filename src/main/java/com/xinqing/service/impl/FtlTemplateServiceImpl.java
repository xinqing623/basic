package com.xinqing.service.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinqing.entity.FtlTemplate;
import com.xinqing.mapping.BaseMapper;
import com.xinqing.mapping.FtlTemplateMapper;
import com.xinqing.service.FtlTemplateService;

@Service("ftlTemplateService")
@Transactional
public class FtlTemplateServiceImpl extends GenericServiceImpl<FtlTemplate> implements FtlTemplateService{
	
	private static Logger logger = LoggerFactory.getLogger(FtlTemplateServiceImpl.class);
	
	@Autowired
	private FtlTemplateMapper ftlTemplateMapper;

	@Override
	protected BaseMapper<FtlTemplate> getMapper() {
		return ftlTemplateMapper;
	}


}
