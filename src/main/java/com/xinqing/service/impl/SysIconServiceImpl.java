package com.xinqing.service.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinqing.entity.SysIcon;
import com.xinqing.mapping.BaseMapper;
import com.xinqing.mapping.SysIconMapper;
import com.xinqing.service.SysIconService;

@Service("sysIconService")
@Transactional
public class SysIconServiceImpl extends GenericServiceImpl<SysIcon> implements SysIconService{
	
	private static Logger logger = LoggerFactory.getLogger(SysIconServiceImpl.class);
	
	@Autowired
	private SysIconMapper sysIconMapper;

	@Override
	protected BaseMapper<SysIcon> getMapper() {
		return sysIconMapper;
	}


		

}
