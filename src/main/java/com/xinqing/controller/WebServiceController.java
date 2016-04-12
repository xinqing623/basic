package com.xinqing.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ora.service.CheckTicketService;
import com.xinqing.entity.TicketEntity;
import com.xinqing.util.ConfigUtils;
import com.xinqing.util.DateUtils;
import com.xinqing.util.HttpUtil;
import com.xinqing.util.MD5Util;
import com.xinqing.util.StringUtil;
import com.xinqing.vo.AjaxData;

@Controller
@RequestMapping("/web")
public class WebServiceController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	private CheckTicketService checkTicketService;
	
	@RequestMapping("/test")
	public void Test(String fromDate, String toDate, MultipartHttpServletRequest request){
		MultipartFile multipartFile = request.getFile("file");
		logger.info(multipartFile.getName());
//		List<TicketEntity> list  = checkTicketService.selectAllOrder(fromDate, toDate);
//		logger.info("number:{}", list.size());
		return;
	}

	/**
	 * 查询最低票价
	 * 
	 * @param orgin
	 * @param destination
	 * @param date1
	 * @param date2
	 * @param traveler1Type
	 * @param traveler2Type
	 * @param OTACodeStr
	 * @param keyStr
	 * @param suffix
	 * @return
	 */
	@RequestMapping("/lowFareSearch")
	@ResponseBody
	public Object lowFareSearch(String orgin, String destination, String date1, String date2, String traveler1Type,
			String traveler2Type, String otaCodeStr, String keyStr, String suffix) {
		AjaxData ajaxData = new AjaxData();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("leg", "index:0;origin:" + orgin + ";originType:A;destination:" + destination
				+ ";destinationType:A;date:" + date1);

		if ((date2 != null) && (date2.length() > 0)) {
			params.put("leg", "index:1;origin:" + destination + ";originType:A;destination:" + orgin
					+ ";destinationType:A;date:" + date2);
		}

		params.put("traveler", "code:" + traveler1Type);

		if ((traveler2Type != null) && (traveler2Type.length() == 3)) {
			params.put("traveler", "code:" + traveler2Type);
		}
		params.put("promotion", "code:ECERT");
		params.put("flexible", "false");
		params.put("cabin", "Economy");
		params.put("ln", "zh_CN");
		try {
			params.putAll(getGeneralPairs(otaCodeStr, keyStr));
		} catch (UnknownHostException e) {
			logger.error("获取IP地址出错", e);
			ajaxData = new AjaxData(false, "获取IP地址出错");
			return ajaxData;
		}
		String url = ConfigUtils.getSysConfig("tdp.url");
		String tmpurl = url + "search-low-fare-trips" + suffix;

		String responseStr = "";
		try {
			responseStr = HttpUtil.HttpClientPost(tmpurl, params);
		} catch (Exception e) {
			logger.error("查询出错",e);
			ajaxData = new AjaxData(false, "获取结果出错");
		}

		int sIndex = responseStr.indexOf("@Uri\":\"");
		if(sIndex > 0){
			String tripUri = responseStr.substring(sIndex + 7, sIndex + 31);
			ajaxData.setObj(tripUri);
		}else{
			ajaxData = new AjaxData(false, "未查询到航班");
		}
		return ajaxData;
	}

	/**
	 * 计算税费
	 * 
	 * @param tmpUid
	 * @param otaCodeStr
	 * @param keyStr
	 * @param suffix
	 * @return
	 */
	@RequestMapping("/calculateTrip")
	@ResponseBody
	public Object calculateTrip(String tmpUid, String otaCodeStr, String keyStr, String suffix) {
		AjaxData ajaxData = new AjaxData();

		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("searchId", tmpUid);
			params.put("index", "0");
			params.putAll(getGeneralPairs(otaCodeStr, keyStr));

			String url = ConfigUtils.getSysConfig("tdp.url");
			String tmpurl = url + "calculate-trip" + suffix;

			String responseStr = HttpUtil.HttpClientPost(tmpurl, params);

			int sIndex = responseStr.indexOf("@Uri\":\"");
			String tripUri = responseStr.substring(sIndex + 7, sIndex + 31);
			ajaxData.setObj(tripUri);

		} catch (Exception e) {
			logger.error("计算税费出错", e);
			ajaxData = new AjaxData(false, "计算税费出错");
		}
		return ajaxData;
	}

	/**
	 * 验证票价是否合理
	 * 
	 * @param tripId
	 * @param otaCodeStr
	 * @param keyStr
	 * @param suffix
	 * @return
	 */
	@RequestMapping("/verifyTrips")
	@ResponseBody
	public Object verifyTrips(String tripId, String otaCodeStr, String keyStr, String suffix) {
		AjaxData ajaxData = new AjaxData();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("tripId", tripId);
			params.putAll(getGeneralPairs(otaCodeStr, keyStr));
			String url = ConfigUtils.getSysConfig("tdp.url");
			String tmpurl = url + "verify-trip" + suffix;
			String response = HttpUtil.HttpClientPost(tmpurl, params);
			ajaxData.setObj(response);
		} catch (Exception e) {
			logger.error("验证票价是否合理出错", e);
		}
		return ajaxData;
	}

	/**
	 * 创建订单
	 * 
	 * @param tmpUid
	 * @param traveler1Type
	 * @param traveler1fName
	 * @param traveler1lName
	 * @param traveler1DocType
	 * @param traveler1DocNo
	 * @param traveler1DocCountry
	 * @param traveler1PhoneNo
	 * @param traveler1Email
	 * @param otaCodeStr
	 * @param keyStr
	 * @param suffix
	 * @return
	 */
	@RequestMapping("/createReservation")
	@ResponseBody
	public Object createReservation(String tmpUid, String traveler1Type, String traveler1fName, String traveler1lName,
			String traveler1DocType, String traveler1DocNo, String traveler1DocCountry, String traveler1PhoneNo,
			String traveler1Email, String otaCodeStr, String keyStr, String suffix) {

		AjaxData ajaxData = new AjaxData();
		try {
			Map<String, Object> params = new HashMap<String, Object>();

			params.put("traveler",
					"code:" + traveler1Type + ";fName:" + traveler1fName + ";lName:" + traveler1lName
							+ ";document.type:" + traveler1DocType + ";document.number:" + traveler1DocNo
							+ ";document.country:" + traveler1DocCountry
							+ ";document.expirationDate:2029-10-01;mobilePhone.number:" + traveler1PhoneNo);

			params.put("tripOptionId", tmpUid);
			params.put("customer", "code:ADT;fName:" + traveler1fName + ";lName:" + traveler1lName
					+ ";mobilePhone.number:" + traveler1PhoneNo + ";email:" + traveler1Email);

			params.putAll(getGeneralPairs(otaCodeStr, keyStr));

			String url = ConfigUtils.getSysConfig("tdp.url");
			String tmpurl = url + "create-reservation" + suffix;
			String responseStr = HttpUtil.HttpClientPost(tmpurl, params);

			int sIndex = responseStr.indexOf("{\"reservationCode\":");
			if (sIndex < 0) {
				ajaxData = new AjaxData(false, "没有获取到订单号");
			}
			String sTmp = responseStr.substring(sIndex + 20);
			sIndex = sTmp.indexOf("\",");
			String tripUri = sTmp.substring(0, sIndex);
			ajaxData.setObj(tripUri);

		} catch (Exception e) {
			logger.error("创建订单出错", e);
			ajaxData = new AjaxData(false, "创建订单出错");
		}
		return ajaxData;
	}

	/**
	 * 出票
	 * 
	 * @param reservationCode
	 * @param otaCodeStr
	 * @param keyStr
	 * @param suffix
	 * @return
	 */
	@RequestMapping("/documentReservation")
	@ResponseBody
	public Object documentReservation(String reservationCode, String amount, String otaCodeStr, String keyStr, String suffix) {
		AjaxData ajaxData = new AjaxData();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("reservationCode", reservationCode);
			params.put("alipaySeq", DateUtils.getCurFormatDate("yyyyMMddHHmmss") + StringUtil.getRandomNum(10));
			params.put("paymentSeq", DateUtils.getCurFormatDate("yyyyMMddHHmmss") + "01"+ reservationCode);
			params.put("amount", amount);
			params.put("currencyCode", "CNY");
			params.put("payDate", DateUtils.getCurFormatDate("yyyy-MM-dd"));
			params.put("payTime", DateUtils.getCurFormatDate("HH:mm:ss"));
			params.put("bankGroupCode", "APAY");
			params.put("bankGroupID", "800");
			params.put("bankCode", "kuaijie");
			params.put("bankID", "802");
			params.put("creditCardCompanyCode", "F1");
			params.putAll(getGeneralPairs(otaCodeStr, keyStr));

			String url = ConfigUtils.getSysConfig("tdp.url");
			String tmpurl = url + "document-reservation" + suffix;
			String response = HttpUtil.HttpClientPost(tmpurl, params);
			ajaxData.setObj(response);
		} catch (Exception e) {
			logger.error("", e);
			ajaxData = new AjaxData(false, "出票出错");
		}
		return ajaxData;
	}

	@RequestMapping("/getReservation")
	@ResponseBody
	public Object getReservation(String reservationCode, String otaCodeStr, String keyStr, String suffix) {
		AjaxData ajaxData = new AjaxData();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("reservationCode", reservationCode);
			params.putAll(getGeneralPairs(otaCodeStr, keyStr));

			String url = ConfigUtils.getSysConfig("tdp.url");
			String tmpurl = url + "get-reservation" + suffix;

			String responseStr = HttpUtil.HttpClientPost(tmpurl, params);
			ajaxData.setObj(responseStr);
		} catch (Exception e) {
			logger.error("", e);
			ajaxData = new AjaxData(false, "查询订单出错");
		}
		return ajaxData;
	}

	private static Map<String, Object> getGeneralPairs(String otaCodeStr, String keyStr) throws UnknownHostException {
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("OTACode", otaCodeStr);
		long times = System.currentTimeMillis();
		params.put("timestamp", String.valueOf(times));
		InetAddress addr = InetAddress.getLocalHost();

		// String ip = addr.getHostAddress().toString();
		String ip = "10.2.54.153";
		String txt = otaCodeStr + ip + times + keyStr;
		params.put("token", MD5Util.getMD5Str(txt));
		return params;
	}

}
