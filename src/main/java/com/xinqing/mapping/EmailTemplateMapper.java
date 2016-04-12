package com.xinqing.mapping;

import com.xinqing.entity.EmailTemplate;

public interface EmailTemplateMapper extends BaseMapper<EmailTemplate>{
	
	public EmailTemplate selectByPK(String pkId);

	public int insert(EmailTemplate entity);
	
	public int update(EmailTemplate entity);
	
	public int delete(String pkId);
	
}
