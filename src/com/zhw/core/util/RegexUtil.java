package com.zhw.core.util;

public class RegexUtil {
	/**
	 * 将字符串转化为正则表达式
	 * 
	 * @param s
	 * @return
	 */
	public static String toRegex(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (MyStringUtil.isLetter(c)) {
				sb.append("[");
				sb.append(MyStringUtil.toLowerCase(c));
				sb.append(MyStringUtil.toUpperCase(c));
				sb.append("]");
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 将指定的文件名格式转换为正则表达式
	 * 
	 * @param str
	 * @return
	 */
	public static String convert2regEx(String str) {
		String regExp = str;
		regExp = regExp.replace('.', '#');
		regExp = regExp.replaceAll("#", "\\\\.");
		regExp = regExp.replace('*', '#');
		regExp = regExp.replaceAll("#", ".*");
		regExp = regExp.replace('?', '#');
		regExp = regExp.replaceAll("#", ".?");
		regExp = "^" + regExp + "$";
		return regExp;
	}
}
