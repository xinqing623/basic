package com.xinqing.mapping;

import java.util.List;
import java.util.Map;

import com.xinqing.entity.UserLog;

public interface UserLogMapper extends BaseMapper<UserLog>{
	
	public UserLog selectByPK(String pkId);

	public int insert(UserLog entity);
	
	public int update(UserLog entity);
	
	public int delete(String pkId);
	
	public List<UserLog> selectGroupLog(Map<String, Object> params);
	
}
