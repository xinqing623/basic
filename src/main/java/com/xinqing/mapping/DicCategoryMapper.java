package com.xinqing.mapping;

import com.xinqing.entity.DicCategory;

public interface DicCategoryMapper extends BaseMapper<DicCategory>{
	
	public DicCategory selectByPK(String id);

	public int insert(DicCategory entity);
	
	public int update(DicCategory entity);
	
	public int delete(String id);
	
}
