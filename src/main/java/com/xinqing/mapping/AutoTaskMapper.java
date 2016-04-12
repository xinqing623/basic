package com.xinqing.mapping;

import com.xinqing.entity.AutoTask;

public interface AutoTaskMapper extends BaseMapper<AutoTask>{
	
	public AutoTask selectByPK(String id);

	public int insert(AutoTask entity);
	
	public int update(AutoTask entity);
	
	public int delete(String id);
	
}
