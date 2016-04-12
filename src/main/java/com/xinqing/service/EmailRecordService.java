package com.xinqing.service;

import com.xinqing.entity.EmailRecord;

public interface EmailRecordService extends GenericService<EmailRecord> {

	public boolean isSend(String pkId, String curFormatDate);
	
}
