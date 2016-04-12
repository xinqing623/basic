package com.xinqing.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings("unchecked")
public class JSONUtil {

	private JSONUtil(){
		
	}
	
	public static String getJsonStr(Object object){
		if(object == null) return "{}";
		try{
			JSONObject obj = JSONObject.fromObject(object);
			return obj.toString();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@SuppressWarnings("rawtypes")
	public static String listToJsonStr(List list){
		if(list == null) return "[]";
		return JSONArray.fromObject(list).toString();
	}
	
	/**
	 * 瑙ｆ瀽json涓篖ist
	 */
	public static  List<Map<String, Object>> parseJSON2List(String jsonStr) {
		JSONArray jsonArr = JSONArray.fromObject(jsonStr);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Iterator<JSONObject> it = jsonArr.iterator();
		while (it.hasNext()) {
			JSONObject json2 = it.next();
			list.add(parseJSON2Map(json2.toString()));
		}
		return list;
	}
	
	/**
	 * 瑙ｆ瀽json涓簃ap
	 * @param jsonStr
	 * @return
	 */
	public static  Map<String, Object> parseJSON2Map(String jsonStr) {
		// 鏈�灞傝В鏋�
		JSONObject json = JSONObject.fromObject(jsonStr);
		return parseJSON2Map(json);
	}
	
	/**
	 * 瑙ｆ瀽json涓簃ap
	 * @param json
	 * @return
	 */
	public static Map<String, Object> parseJSON2Map(JSONObject json) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (Object k : json.keySet()) {
			Object v = json.get(k);
			// 濡傛灉鍐呭眰杩樻槸鏁扮粍鐨勮瘽锛岀户缁В鏋�
			if(v == null){
				map.put(k.toString(), "");
			}
			else if (v instanceof JSONObject) {
				if(((JSONObject)v).isNullObject()){
					map.put(k.toString(), "");
				}
				else{
					map.put(k.toString(), parseJSON2Map((JSONObject)v));
				}
			}else if (v instanceof JSONArray) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Iterator<JSONObject> it = ((JSONArray) v).iterator();
				while (it.hasNext()) {
					JSONObject json2 = it.next();
					list.add(parseJSON2Map(json2));
				}
				map.put(k.toString(), list);
			} else {
				map.put(k.toString(), v);
			}
		}
		return map;
	}
	
	
	public static List<Map<String, Object>> getListByUrl(String url) {
		try {
			// 閫氳繃HTTP鑾峰彇JSON鏁版嵁
			InputStream in = new URL(url).openStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			return parseJSON2List(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public  Map<String, Object> getMapByUrl(String url) {
		try {
			// 閫氳繃HTTP鑾峰彇JSON鏁版嵁
			InputStream in = new URL(url).openStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			return parseJSON2Map(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// test
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		// String url = "http://...";
		// List<Map<String,Object>> list = getListByUrl(url);
		// System.out.println(list);
		String str = "{\"ACTION_NAME\":\"COMORDER_TRANS\",\"CITY_CODE\":\"420100\",\"ACTION_INFO\":{\"TRANS_TYPE\":\"04\",\"PHONE\":\"18611159401\",\"ORDER_ID\":\"12380147\",\"USER_ID\":null},\"ACTION_INVOKER\":{\"OSVER\":\"2.3.3\",\"PHONE\":\"\",\"IMSI\":\"89860047175006838074\",\"VER\":\"18\",\"OSDESCRIPT\":\"HTC Vision\",\"IMEI\":\"352212045773335\",\"OSNAME\":\"Android\"}}";
//		String str = "{ \"people\": [  { \"firstName\": \"Brett\", \"lastName\":\"McLaughlin\", \"email\": \"brett@newInstance.com\" },  { \"firstName\": \"Jason\", \"lastName\":\"Hunter\", \"email\": \"jason@servlets.com\" },  { \"firstName\": \"Elliotte\", \"lastN1ame\":\"Harold\", \"email\": \"elharo@macfaq.com\" }  ]}";
//		String str = "{\"ACTION_NAME\":\"FIND_ORDER\",\"CITY_CODE\":\"420100\",\"ACTION_INFO\":{\"CSN\":\"CB62EF2CDAF7BF920100\",\"ORDER_ID\":\"12382096\"},\"ACTION_INVOKER\":{\"PHONE\":\"\",\"IMEI\":\"863020015565892\",\"OSNAME\":\"Android\",\"OSVER\":\"4.0.4\",\"IMSI\":\"89860065171807211732\",\"VER\":\"18\",\"CSN\":\"CB62EF2CDAF7BF920100\",\"OSDESCRIPT\":\"MI-ONE C1\"}}";
//		String str = "{\"ACTION_NAME\":\"FIND_ORDER\",\"CITY_CODE\":\"420100\",\"ACTION_INFO\":{\"ORDER_ID\":\"12382102\"},\"ACTION_INVOKER\":{\"OSVER\":\"4.0.4\",\"PHONE\":\"\",\"IMSI\":\"89860065171807211732\",\"VER\":\"18\",\"OSDESCRIPT\":\"MI-ONE C1\",\"IMEI\":\"863020015565892\",\"OSNAME\":\"Android\"}}";
		Map map = JSONUtil.parseJSON2Map(str);
		System.out.println(map);
	}
}
