package com.xinqing.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.xinqing.entity.SysConfig;
import com.xinqing.service.SysConfigService;

public class ConfigUtils {
	
	private static Logger logger = LoggerFactory.getLogger(ConfigUtils.class);
	
	private static Properties props = null;
	
	static{		
		try {  
            props=PropertiesLoaderUtils.loadAllProperties("mybatis.properties");  
        } catch (IOException e) {  
            logger.error("文件不存在或解析出错",e);
        }
	}
	
	private ConfigUtils(){	
		
	}
	
	public static String getProperty(String key){
		return props.getProperty(key);
	}
	
	public static String getSysConfig(String configName){
		SysConfigService service = (SysConfigService) SpringContextUtil.getBean("sysConfigService");
		SysConfig config = service.findByName(configName);
		if(config == null){
			logger.error("没有找到配置项{}", configName);
			return "";
		}
		return config.getConfigValue();
	}
}
