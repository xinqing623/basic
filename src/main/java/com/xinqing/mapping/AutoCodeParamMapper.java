package com.xinqing.mapping;

import com.xinqing.entity.AutoCodeParam;

public interface AutoCodeParamMapper extends BaseMapper<AutoCodeParam>{
	
	public AutoCodeParam selectByPK(String id);

	public int insert(AutoCodeParam entity);
	
	public int update(AutoCodeParam entity);
	
	public int delete(String id);
	
}
