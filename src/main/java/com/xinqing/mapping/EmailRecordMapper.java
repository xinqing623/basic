package com.xinqing.mapping;

import com.xinqing.entity.EmailRecord;

public interface EmailRecordMapper extends BaseMapper<EmailRecord>{
	
	public EmailRecord selectByPK(String pkId);

	public int insert(EmailRecord entity);
	
	public int update(EmailRecord entity);
	
	public int delete(String pkId);
	
}
