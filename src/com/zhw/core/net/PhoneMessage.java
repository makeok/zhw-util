package com.zhw.core.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

import com.alibaba.fastjson.JSONObject;
import com.zhw.core.util.MyDateUtil;
import com.zhw.core.util.MyStringUtil;



/*
 * bmobSmsMsg 短信接口由bmob提供
 * baiduSmsSend1069 百度短信接口
 */
public class PhoneMessage {


	private static String APP_ID = "";//填写自己的APP_ID和API_Key
	private static String API_Key = "";



	/**
	 * @param urlAll
	 *            :请求接口
	 * @param httpArg
	 *            :参数
	 * @return 返回结果
	 */
	public static String request(String httpUrl, String httpArg) {
	    BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();
	    httpUrl = httpUrl + "?" + httpArg;

	    try {
	        URL url = new URL(httpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setRequestMethod("GET");
	        // 填入apikey到HTTP header
	        connection.setRequestProperty("apikey",  "");//填写自己的apikey
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	        result = sbf.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}
	public static void baiduSmsSend1069(){
		String httpUrl = "http://apis.baidu.com/106908api/qxtsms/106api";
		String httpArg = "username=donglan&mobile=15528265851&content=%E6%82%A8%E5%BD%93%E5%89%8D%E7%9A%84%E9%AA%8C%E8%AF%81%E7%A0%81%E4%B8%BA2619%2C%E8%AF%B7%E4%BD%BF%E7%94%A8%E6%94%B6%E5%88%B0%E7%9A%84%E9%AA%8C%E8%AF%81%E7%A0%81%E7%99%BB%E5%BD%95%E3%80%90%E5%8D%83%E6%A9%99%E7%A7%91%E6%8A%80%E3%80%91";
		String jsonResult = request(httpUrl, httpArg);
		System.out.println(jsonResult);
	}
	public static void main(String args[]){
//		bmobSmsMsg("15528265851","nihao",null);
//		String SMSCODE = bmobSmsCode("15528265851");
		String SMSCODE = "6421733";
		bmobSmsCheck(SMSCODE);
		bmobSmsCodeCheck("240940","15528265851");
		bmobSmsCheck(SMSCODE);
	}

	//短信消息发送
	public static void bmobSmsMsg(String mobilePhoneNumber,String content,Date sendTime){
//		你还可以选择定时发送，比如未来的某一时刻给某个手机发送一条短信，sendTime的格式必须是YYYY-mm-dd HH:ii:ss， 如: 2015-05-26 12:13:14，请求如下：
//		curl -X POST \
//		  -H "X-Bmob-Application-Id: Your Application ID"          \
//		  -H "X-Bmob-REST-API-Key: Your REST API Key"        \
//		  -H "Content-Type: application/json" \
//		  -d '{"mobilePhoneNumber": "186xxxxxxxx", "content":"您的验证码是：222222, 有效期是10分钟。感谢您使用Bmob。", "sendTime":"2016-05-26 12:13:14"}' \
//		  https://api.bmob.cn/1/requestSms
//		成功返回，短信验证码ID，可用于后面使用查询短信状态接口来查询该条短信是否发送成功：
//		{
//		    "smsId": 1232222
//		}
		
		try {
			String API_URL = "https://api.bmob.cn/1/requestSms";
			
		    JSONObject data = new JSONObject();
		    data.put("mobilePhoneNumber", mobilePhoneNumber);
		    data.put("content", content);
		    if(sendTime != null){
		    	data.put("sendTime", MyDateUtil.format_df19(sendTime));
		    }	    
	    
			String responseText = bmobRequest(API_URL,data.toString(),"POST");			
		    System.out.println(responseText.toString());  
		    
		    JSONObject result = JSONObject.parseObject(responseText.toString());
		    String validNum = result.getString("smsId");
		    System.out.println(validNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//短信验证码
	public static String bmobSmsCode(String phoneNum){
		String validNum = "";
		try {
			String API_URL = "https://api.bmob.cn/1/requestSmsCode";
			
		    JSONObject data = new JSONObject();
		    data.put("mobilePhoneNumber", phoneNum);
		    data.put("template", "sms");
	    
	    
			String responseText = bmobRequest(API_URL,data.toString(),"POST");			
		    System.out.println(responseText.toString());  
		    
		    JSONObject result = JSONObject.parseObject(responseText.toString());
		    validNum = result.getString("smsId");
		    System.out.println(validNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return validNum;
	}
	
	//短信验证码验证
	public static boolean bmobSmsCodeCheck(String SMSCODE,String phoneNum){
		boolean out = false; 
		try {
			if(MyStringUtil.isEmpty(SMSCODE)){
				return out;
			}
			String API_URL = "https://api.bmob.cn/1/verifySmsCode/"+SMSCODE;
			
		    JSONObject data = new JSONObject();
		    data.put("mobilePhoneNumber", phoneNum);
	    
			String responseText = bmobRequest(API_URL,data.toString(),"POST");			
		    System.out.println(responseText.toString());  
		    
		    JSONObject result = JSONObject.parseObject(responseText.toString());
		    String validNum = result.getString("msg");
		    if(validNum.equals("ok")){
		    	out = true;	
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out;
	}
	
	public static void bmobSmsCheck(String smsId){
		try {
			String API_URL = "https://api.bmob.cn/1/querySms/"+smsId;
			
//		    JSONObject data = new JSONObject();
//		    data.put("mobilePhoneNumber", phoneNum);
	    
			String responseText = bmobRequest(API_URL,"","GET");			
		    System.out.println(responseText.toString());  
		    
		    JSONObject result = JSONObject.parseObject(responseText.toString());
		    String sms_state = result.getString("sms_state");
		    String verify_state = result.getString("verify_state");
		} catch (Exception e) {
			e.printStackTrace();
		}
//		通过以下接口，你可以查询某条短信是否发送成功，如果是使用了Bmob的模板的话还能查询到是否被验证过，其实:smsId是请求短信验证码API返回的smsId值：
//
//		curl -X GET \
//		  -H "X-Bmob-Application-Id: Your Application ID"          \
//		  -H "X-Bmob-REST-API-Key: Your REST API Key"        \
//		  https://api.bmob.cn/1/querySms/:smsId
//		成功返回以下JSON：
//
//		{
//		  "sms_state": "SENDING", 
//		  "verify_state": false
//		}
//		其中sms_state是发送状态，有值: SENDING-发送中，FAIL-发送失败 SUCCESS-发送成功 其中verify_state是验证码是否验证状态， 有值: true-已验证 false-未验证
//
//		注意事项
//
//		关于短信条数的计算规则如下:
//
//		实际计算的短信字数 = 模板的内容或自定义短信的内容字数 + 5。加上5是因为默认的签名【云验证】占了5个字。
//		实际计算的短信字数在70个字以下算1条。
//		实际计算的短信字数超过70字的以67字为一条来计算的。也就是135个字数是计算为3条的。
//		计算得到的短信条数在本条短信发送成功后将会从你的账户剩余的短信条数中扣除。
//		短信发送限制规则是1/分钟，5/小时，10/天。即对于一个应用来说，一天给同一手机号发送短信不能超过10条，一小时给同一手机号发送短信不能超过5条，一分钟给同一手机号发送短信不能超过1条
	}
	/** 
	 * 
	 * @return 
	 * @see <a href='http://docs.bmob.cn/restful/developdoc/index.html?menukey=develop_doc&key=develop_restful#index_添加数据'>例子</a> 
	 * */  
	public static String bmobRequest(String API_URL,String data,String method) throws Exception {
//		// 创建SSLContext对象，并使用我们指定的信任管理器初始化
//		TrustManager[] tm = { new MyX509TrustManager() };
//		SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
//		sslContext.init(null, tm, new java.security.SecureRandom());
//		// 从上述SSLContext对象中得到SSLSocketFactory对象
//		SSLSocketFactory ssf = sslContext.getSocketFactory();
					
	    //构建请求  
	    URL postUrl = new URL(API_URL);  
	  
	    HttpsURLConnection con = (HttpsURLConnection) postUrl.openConnection();//打开连接   
//	    con.setSSLSocketFactory(ssf);
	    
	    con.setRequestMethod(method);//post方式提交  	  
	    con.setDoOutput(true);//打开读写属性，默认均为false   
	    con.setDoInput(true);  
	    con.setUseCaches(false);//Post请求不能使用缓存   
//	    con.setInstanceFollowRedirects(true);  
	  
//		if ("GET".equalsIgnoreCase(method))
//			con.connect();

	    //添加头信息  
	    con.setRequestProperty("X-Bmob-Application-Id", APP_ID);  
	    con.setRequestProperty("X-Bmob-REST-API-Key", API_Key);  
	    con.setRequestProperty("Content-Type", "application/json");  
	    
	    if(!MyStringUtil.isEmpty(data)){
		    //发送请求  
		    DataOutputStream out = new DataOutputStream(con.getOutputStream());
		    out.write(data.getBytes("UTF-8"));
		    out.flush();  
		    out.close();  
	    }
	  
	    //接收数据  
	    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));  
	    String line;  
	    StringBuffer responseText = new StringBuffer();  
	    while ((line = reader.readLine()) != null) {  
	        responseText.append(line);  
	    }  
	    reader.close();  
	    con.disconnect();  
  
	    return responseText.toString();
	}  
}

