package com.xinqing.controller;

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

import com.xinqing.entity.FtlTemplate;
import com.xinqing.service.FtlTemplateService;
import com.xinqing.util.ConfigUtils;
import com.xinqing.util.FileUtils;
import com.xinqing.util.UUIDUtil;
import com.xinqing.vo.AjaxData;
import com.xinqing.vo.Constant;
import com.xinqing.vo.PageInfo;
import com.xinqing.vo.Pager;

@Controller
@RequestMapping("/ftlTemplate")
public class FtlTemplateController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(SysConfigController.class);
	
	private static final String FOLDER = "ftltemplate";
	
	@Autowired
	private FtlTemplateService ftlTemplateService;
	
	@RequestMapping("/selectPage")
	@ResponseBody
	public Object selectPage(PageInfo pageInfo, FtlTemplate ftlTemplate){
		try {
			Map<String, Object> params = BeanUtils.describe(ftlTemplate);
			Pager<FtlTemplate> pager = ftlTemplateService.findPage(pageInfo, params);
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
		FtlTemplate ftlTemplate = ftlTemplateService.get(id);
		modelMap.addAttribute("ftlTemplate", ftlTemplate);
		return FOLDER + "/edit";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Object save(FtlTemplate ftlTemplate){
		AjaxData ajaxData = new AjaxData();
		try {
			if(StringUtils.isEmpty(ftlTemplate.getId())){
				ftlTemplate.setId(UUIDUtil.getUUID());
				ftlTemplateService.save(ftlTemplate);
			}else{
				ftlTemplateService.update(ftlTemplate);
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
			ftlTemplateService.delete(ids);
		} catch (Exception e) {
			logger.error("删除出错",e);
			ajaxData = new AjaxData(false);
		}
		return ajaxData;
	}
	
	@RequestMapping("/editTemplate")
	public String editTemplate(String id, ModelMap modelMap){
		try {
			FtlTemplate template = ftlTemplateService.get(id);
			String templateFolder = ConfigUtils.getSysConfig("temp.dir");
			String tempContent = FileUtils.readFileByLines(templateFolder + template.getTemplateName());
			modelMap.addAttribute("content", tempContent);					
		} catch (Exception e) {
			logger.error("查询模板出错",e);
		}
		modelMap.addAttribute("id", id);	
		return FOLDER + "/template";
	}
	
	@RequestMapping("/saveTemplate")
	@ResponseBody
	public Object saveTemplate(String id,String content){
		AjaxData ajaxData = new AjaxData();
		try {
			FtlTemplate template = ftlTemplateService.get(id);
			content = content.replaceAll("&quot;", "\"");
			String templateFolder = ConfigUtils.getSysConfig("temp.dir");
			FileUtils.writeFile(content, templateFolder + template.getTemplateName());
		} catch (Exception e) {
			logger.error("查询模板出错",e);
		}
		return ajaxData;
	}
	
	@RequestMapping("/listAll")
	@ResponseBody
	public Object listAll(){
		List<FtlTemplate> list = ftlTemplateService.findAll();
		return list;
	}

}
