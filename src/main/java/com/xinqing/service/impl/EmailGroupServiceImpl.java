package com.xinqing.service.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinqing.entity.EmailGroup;
import com.xinqing.mapping.BaseMapper;
import com.xinqing.mapping.EmailGroupMapper;
import com.xinqing.service.EmailGroupService;

@Service("emailGroupService")
@Transactional
public class EmailGroupServiceImpl extends GenericServiceImpl<EmailGroup> implements EmailGroupService{
	
	private static Logger logger = LoggerFactory.getLogger(EmailGroupServiceImpl.class);
	
	@Autowired
	private EmailGroupMapper emailGroupMapper;

	@Override
	protected BaseMapper<EmailGroup> getMapper() {
		return emailGroupMapper;
	}

	
		

}
