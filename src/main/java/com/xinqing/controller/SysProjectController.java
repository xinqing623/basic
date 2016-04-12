package com.xinqing.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

import com.xinqing.entity.SysProject;
import com.xinqing.entity.SysUser;
import com.xinqing.service.SysProjectService;
import com.xinqing.util.JSONUtil;
import com.xinqing.util.SessionUtil;
import com.xinqing.util.UUIDUtil;
import com.xinqing.vo.AjaxData;
import com.xinqing.vo.Constant;
import com.xinqing.vo.PageInfo;
import com.xinqing.vo.Pager;

@Controller
@RequestMapping("/sysProject")
public class SysProjectController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(SysProjectController.class);
	
	private static final String FOLDER = "sysProject";
	
	@Autowired
	private SysProjectService sysProjectService;
	
	@RequestMapping("/selectPage")
	@ResponseBody
	public Object selectPage(PageInfo pageInfo, SysProject sysProject){
		try {
			Map<String, Object> params = BeanUtils.describe(sysProject);
			Pager<SysProject> pager = sysProjectService.findPage(pageInfo, params);
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
		SysProject sysProject = sysProjectService.get(pkId);
		modelMap.addAttribute("sysProject", sysProject);
		return FOLDER + "/edit";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Object save(SysProject sysProject){
		AjaxData ajaxData = new AjaxData();
		try {
			if(StringUtils.isEmpty(sysProject.getPkId())){
				sysProject.setPkId(UUIDUtil.getUUID());
				sysProjectService.save(sysProject);
			}else{
				sysProjectService.update(sysProject);
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
			sysProjectService.delete(ids);
		} catch (Exception e) {
			logger.error("删除出错",e);
			ajaxData = new AjaxData(false);
		}
		return ajaxData;
	}
	
	
	@RequestMapping("/selectProjects")
	@ResponseBody
	public Object selectProjects(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", "1");
		List<SysProject> list = sysProjectService.findList(params);
		String jsonStr = JSONUtil.listToJsonStr(list);
		jsonStr = jsonStr.replaceAll("projectName", "text");
		return jsonStr;
	}
	
	/**
	 * 查询用户加入的项目
	 * @return
	 */
	@RequestMapping("/selectUserProjects")
	@ResponseBody
	public Object selectUserProjects(){
		SysUser user = (SysUser) SessionUtil.getSessionObject(Constant.USER);
		List<SysProject> projects = sysProjectService.findUserProjects(user.getId());
		String jsonStr = JSONUtil.listToJsonStr(projects);
		jsonStr = jsonStr.replaceAll("projectName", "text");
		return jsonStr;
	}

}
