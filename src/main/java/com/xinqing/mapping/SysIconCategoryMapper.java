package com.xinqing.mapping;

import com.xinqing.entity.SysIconCategory;

public interface SysIconCategoryMapper extends BaseMapper<SysIconCategory>{
	
	public SysIconCategory selectByPK(String id);

	public int insert(SysIconCategory entity);
	
	public int update(SysIconCategory entity);
	
	public int delete(String id);
	
}
