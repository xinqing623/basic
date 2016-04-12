package com.xinqing.service;

import com.xinqing.entity.EmailGroupUser;

public interface EmailGroupUserService extends GenericService<EmailGroupUser> {

	void update(String emailGroupId, String userId, boolean isCcUser);
	
}
