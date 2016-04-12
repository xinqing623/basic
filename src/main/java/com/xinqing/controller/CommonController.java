package com.xinqing.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinqing.entity.StMenu;
import com.xinqing.entity.SysUser;
import com.xinqing.service.StMenuService;
import com.xinqing.service.SysUserService;
import com.xinqing.util.NetworkUtil;
import com.xinqing.util.SessionUtil;
import com.xinqing.util.StringUtil;
import com.xinqing.vo.Constant;

@Controller
public class CommonController extends BaseController{
	
	private static Logger logger = LoggerFactory.getLogger(CommonController.class);
	
	@Autowired
	private StMenuService stMenuService;
	
	
	@Resource
	private SysUserService sysUserService;
	
	@RequestMapping("/showPage")
	public String showPage(String menuId){
		Map<String, StMenu> menus = (Map<String, StMenu>) SessionUtil.getSessionObject(Constant.USER_MENU);
		StMenu menu = menus.get(menuId);
		if(menu != null){
			if("1".equals(menu.getIsRedirect())){
				return "redirect:"  + menu.getUrl();
			}
			return menu.getUrl();
		}else{
			return "nopermission";
		}
	}
	
	@RequestMapping("/login")
	public Object login(String userName, String password, String validateCode, ModelMap modelMap){
		if(StringUtil.isEmpty(userName) || StringUtil.isEmpty(password)){
//			modelMap.addAttribute("msg", "用户名或密码为空");
			return "login";
		}
		SysUser user = sysUserService.login(userName, password);
		if(user == null){
			modelMap.put("msg", "未找到该用户或用户名密码错误");
			return "login";
		}
		List<StMenu> menus = stMenuService.findUserMenus(user.getId());
		Map<String, StMenu> menuMap = new HashMap<String, StMenu>();
		for(StMenu menu : menus){
			menuMap.put(menu.getId(), menu);
		}
		SessionUtil.setSessionObject(Constant.USER, user);
		SessionUtil.setSessionObject(Constant.USER_MENU, menuMap);
		return "redirect:/index";
	}

	@RequestMapping("/logout")
	public Object logout(HttpServletRequest request){
		request.getSession().invalidate();
		return "login";
	}
	
	@RequestMapping("/sessionout")
	public String sessionout(){
		return "sessionout";
	}
	
	@RequestMapping("/index")
	public Object index(){
		return "index";
	}
}
