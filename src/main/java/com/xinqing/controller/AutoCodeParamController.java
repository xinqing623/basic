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

import com.xinqing.entity.AutoCodeParam;
import com.xinqing.entity.DataBaseBean;
import com.xinqing.service.AutoCodeParamService;
import com.xinqing.service.GenerateService;
import com.xinqing.util.UUIDUtil;
import com.xinqing.vo.AjaxData;
import com.xinqing.vo.Constant;
import com.xinqing.vo.PageInfo;
import com.xinqing.vo.Pager;

@Controller
@RequestMapping("/autoCode")
public class AutoCodeParamController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(SysConfigController.class);
	
	private static final String FOLDER = "autoCode";
	
	@Autowired
	private AutoCodeParamService autoCodeParamService;
	
	@RequestMapping("/selectPage")
	@ResponseBody
	public Object selectPage(PageInfo pageInfo, AutoCodeParam autoCodeParam){
		try {
			Map<String, Object> params = BeanUtils.describe(autoCodeParam);
			Pager<AutoCodeParam> pager = autoCodeParamService.findPage(pageInfo, params);
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
		AutoCodeParam autoCodeParam = autoCodeParamService.get(id);
		modelMap.addAttribute("autoCodeParam", autoCodeParam);
		return FOLDER + "/edit";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Object save(AutoCodeParam autoCodeParam){
		AjaxData ajaxData = new AjaxData();
		try {
			if(StringUtils.isEmpty(autoCodeParam.getId())){
				autoCodeParam.setId(UUIDUtil.getUUID());
				autoCodeParamService.save(autoCodeParam);
			}else{
				autoCodeParamService.update(autoCodeParam);
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
			autoCodeParamService.delete(ids);
		} catch (Exception e) {
			logger.error("删除出错",e);
			ajaxData = new AjaxData(false);
		}
		return ajaxData;
	}
	
	@RequestMapping("/testConnection")
	@ResponseBody
	public Object testConnection(String id){
		AjaxData ajaxData= new AjaxData();
		try{
			AutoCodeParam param = autoCodeParamService.get(id);
			GenerateService service = new GenerateService(param);
			if(service.testConnection()){
				ajaxData.setMessage("连接成功");
				return ajaxData;
			}else{
				ajaxData = new AjaxData(false, "测试连接失败");
			}
		}catch(Exception e){
			logger.error("测试连接出错",e);
			ajaxData = new AjaxData(false,"连接失败，请检查参数");
		}
		return ajaxData;
	}
	
	
	
	@RequestMapping("/viewTables")
	public String viewTables(String id, ModelMap modelMap){
		AutoCodeParam param = autoCodeParamService.get(id);
		GenerateService service = new GenerateService(param);
		List<DataBaseBean> tables = service.selectTables();
		modelMap.put("tables", tables);
		modelMap.put("id", id);
		return FOLDER + "/tables";
	}
	
	@RequestMapping("/generateCode")
	@ResponseBody
	public Object generateCode(String id,String tableName,String tempFile,String basePackage, String isCoverOldFile){
		AjaxData ajaxData = new AjaxData();
		try {
			AutoCodeParam param = autoCodeParamService.get(id);
			GenerateService service = new GenerateService(param);
			if(!StringUtils.isEmpty(isCoverOldFile) && "true".equals(isCoverOldFile)){
				isCoverOldFile = "true";
			}
			String[] tableNames = tableName.split(Constant.ID_SPLIT);
			String[] tempFiles = tempFile.split(Constant.ID_SPLIT);
			service.createFileFromFtl(tableNames, tempFiles, basePackage, "true".equals(isCoverOldFile));
			ajaxData.setMessage("生成成功");
		} catch (Exception e) {
			logger.error("生成代码出错",e);
			ajaxData = new AjaxData(false, "生成失败");
		}
		return ajaxData;
	}

}
