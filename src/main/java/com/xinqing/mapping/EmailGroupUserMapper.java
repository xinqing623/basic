package com.xinqing.mapping;

import com.xinqing.entity.EmailGroupUser;

public interface EmailGroupUserMapper extends BaseMapper<EmailGroupUser>{
	
	public EmailGroupUser selectByPK(String pkId);

	public int insert(EmailGroupUser entity);
	
	public int update(EmailGroupUser entity);
	
	public int delete(String pkId);
	
}
