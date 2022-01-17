package com.foxfxb.interviewee.utils;

import com.foxfxb.interviewee.response.RestResponse;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用工具类
 *
 * @author: fengxianbo
 * @version: 1.0, 2017年7月11日
 */
public class CommonUtils {

	public static final String	DEFUALT_DOUBLE_FORMAT		= "#.##";

	public static final String	MONEY_FORMAT				= "#0.00";



	/**
	 * 判断一个对象是否为空
	 *
	 * @param obj
	 * @return boolean
	 * @author: Daniel
	 */
	@SuppressWarnings({ "rawtypes" })
	public static boolean isNull(Object obj) {
		if (obj == null) {
			return true;
		}
		if (obj instanceof String) {
			return obj.toString().length() <= 0;
		} else if (obj instanceof Collection) {
			return ((Collection) obj).isEmpty();
		} else if (obj instanceof Map) {
			return ((Map) obj).isEmpty();
		} else if (obj.getClass().isArray()) {
			return ((Object[]) obj).length == 0;
		} else {
			return obj == null;
		}
	}



	/**
	 * 判断多个参数是否有空值
	 *
	 * @param objs
	 * @return
	 * @author: Daniel
	 */
	public static boolean isNulls(Object... objs) {
		for (Object obj : objs) {
			boolean flag = isNull(obj);
			if (flag) {
				return flag;
			}
		}
		return false;
	}

	/**
	 * 将一个对象转换为字符串
	 *
	 * @param obj
	 * @return
	 * @author: Daniel
	 */
	public static String getString(Object obj) {
		return getString(obj, "");
	}

	/**
	 * 将一个对象转换为字符串
	 *
	 * @param obj
	 * @return
	 * @author: Daniel
	 */
	public static String getString(Object obj, String defaultValue) {
		return obj == null ? defaultValue : String.valueOf(obj);
	}



	/**
	 * 将集合的第一个对象转为字符串
	 *
	 * @param list
	 * @return
	 * @author: Daniel
	 */
	public static String get1stStr(List<?> list) {

		if (isNull(list)) {
			return "";
		} else {
			return getString(list.get(0));
		}
	}



