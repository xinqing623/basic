package com.xinqing.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinqing.entity.EmailRecord;
import com.xinqing.mapping.BaseMapper;
import com.xinqing.mapping.EmailRecordMapper;
import com.xinqing.service.EmailRecordService;

@Service("emailRecordService")
@Transactional
public class EmailRecordServiceImpl extends GenericServiceImpl<EmailRecord> implements EmailRecordService{
	
	private static Logger logger = LoggerFactory.getLogger(EmailRecordServiceImpl.class);
	
	@Autowired
	private EmailRecordMapper emailRecordMapper;
	
	@Override
	protected BaseMapper<EmailRecord> getMapper() {
		return emailRecordMapper;
	}

	@Override
	public boolean isSend(String emailGroupId, String emailDate) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("emailGroupId", emailGroupId);
		params.put("emailDate", emailDate);
		List<EmailRecord> records = getMapper().select(params);
		if(null != records && records.size() > 0){
			return true;
		}
		return false;
	}

	

	

		

}
