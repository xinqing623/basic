package com.xinqing.service.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinqing.entity.UserMenu;
import com.xinqing.mapping.BaseMapper;
import com.xinqing.mapping.UserMenuMapper;
import com.xinqing.service.UserMenuService;

@Service("userMenuService")
@Transactional
public class UserMenuServiceImpl extends GenericServiceImpl<UserMenu> implements UserMenuService{
	
	private static Logger logger = LoggerFactory.getLogger(UserMenuServiceImpl.class);
	
	@Autowired
	private UserMenuMapper userMenuMapper;

	@Override
	protected BaseMapper<UserMenu> getMapper() {
		return userMenuMapper;
	}		

}
