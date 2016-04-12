package com.xinqing.controller;

import java.util.Date;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinqing.entity.SysUser;
import com.xinqing.entity.UserLog;
import com.xinqing.service.UserLogService;
import com.xinqing.util.SessionUtil;
import com.xinqing.util.UUIDUtil;
import com.xinqing.vo.AjaxData;
import com.xinqing.vo.Constant;
import com.xinqing.vo.PageInfo;
import com.xinqing.vo.Pager;

@Controller
@RequestMapping("/userLog")
public class UserLogController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(UserLogController.class);
	
	private static final String FOLDER = "userLog";
	
	@Autowired
	private UserLogService userLogService;
	
	@RequestMapping("/selectPage")
	@ResponseBody
	public Object selectPage(PageInfo pageInfo, UserLog userLog){
		try {
			SysUser user = (SysUser) SessionUtil.getSessionObject(Constant.USER);
			if(!user.hasRole("0")){
				userLog.setUserId(user.getId());
			}
			Map<String, Object> params = BeanUtils.describe(userLog);
			Pager<UserLog> pager = userLogService.findPage(pageInfo, params);
			return pager;
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
		return null;
	}
	
	@RequestMapping("/preInsert")
	public String preInsert(){
		return FOLDER + "/insert";
	}
	
	@RequestMapping("/preEdit")
	public String preEdit(String pkId, ModelMap modelMap){
		UserLog userLog = userLogService.get(pkId);
		modelMap.addAttribute("userLog", userLog);
		return FOLDER + "/edit";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Object save(UserLog userLog){
		AjaxData ajaxData = new AjaxData();
		try {
			SysUser user = (SysUser) SessionUtil.getSessionObject(Constant.USER);
			if(StringUtils.isEmpty(userLog.getPkId())){
				userLog.setUserId(user.getId());
				userLog.setPkId(UUIDUtil.getUUID());
				userLog.setLogDate(new Date());
				userLog.setCreateTime(new Date());
				userLogService.save(userLog);
			}else{
				userLog.setLogDate(new Date());
				userLogService.update(userLog);
			}
			
		} catch (Exception e) {
			logger.error("保存出错",e);
			ajaxData = new AjaxData(false);
		}
		return ajaxData;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(String pkId){
		AjaxData ajaxData = new AjaxData();
		try {
			String[] ids = pkId.split(Constant.ID_SPLIT);
			userLogService.delete(ids);
		} catch (Exception e) {
			logger.error("删除出错",e);
			ajaxData = new AjaxData(false);
		}
		return ajaxData;
	}

}
