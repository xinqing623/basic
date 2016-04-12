package com.xinqing.controller;

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

import com.xinqing.entity.AutoTask;
import com.xinqing.service.AutoTaskService;
import com.xinqing.util.QuartzManager;
import com.xinqing.util.UUIDUtil;
import com.xinqing.vo.AjaxData;
import com.xinqing.vo.Constant;
import com.xinqing.vo.PageInfo;
import com.xinqing.vo.Pager;

@Controller
@RequestMapping("/autoTask")
public class AutoTaskController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(AutoTaskController.class);
	
	private static final String FOLDER = "task";
	
	@Autowired
	private AutoTaskService autoTaskService;
	
	@RequestMapping("/selectPage")
	@ResponseBody
	public Object selectPage(PageInfo pageInfo, AutoTask autoTask){
		try {
			Map<String, Object> params = BeanUtils.describe(autoTask);
			Pager<AutoTask> pager = autoTaskService.findPage(pageInfo, params);
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
	public String preEdit(String id, ModelMap modelMap){
		AutoTask autoTask = autoTaskService.get(id);
		modelMap.addAttribute("autoTask", autoTask);
		return FOLDER + "/edit";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Object save(AutoTask autoTask){
		AjaxData ajaxData = new AjaxData();
		try {
			if(StringUtils.isEmpty(autoTask.getId())){
				autoTask.setId(UUIDUtil.getUUID());
				autoTaskService.save(autoTask);
			}else{
				autoTaskService.update(autoTask);
			}
			
		} catch (Exception e) {
			logger.error("保存出错",e);
			ajaxData = new AjaxData(false);
		}
		return ajaxData;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(String id){
		AjaxData ajaxData = new AjaxData();
		try {
			String[] ids = id.split(Constant.ID_SPLIT);
			autoTaskService.delete(ids);
		} catch (Exception e) {
			logger.error("删除出错",e);
			ajaxData = new AjaxData(false);
		}
		return ajaxData;
	}
	
	@RequestMapping("/startJob")
	@ResponseBody
	public Object startJob(String id){
		AjaxData ajaxData = new AjaxData();
		try {
			boolean isSucc = autoTaskService.startJob(id);
			if(!isSucc){
				ajaxData.setMessage("启动作业出错，请确认作业的工作状态");
			}
		} catch (Exception e) {
			logger.error("启动作业出错",e);
			ajaxData = new AjaxData(false);
		}
		return ajaxData;
	}
	
	@RequestMapping("/stopJob")
	@ResponseBody
	public Object stopJob(String id){
		AjaxData ajaxData = new AjaxData();
		try {
			boolean isSucc = autoTaskService.stopJob(id);	
			if(!isSucc){
				ajaxData.setMessage("停止作业出错，请确认作业的工作状态");
			}
		} catch (Exception e) {
			logger.error("停止作业出错",e);
			ajaxData = new AjaxData(false);
		}
		return ajaxData;
	}

}
