package com.zhw.core.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class MyEncodeUtil {
	public static String getUtf8FromIso(String value) {
        try {
        	if(MyStringUtil.isEmpty(value)){
        		return "";
        	}
			return new String(value.getBytes("ISO-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
    }
	public static String decode2Utf8(String src) {
		String out = "";
		try {
			out = URLDecoder.decode(src,"utf-8");
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		return out;
	}
	// 去掉utf8开头的bom
	public static String unBomStr(String in){
		byte[] chararray = in.getBytes();
		int ch0 = chararray[0] & 0xFF;
		int ch1 = chararray[1] & 0xFF;
		int ch2 = chararray[2] & 0xFF;
		if (ch0 == 0xEF && ch1 == 0xBB && ch2 == 0xBF){
			byte[] bytearr = Arrays.copyOfRange(chararray, 3, chararray.length) ;
		    return new String(bytearr);
		} else{
			return in;
		}
	}
    //将map型转为请求参数型
    public static String map2para(Map<String,Object> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

	
	public static String unicode2Ascii(String str){
		Pattern pattern = Pattern.compile("\\&\\#(\\d+)"); 
		if (StringUtils.isEmpty(str)) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		Matcher m =pattern.matcher(str);
		
		while (m.find()){
			str=str.replace("&#"+m.group(1)+";", ""+(char)Integer.valueOf(m.group(1)).intValue());
		}
		
		return str;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(unicode2Ascii(">&#24212;&#29992;&#31995;&#32479;&#39564;&#25910;&#21051;&#30424;</span></p><p"));

	}
}
