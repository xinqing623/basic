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

import com.xinqing.entity.SysOrganization;
import com.xinqing.service.SysOrganizationService;
import com.xinqing.util.JSONUtil;
import com.xinqing.util.UUIDUtil;
import com.xinqing.vo.AjaxData;
import com.xinqing.vo.Constant;
import com.xinqing.vo.PageInfo;
import com.xinqing.vo.Pager;

@Controller
@RequestMapping("/org")
public class SysOrganizationController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(SysConfigController.class);
	
	private static final String FOLDER = "org";
	
	@Autowired
	private SysOrganizationService sysOrganizationService;
	
	@RequestMapping("/selectPage")
	@ResponseBody
	public Object selectPage(PageInfo pageInfo, SysOrganization sysOrganization){
		try {
			Map<String, Object> params = BeanUtils.describe(sysOrganization);
			if(StringUtils.isEmpty(params.get("parentId"))){
				params.put("parentId", "0");
			}
			Pager<SysOrganization> pager = sysOrganizationService.findPage(pageInfo, params);
			return pager;
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
		return null;
	}
	
	@RequestMapping("/listAll")
	@ResponseBody
	public Object listAll(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parentId", "0");
		List<SysOrganization> charts = sysOrganizationService.findList(params);
		String jsonStr = JSONUtil.listToJsonStr(charts);
		jsonStr = jsonStr.replaceAll("groupName", "text");
		return jsonStr;		
	}
	
	@RequestMapping("/preInsert")
	public String preInsert(){
		return FOLDER + "/insert";
	}
	
	@RequestMapping("/preEdit")
	public String preEdit(String id, ModelMap modelMap){
		SysOrganization sysOrganization = sysOrganizationService.get(id);
		modelMap.addAttribute("sysOrganization", sysOrganization);
		return FOLDER + "/edit";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Object save(SysOrganization sysOrganization){
		AjaxData ajaxData = new AjaxData();
		try {
			if(StringUtils.isEmpty(sysOrganization.getId())){
				sysOrganization.setId(UUIDUtil.getUUID());
				sysOrganizationService.save(sysOrganization);
			}else{
				sysOrganizationService.update(sysOrganization);
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
			sysOrganizationService.delete(ids);
		} catch (Exception e) {
			logger.error("删除出错",e);
			ajaxData = new AjaxData(false);
		}
		return ajaxData;
	}

}