	/**
	 * 尝试将一个对象转为Numer，如果失败则转为0
	 *
	 * @param obj
	 * @return
	 * @author: Daniel
	 */
	public static Number getNumber(Object obj) {
		if(isNull(obj)){
			return 0;
		}
		String s = getString(obj);
		try {
			return s.matches("(-|\\+)?\\d+(.\\d+)?") ? NumberFormat.getInstance().parse(s) : 0;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}



	public static Integer getInteger(Object obj) {
		return getNumber(obj).intValue();
	}



	/**
	 * 尝试将一个对象转为Double，如果失败则转为0.0
	 *
	 * @param obj
	 * @return
	 * @author: Daniel
	 */
	public static Double getDouble(Object obj) {
		return getNumber(obj).doubleValue();
	}



	/**
	 * 将字符串格式为double的形式显示，默认格式为#.##
	 *
	 * @param str
	 * @return
	 * @author: Daniel
	 */
	public static String formatDouble(String str) {
		return formatDouble(str, DEFUALT_DOUBLE_FORMAT);
	}



	/**
	 * 自定义格式，将字符串格式为double的形式显示
	 *
	 * @param str
	 * @param format
	 * @return
	 * @author: Daniel
	 */
	public static String formatDouble(String str, String format) {
		return formatNumber(str, format);
	}



	/**
	 * 将字符串格式为Number的形式显示
	 *
	 * @param str
	 * @param format
	 * @return
	 * @author: Daniel
	 */
	public static String formatNumber(String str, String format) {

		DecimalFormat df = new DecimalFormat(format);
		Number n = getNumber(str);
		if (n.doubleValue() == 0.0) {
			n = 0;
		}
		String f = df.format(n);
		if (getNumber(f).doubleValue() == 0.0) {
			n = 0;
			f = df.format(n);
		}
		return f;
	}



	/**
	 * 将double格式为double的格式显示
	 *
	 * @param number
	 * @param format
	 * @return
	 * @author: Daniel
	 */
	public static String formatDouble(Double number, String format) {
		return formatDouble(getString(number), format);
	}

	public static String formatDouble(Float number, String format) {
		return formatDouble(getString(number), format);
	}



	/**
	 * 将double格式为double的形式显示，默认格式为#.##
	 *
	 * @param number
	 * @return
	 * @author: Daniel
	 */
	public static String formatDouble(Double number) {
		return formatDouble(getString(number));
	}



	/**
	 * 将字符串格式为#0.00显示
	 *
	 * @param money
	 * @return
	 * @author: Daniel
	 */
	public static String formatMoney(String money) {
		return formatDouble(money, MONEY_FORMAT);
	}



	/**
	 * 将double格式为#0.00显示
	 *
	 * @param money
	 * @return
	 * @author: Daniel
	 */
	public static String formatMoney(Double money) {
		return formatMoney(getString(money));
	}



	/**
	 * 将字符串里的中文，转换为unicode
	 *
	 * @param str
	 * @return
	 */
	public static String utf82Unicode(String str) {
		String result = "";
		for (int i = 0; i < str.length(); i++) {
			int chr1 = str.charAt(i);
			if (chr1 >= 19968 && chr1 <= 171941) {// 汉字范围 \u4e00-\u9fa5 (中文)
				result += "\\u" + Integer.toHexString(chr1);
			} else {
				result += str.charAt(i);
			}
		}
		return result;
	}



	/**
	 * 将字符串里的unicode，转换为中文
	 *
	 * @param str
	 * @return
	 */
	public static String unicode2Utf8(String str) {

		StringBuilder string = new StringBuilder();
		String[] hex = str.split("\\\\u");
		string.append(hex[0]);
		for (int i = 1; i < hex.length; i++) {

			String other = "";
			if (hex[i].length() > 4) {
				other = hex[i].substring(4);
				hex[i] = hex[i].substring(0, 4);
			}
			int data = Integer.parseInt(hex[i], 16);
			// 追加成string
			string.append((char) data).append(other);
		}

		return string.toString();
	}



	/**
	 * 将正则匹配得到的最后一个字符串转换为int
	 *
	 * @param text
	 * @param reg
	 * @return
	 * @author: Daniel
	 */
	public static int getLastRegInt(String text, String reg) {

		int num = 0;
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(text);

		while (m.find()) {
			String s = m.group(1);
			num = Integer.parseInt(s);
		}

		return num;
	}


	/**
	 *
	 * @param o
	 * @return
	 * @author: Daniel
	 */
	public static String trim(Object o) {
		return isNull(o) ? "" : getString(o).trim();
	}

	public static <T> String connectList2Str(List<T> list, char c) {
		return connectList2Str(list, String.valueOf(c));
	}

	public static <T> String connectList2Str(List<T> list, String c) {
		if (isNull(list)) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (T s : list) {
			sb.append(s).append(c);
		}

		if (sb.length() > 0) {
			return sb.substring(0, sb.lastIndexOf(c));
		}
		return sb.toString();
	}


	public static String substring(String text, int begin, int end){
		if(isNull(text)){
			return "";
		}
		int length = text.length();
		if(begin > length - 1){
			return "";
		}
		if(end > length -1){
			end = length - 1;
		}
		if(begin < end) {
			return text.substring(begin, end);
		}else{
			return text.substring(begin);
		}
	}

	/**
	 * 截取长度为length的字符串
	 * @param text
	 * @param length
	 * @return
	 */
	public static String substring(String text, int length){
		if(isNull(text)){
			return "";
		}
		if(length <= text.length()){
			return substring(text, 0, length - 1);
		}else{
			return text;
		}
	}

	/**
	 * 从startIndex开始，截取字符串
	 * @param text
	 * @param startIndex
	 * @return
	 */
	public static String subText(String text, int startIndex){
		return substring(text, startIndex, -1);
	}

	public static boolean equals(Object obj1, Object obj2){
		if(obj1 == obj2){
			return true;
		}else if(obj1 != null && obj1.equals(obj2)){
			return true;
		}else if(obj2 != null && obj2.equals(obj1)){
			return true;
		}
		return false;
	}

	public static Map<String, String> parseUriParams(String uri){
		Map<String, String> map = new LinkedHashMap<>();
		if(isNull(uri)){
			return map;
		}
		String[] paramArr = uri.split("&");
		for(String str : paramArr){

			String key = str.split("=")[0];
			String val = str.split("=")[1];
			map.put(key, val);
		}
		return map;
	}

	public static String parseUri(String host, Map<String, String> map){

		if(isNull(map)){
			return host;
		}
		StringBuilder sb = new StringBuilder()
				.append(host);
		if(!host.contains("?")){
			sb.append("?");
		}
		Iterator<String> it = map.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			String value = map.get(key);
			sb.append("&")
				.append(key)
				.append("=")
				.append(value);
		}
		return sb.toString();
	}

    public static boolean contains(String s1, String s2) {
		if(isNull(s1) || isNull(s2)){
			return false;
		}
		return s1.indexOf(s2) >= 0;
    }

	public static String hidePhone(String phone){
		if(isNull(phone)){
			return "-";
		}
		return substring(phone, 0, 3) + "****" + subText(phone, 7);
	}

	public static String hideEmail(String email){
		if(isNull(email)){
			return "-";
		}
		String[] email2 = email.split("@");
		if(email2[0].length() < 2){
			return email;
		}
		return email2[0].substring(0, 1) + "*@" + email2[1];
	}

	public static String encodeHtml(String html){
		return html.replaceAll("&", "&amp;")
				.replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;")
				.replaceAll("\"", "&quot;");
	}
}
