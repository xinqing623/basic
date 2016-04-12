package com.xinqing.mapping;

import com.xinqing.entity.SysIcon;

public interface SysIconMapper extends BaseMapper<SysIcon>{
	
	public SysIcon selectByPK(String id);

	public int insert(SysIcon entity);
	
	public int update(SysIcon entity);
	
	public int delete(String id);
	
}
