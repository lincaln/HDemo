package com.emt.card.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * JSON数据转换工具类
 */
public class FastjsonUtil {

	/**
	 * 将任意类型转换为json字符串
	 * @param object 任意java类型
	 * @return
	 */
	public static String toJsonString(Object object){
		return JSON.toJSONString(object,SerializerFeature.WriteMapNullValue);
	}
	
	/**
	 * 将json对象转换为java对象
	 * @param json 对象
	 * @param clz 任意java对象的Class
	 * @return
	 */
	public static <T> T jsonToObject(JSONObject json, Class<T> clz){
		return JSON.parseObject(toJsonString(json), clz);
	}
	/**
	 * 将json集合转换为List
	 * @param array 集合
	 * @param clz 任意java对象的Class
	 * @return
	 */
	public static <T> List<T> jsonToList(JSONArray array, Class<T> clz){
		return JSON.parseArray(toJsonString(array), clz);
	}
	/**
	 * map集合转java集合
	 * @param list
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> toJavaList(
			List<Map<String,Object> > list, Class<T> clazz){
		String str=JSONArray.toJSONString(list);
		List<T> array =JSONArray.parseArray(str, clazz);
		return array;
	}
	
	/**
	 * 将java对象转为JSON
	 * @param obj
	 * @return
	 */
	public static JSONObject toJSONObject(Object obj){
		String str=toJsonString(obj);
		return JSONObject.parseObject(str);
	}
	/**
	 * 将list对象转为array
	 * @param obj
	 * @return
	 */
	public static JSONArray JSONArray(List<?> list){
		String str=toJsonString(list);
		return JSONArray.parseArray(str);
	}
	
	/**
	 * 通过json合并对象
	 * @param master
	 * @param follow
	 * @param clz
	 * @return
	 */
	public static <T> T merge(T master, T follow,Class<T> clz){
		JSONObject m=toJSONObject(master);
		JSONObject f=toJSONObject(follow);
		Set<String> keys=m.keySet();
		for (String key : keys) {
			Object value=m.get(key);
			if("null".equals(value)) {
			f.remove(key);
			}else {
			f.put(key, value);
			}
		}
		T r=jsonToObject(f, clz);
		return r;
	}
	
	/**
	 * 接口调用时返回json格式字符串
	 * @param resp HttpServletResponse
	 * @param jsonString json格式字符串
	 * @throws IOException
	 */
	public static void jsonResponse(HttpServletResponse resp,
			JSONObject jsonObject) throws IOException{
		resp.setContentType("application/json;charset=utf-8");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setCharacterEncoding("utf-8");
		PrintWriter writer = resp.getWriter();
		writer.write(jsonObject.toJSONString());
		writer.flush();
		writer.close();
	}
	
}
