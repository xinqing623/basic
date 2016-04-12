package com.xinqing.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.xinqing.entity.SysUser;
import com.xinqing.util.SessionUtil;
import com.xinqing.vo.Constant;

public class LoginIntercepter implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String contextPath = request.getContextPath();
		SysUser user = (SysUser) SessionUtil.getSessionObject(Constant.USER);
		if(user == null){
			response.sendRedirect(contextPath + "/sessionout");
			return false;
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}


}
