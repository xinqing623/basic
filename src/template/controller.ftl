package ${basePackage}.controller;

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

import ${basePackage}.entity.${className};
import ${basePackage}.service.${className}Service;
import ${basePackage}.util.UUIDUtil;
import ${basePackage}.vo.AjaxData;
import ${basePackage}.vo.Constant;
import ${basePackage}.vo.PageInfo;
import ${basePackage}.vo.Pager;

@Controller
@RequestMapping("/${tableName}")
public class ${className}Controller extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(${className}Controller.class);
	
	private static final String FOLDER = "${tableName}";
	
	@Autowired
	private ${className}Service ${tableName}Service;
	
	@RequestMapping("/selectPage")
	@ResponseBody
	public Object selectPage(PageInfo pageInfo, ${className} ${tableName}){
		try {
			Map<String, Object> params = BeanUtils.describe(${tableName});
			Pager<${className}> pager = ${tableName}Service.findPage(pageInfo, params);
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
	public String preEdit(${pkDataTypeClass} ${pkFieldName}, ModelMap modelMap){
		${className} ${tableName} = ${tableName}Service.get(${pkFieldName});
		modelMap.addAttribute("${tableName}", ${tableName});
		return FOLDER + "/edit";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Object save(${className} ${tableName}){
		AjaxData ajaxData = new AjaxData();
		try {
			if(StringUtils.isEmpty(${tableName}.get${pkFieldNameUpperCase}())){
				${tableName}.set${pkFieldNameUpperCase}(UUIDUtil.getUUID());
				${tableName}Service.save(${tableName});
			}else{
				${tableName}Service.update(${tableName});
			}
			
		} catch (Exception e) {
			logger.error("保存出错",e);
			ajaxData = new AjaxData(false);
		}
		return ajaxData;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(${pkDataTypeClass} ${pkFieldName}){
		AjaxData ajaxData = new AjaxData();
		try {
			String[] ids = ${pkFieldName}.split(Constant.ID_SPLIT);
			${tableName}Service.delete(ids);
		} catch (Exception e) {
			logger.error("删除出错",e);
			ajaxData = new AjaxData(false);
		}
		return ajaxData;
	}

}
