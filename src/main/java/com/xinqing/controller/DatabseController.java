package com.xinqing.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinqing.entity.DataBaseBean;
import com.xinqing.entity.StMenu;
import com.xinqing.entity.TableColumn;
import com.xinqing.service.DatabaseService;
import com.xinqing.service.StMenuService;
import com.xinqing.vo.AjaxData;

@Controller
@RequestMapping("/db")
public class DatabseController {

	private static Logger logger = LoggerFactory.getLogger(DatabseController.class);
	@Autowired
	private DatabaseService databaseService;
	
	@Autowired
	private StMenuService  stMenuService;
	
	@RequestMapping("/list")
	@ResponseBody
	public Object selectTables(){
		AjaxData ajaxData = new AjaxData();
		try{
			List<DataBaseBean> list = databaseService.selectAllTables("vote");
			ajaxData.setObj(list);
		}catch(Exception e){
			logger.error("查询出错",e);
			ajaxData = new AjaxData(false);
		}
		return ajaxData;
	}
	
	@RequestMapping("/listColumns")
	@ResponseBody
	public Object selectTableColumns(){
		AjaxData ajaxData = new AjaxData();
		try{
			List<TableColumn> list = databaseService.selectTableColumns("acct_user");
			ajaxData.setObj(list);
		}catch(Exception e){
			logger.error("查询出错",e);
			ajaxData = new AjaxData(false);
		}
		return ajaxData;
	}
	
	@RequestMapping("/user")
	@ResponseBody
	public Object user(){		
		AjaxData ajaxData = new AjaxData();
		try{
//			AcctUser user = acctUserService.get("1");
//			user.setDepartment("测试");
//			acctUserService.update(user);
//			ajaxData.setObj(user);
//			AcctUser user = new AcctUser();
//			user.setId("1");
//			user.setDepartment("烽火集成");
//			user.setNickName("王方");
//			user.setPassword("123");
//			user.setRegisterTime(new Date());
//			acctUserService.save(user);
			
//			List<AcctUser> users = acctUserService.findPage();
//			Page<AcctUser> users = acctUserService.findPage();
			
			List<StMenu> menus = stMenuService.findAll();
			ajaxData.setObj(menus);
		}catch(Exception e){
			logger.error("查询出错",e);
			ajaxData = new AjaxData(false);
		}
		return ajaxData;
	}
}
