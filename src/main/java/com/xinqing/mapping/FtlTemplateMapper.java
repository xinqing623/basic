package com.xinqing.mapping;

import com.xinqing.entity.FtlTemplate;

public interface FtlTemplateMapper extends BaseMapper<FtlTemplate>{
	
	public FtlTemplate selectByPK(String id);

	public int insert(FtlTemplate entity);
	
	public int update(FtlTemplate entity);
	
	public int delete(String id);
	
}
