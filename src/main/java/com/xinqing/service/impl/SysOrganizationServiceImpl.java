package com.xinqing.service.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinqing.entity.SysOrganization;
import com.xinqing.mapping.BaseMapper;
import com.xinqing.mapping.SysOrganizationMapper;
import com.xinqing.service.SysOrganizationService;

@Service("sysOrganizationService")
@Transactional
public class SysOrganizationServiceImpl extends GenericServiceImpl<SysOrganization> implements SysOrganizationService{
	
	private static Logger logger = LoggerFactory.getLogger(SysOrganizationServiceImpl.class);
	
	@Autowired
	private SysOrganizationMapper sysOrganizationMapper;

	@Override
	protected BaseMapper<SysOrganization> getMapper() {
		return sysOrganizationMapper;
	}	

}
