package com.xinqing.service;

import com.xinqing.entity.SysConfig;

public interface SysConfigService extends GenericService<SysConfig> {
	
	public SysConfig findByName(String configName);
	
}
