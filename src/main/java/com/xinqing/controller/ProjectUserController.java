package com.xinqing.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinqing.entity.ProjectUser;
import com.xinqing.entity.SysUser;
import com.xinqing.service.ProjectUserService;
import com.xinqing.service.SysUserService;
import com.xinqing.util.UUIDUtil;
import com.xinqing.vo.AjaxData;
import com.xinqing.vo.Constant;
import com.xinqing.vo.PageInfo;
import com.xinqing.vo.Pager;
import com.xinqing.vo.TreeVo;

@Controller
@RequestMapping("/projectUser")
public class ProjectUserController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(ProjectUserController.class);
	
	private static final String FOLDER = "projectUser";
	
	@Autowired
	private ProjectUserService projectUserService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@RequestMapping("/selectPage")
	@ResponseBody
	public Object selectPage(PageInfo pageInfo, ProjectUser projectUser){
		try {
			Map<String, Object> params = BeanUtils.describe(projectUser);
			Pager<ProjectUser> pager = projectUserService.findPage(pageInfo, params);
			return pager;
		} catch (Exception e) {
			logger.error("查询出错",e);
		}
		return null;
	}
	
	@RequestMapping("/preInsert")
	public String preInsert(HttpServletRequest request,String projectId){
		request.setAttribute("projectId", projectId);
		return FOLDER + "/insert";
	}
	
	@RequestMapping("/preEdit")
	public String preEdit(String pkId, ModelMap modelMap){
		ProjectUser projectUser = projectUserService.get(pkId);
		modelMap.addAttribute("projectUser", projectUser);
		return FOLDER + "/edit";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Object save(String projectId, String userId){
		AjaxData ajaxData = new AjaxData();
		try {
			projectUserService.update(projectId, userId);
			
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
			projectUserService.delete(ids);
		} catch (Exception e) {
			logger.error("删除出错",e);
			ajaxData = new AjaxData(false);
		}
		return ajaxData;
	}
	
	
	@RequestMapping("/loadProjectUsers")
	@ResponseBody
	public Object loadProjectUsers(String projectId){
		List<SysUser> userList = sysUserService.findAll();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("projectId", projectId);
		List<ProjectUser> projectUsers = projectUserService.findList(params); 
		List<TreeVo> treeVos = new ArrayList<TreeVo>();
		for(SysUser user : userList){
			TreeVo vo = new TreeVo();
			vo.setId(user.getId());
			vo.setText(user.getNickName());
			vo.setChecked(isUserInProject(user, projectUsers));
			treeVos.add(vo);
		}
		return treeVos;
	}

	private boolean isUserInProject(SysUser user, List<ProjectUser> projectUsers) {
		for(ProjectUser projectUser : projectUsers){
			if(user.getId().equals(projectUser.getUserId())){
				return true;
			}
		}
		return false;
	}

}
