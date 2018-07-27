package com.core.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.ListOrderedMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created by WJ on 2018/5/15
 */
public class JsonUtil {

	public static Map<String, Object> toMap(Object data, String return_code, String return_message) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (data == null) {
			data = new HashMap<String, Object>();
		}
		map.put("rtData", data);
		map.put("rtMessage", return_message);
		map.put("rtCode", return_code);
		return map;
	}

	/**
	 * 返回json格式data
	 * 
	 * @param data
	 * @param return_code
	 * @param return_message
	 * @return
	 */
	public static String toJson(Object data, String return_code, String return_message) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (data == null) {
			data = new HashMap<String, Object>();
		}
		map.put("rtData", data);
		map.put("rtMessage", return_message);
		map.put("rtCode", return_code);
		JSONObject rtInfo = JSONObject.fromObject(map);
		return rtInfo.toString();
	}

	/**
	 * 返回json格式data
	 * 
	 * @param data
	 * @param return_code
	 * @param return_message
	 * @return
	 */
	public static String toJson(String return_code, String return_message) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("rtMessage", return_message);
		map.put("rtCode", return_code);
		JSONObject rtInfo = JSONObject.fromObject(map);
		return rtInfo.toString();
	}

	/**
	 * 
	 * json转换list. <br>详细说明 @param jsonStr json字符串 @return @return
	 * List<Map<String,Object>> list @throws
	 */
	public static List<Map<String, String>> parseJSON2List(String jsonStr) {
		JSONArray jsonArr = JSONArray.fromObject(jsonStr);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		@SuppressWarnings("unchecked")
		Iterator<JSONObject> it = jsonArr.iterator();
		while (it.hasNext()) {
			JSONObject json2 = it.next();
			list.add(parseJSON2Map(json2.toString()));
		}
		return list;
	}

	/**
	 * 
	 * json转换map. <br>
	 * 详细说明
	 * 
	 * @param jsonStr
	 *            json字符串
	 * @return
	 * @return Map<String,Object> 集合
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseJSON2Map(String jsonStr) {
		ListOrderedMap map = new ListOrderedMap();
		// 最外层解析
		JSONObject json = JSONObject.fromObject(jsonStr);
		for (Object k : json.keySet()) {
			Object v = json.get(k);
			// 如果内层还是数组的话，继续解析
			if (v instanceof JSONArray) {
				List<Map<String, String>> list = new ArrayList<Map<String, String>>();
				Iterator<JSONObject> it = ((JSONArray) v).iterator();
				while (it.hasNext()) {
					JSONObject json2 = it.next();
					list.add(parseJSON2Map(json2.toString()));
				}
				map.put(k.toString(), list);
			} else {
				map.put(k.toString(), v);
			}
		}
		return map;
	}
}
