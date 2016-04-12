package com.xinqing.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xinqing.vo.AjaxData;

@Controller
@RequestMapping("/ibe")
public class IBEController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping("/query")
	public Object query(String pnr){
		AjaxData ajaxData = new AjaxData();
		try{
			
			
			
			
		}catch(Exception e){
			
		}
		return ajaxData;
	}
}
