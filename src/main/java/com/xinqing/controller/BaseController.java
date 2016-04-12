package com.xinqing.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinqing.util.NetworkUtil;

@Controller
public class BaseController {
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
	    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    CustomDateEditor dateEditor = new CustomDateEditor(format, true);
	    binder.registerCustomEditor(Date.class, dateEditor);
	}	

}
