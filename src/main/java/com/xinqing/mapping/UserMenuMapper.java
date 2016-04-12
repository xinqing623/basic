package com.xinqing.mapping;

import com.xinqing.entity.UserMenu;

public interface UserMenuMapper extends BaseMapper<UserMenu>{
	
	public UserMenu selectByPK(String pkId);

	public int insert(UserMenu entity);
	
	public int update(UserMenu entity);
	
	public int delete(String pkId);
	
}
