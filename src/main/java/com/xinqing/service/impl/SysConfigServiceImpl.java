package com.xinqing.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xinqing.entity.AutoCodeParam;
import com.xinqing.entity.SysConfig;
import com.xinqing.mapping.BaseMapper;
import com.xinqing.mapping.SysConfigMapper;
import com.xinqing.service.SysConfigService;
import com.xinqing.vo.PageInfo;
import com.xinqing.vo.Pager;

@Service("sysConfigService")
@Transactional
public class SysConfigServiceImpl extends GenericServiceImpl<SysConfig> implements SysConfigService{
	
	private static Logger logger = LoggerFactory.getLogger(SysConfigServiceImpl.class);
	
	@Autowired
	private SysConfigMapper sysConfigMapper;

	@Override
	protected BaseMapper<SysConfig> getMapper() {
		return sysConfigMapper;
	}

	@Override
	public SysConfig findByName(String configName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("configName", configName);
		List<SysConfig> configs = findList(params);
		if(configs != null && configs.size() > 0){
			return configs.get(0);
		}
		return null;
	}

}
