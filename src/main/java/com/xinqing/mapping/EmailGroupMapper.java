package com.xinqing.mapping;

import com.xinqing.entity.EmailGroup;

public interface EmailGroupMapper extends BaseMapper<EmailGroup>{
	
	public EmailGroup selectByPK(String pkId);

	public int insert(EmailGroup entity);
	
	public int update(EmailGroup entity);
	
	public int delete(String pkId);
	
}
