package com.xinqing.mapping;

import com.xinqing.entity.DicItems;

public interface DicItemsMapper extends BaseMapper<DicItems>{
	
	public DicItems selectByPK(String id);

	public int insert(DicItems entity);
	
	public int update(DicItems entity);
	
	public int delete(String id);
	
}
