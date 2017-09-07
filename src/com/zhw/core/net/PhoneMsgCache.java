package com.zhw.core.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.zhw.core.log.Syslog;
import com.zhw.core.util.IOUtil;
import com.zhw.core.util.MyPropertyUtil;
import com.zhw.core.util.MyStringUtil;


/*
 * 基于guava实现手机短信验证,包含过期验证
 * 短信发送基于聚合平台接口
 */
public class PhoneMsgCache {
	public static boolean SEND_PHONEMSG_FLG = false;
	public static int MSGCODE_TIMEOUT = 5;
    //配置您申请的KEY
    public static String APPKEY ="";
    public static String TPLID ="";
    public static String URL ="http://v.juhe.cn/sms/send";//请求接口地址
    
    public static String CONFIG_FILE= "conf.property";
    private static String COMPANY;  
    
	static{
		String timeout = MyPropertyUtil.getValue(CONFIG_FILE, "phonemsg.timeout");
		APPKEY = MyPropertyUtil.getValue(CONFIG_FILE, "phonemsg.APPKEY");
		TPLID = MyPropertyUtil.getValue(CONFIG_FILE, "phonemsg.TPLID");
		URL = MyPropertyUtil.getValue(CONFIG_FILE, "phonemsg.URL");
		String flg = MyPropertyUtil.getValue(CONFIG_FILE, "phonemsg.flg");
		try {
			MSGCODE_TIMEOUT = Integer.parseInt(timeout);
			if(!MyStringUtil.isEmpty(flg) && Integer.parseInt(flg) == 1){
				SEND_PHONEMSG_FLG = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Syslog.error("获取短信超时时间设置失败");
		}		
	}
    private static LoadingCache<String,String> cahceBuilder=CacheBuilder
    .newBuilder()
	.maximumSize(10000)
	.expireAfterWrite(MSGCODE_TIMEOUT, TimeUnit.MINUTES)
//	.removalListener(MY_LISTENER)
    .build(new CacheLoader<String, String>(){
        @Override
        public String load(String key) throws Exception {                  
            return key+" is not existed.";
        }        
    });
	
    public static void putValue(String key ,String value){
    	cahceBuilder.put(key, value);
    }
    /**
    * 生成随机数
    * 
    * */
    public static int randomInt(int from, int to) {
	    Random r = new Random();
	    return from + r.nextInt(to - from);
    }
    //生成短信验证码
    public static String createCode(){
    	//生成六位验证码
		String charValue = "";
		for (int i = 0; i < 6; i++) {
			char c = (char) (randomInt(0, 9) + '0');
			charValue += String.valueOf(c);
		}
		return charValue;
    }
    //获取短信验证码
    public static String getValue(final String key){
    	String value = cahceBuilder.getIfPresent(key);
		return value;
    }
    //发送短信验证码
    public static boolean sendMsgcode(String code,final String sessionid,String telno,boolean sendflg){
    	if(sendflg){
        	if(MyStringUtil.isEmpty(code)){
        		code = createCode();
        	}
        	if(PhoneMsgUtil.sendPhoneMsg(telno, code, COMPANY)){
	    		putValue(sessionid, code);
	    		return true;
	    	}
        	Syslog.error("发送短信失败["+sessionid+":"+telno+"]");
    		return false;
    	}
    	code = "123456";
    	putValue(sessionid, code);
    	return true;
    }
    //验证短信验证码
    public static boolean validMsgcode(final String sessionid,String code){
    	String value = cahceBuilder.getIfPresent(sessionid);
    	if(value!=null && code!=null && value.equals(code)){
    		return true;
    	}
		return false;
    }

    public static void main(String [] args) throws ExecutionException{
//    	cahceBuilder.put("harry", "ssdded");
//    	Syslog.out(cahceBuilder.get("harry"));
//    	Syslog.out(cahceBuilder.size());
    	
    	String sessionid = "12345678";
    	sendMsgcode("",sessionid, "15528265851",true);
    	String str = IOUtil.readDataFromConsole("Please input string：");
    	Syslog.out(validMsgcode(sessionid, str));
    }
    
}
