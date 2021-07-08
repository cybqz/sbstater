package com.cyb.common.utils;

import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

/**
 * @author CYB
 */
public class MyStringUtil {
	
	public static String format(String format, Date date) {
		
		if(org.apache.commons.lang3.StringUtils.isBlank(format)) {
			format = "yyyyMMDDHHmmss";
		}
		if(date == null) {
			date = new Date();
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	public static Date parse(String format, Date date) {
		Date result = null;
		try {
			if(org.apache.commons.lang3.StringUtils.isBlank(format)) {
				format = "yyyyMMDDHHmmss";
			}
			if(date == null) {
				date = new Date();
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			result = dateFormat.parse(dateFormat.format(date));
		}catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 生成随机ID
	 * @param noStrikethrough ,不需要中划线（-）
	 * @param isTooUpperCase ,是否转大写
	 * @return
	 */
	public static String getPrimaryKey(boolean noStrikethrough, boolean isTooUpperCase){
		String s = UUID.randomUUID().toString();
		if(noStrikethrough){
			s = s.replace("-","");
		}
		if(isTooUpperCase){
			s = s.toUpperCase();
		}
		return s;
	}

	/**
	 * @Author 陈迎博
	 * @Title 获取当前日期
	 * @Description
	 * @Date 2021/1/17
	 */
	public static LocalDate getLocalDate(){
		return LocalDate.now();
	}

	/**
	 * 根据文件名称获取文件类型
	 * @param fullName
	 * @return
	 */
	public static String getFileTypeByFullName(String fullName){
		if(!org.apache.commons.lang3.StringUtils.isEmpty(fullName)){
			return fullName.substring(fullName.lastIndexOf("."), fullName.length());
		}
		return null;
	}

	public static String getByJSONObject(JSONObject object, String key){
		if(null != object && object.containsKey(key) && org.apache.commons.lang3.StringUtils.isNotBlank(object.getString(key))){
			return object.getString(key);
		}
		return null;
	}

	public static int count(String text,String tag){
		int count =0, start =0;
		while((start=text.indexOf(tag,start))>=0){
			start += tag.length();
			count ++;
		}
		return count;
	}
}
