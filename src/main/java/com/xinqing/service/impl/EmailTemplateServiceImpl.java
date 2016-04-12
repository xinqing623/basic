package com.xinqing.service.impl;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xinqing.entity.EmailTemplate;
import com.xinqing.mapping.BaseMapper;
import com.xinqing.mapping.EmailTemplateMapper;
import com.xinqing.service.EmailTemplateService;
import com.xinqing.vo.PageInfo;
import com.xinqing.vo.Pager;

@Service("emailTemplateService")
@Transactional
public class EmailTemplateServiceImpl extends GenericServiceImpl<EmailTemplate> implements EmailTemplateService{
	
	private static Logger logger = LoggerFactory.getLogger(EmailTemplateServiceImpl.class);
	
	@Autowired
	private EmailTemplateMapper emailTemplateMapper;

	@Override
	protected BaseMapper<EmailTemplate> getMapper() {
		return emailTemplateMapper;
	}


}
