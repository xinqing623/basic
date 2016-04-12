package com.xinqing.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * <pre>获取客户端真实IP</pre>
 *
 * @author 郑兴(zhengxing)
 *
 */
public class NetworkUtil {
	private static final Log logger = LogFactory.getLog(NetworkUtil.class);

	/**
	 * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public final static String getIpAddress(HttpServletRequest request) throws IOException {
		String ip = request.getHeader("x-forwarded-for");
		if (logger.isDebugEnabled()) {
			logger.debug("getIpAddress(HttpServletRequest) - x-forwarded-for - String ip=" + ip);
		}
		if (isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			if (isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
				if (logger.isDebugEnabled()) {
					logger.debug("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=" + ip);
				}
			}
			if (isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
				if (logger.isDebugEnabled()) {
					logger.debug("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=" + ip);
				}
			}
			if (isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
				if (logger.isDebugEnabled()) {
					logger.debug("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=" + ip);
				}
			}
			if (isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
				if (logger.isDebugEnabled()) {
					logger.debug("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=" + ip);
				}
			}
			if (isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
				if (logger.isDebugEnabled()) {
					logger.debug("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);
				}
			}
		} else if (ip.length() > 15) {
			String[] ips = ip.split(",");
			for (int index = 0; index < ips.length; index++) {
				String strIp = ips[index];
				if (!("unknown".equalsIgnoreCase(strIp))) {
					ip = strIp;
					break;
				}
			}
		}
		return ip;
	}

	/**
	 * <pre></pre>
	 * @Auther 郑兴(zhengxing)
	 * @Date 2013-2-22
	 * @param string
	 * @return
	 */
	private static boolean isEmpty(String string) {
		return string == null || "".equals(string.trim());
	}
}
