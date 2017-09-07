package com.zhw.core.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Properties;

public class MyPropertyUtil {

	public static synchronized String getValue(String filename,String key)
	{
		Properties prop = null;
		InputStream ins = null;
		String sVaule = null;
		try
		{
			prop = new Properties();
			ins = MyPropertyUtil.class.getClassLoader().getResourceAsStream(filename);
			// InputStream ins= this.getClass().getResourceAsStream(filename);
			prop.load(new InputStreamReader(ins,"UTF-8"));
			sVaule = prop.getProperty(key);
			ins.close();
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(prop != null){
				prop.clear();
			}
		}
		return sVaule;
	}

	/**
	 * 把key value pair组成的字符串数组转换为属性 如：fileName=a.txt
	 * 
	 * @param args
	 * @return
	 */
	public static Properties parseArgs(String[] args) {

		if (args == null || args.length < 1) {
			return EMPTY_PROPERTIES;
		}

		Properties p = new Properties();

		for (int i = 0; i < args.length; i++) {
			String[] strings = args[i].split("=");
			if(strings!=null && strings.length>0){
				p.setProperty(strings[0].trim(), strings[1].trim());
			}
		}
		return p;
	}
	/**
	 * 空属性 Properties() 对象
	 */
	public static final Properties EMPTY_PROPERTIES = new Properties();

	/**
	 * 把以空格分隔的key value pair组成的字符串转换为属性 如：fileName=a.txt taskId =123
	 * 
	 * @param args
	 * @return
	 */
	public static Properties parseArgs(String args) {
		if (args != null) {
			args = MyStringUtil.checkNull(args);
			return parseArgs(args.split(","));
		}
		return EMPTY_PROPERTIES;
	}
	
	public static String createProperty(Map<String,Object> data) {
		StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            sb.append(i.getKey()).append("=").append(i.getValue()+"").append(",");
        }
        if(sb.length() > 0){
        	sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
	}
}
