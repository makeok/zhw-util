package com.zhw.web.httpapi;

//导入必需的 java 库
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhw.core.log.Syslog;
import com.zhw.core.util.JSONResult;
import com.zhw.core.util.JsonUtil;
import com.zhw.core.util.MyMathUtil;
import com.zhw.core.util.MyStringUtil;
import com.zhw.web.util.BeanUtil;



//扩展 HttpServlet 类
public class WebServiceServlet extends HttpServlet {

    public static Map<String,String> userAcckenMap = new HashMap<String,String>();//用户accessken列表
    public static Map<String,Long> acckenExpMap = new HashMap<String,Long>();//accessken 失效列表
    public static Long expires_in = 30*60*1000L;//30分钟
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	
	public void init() throws ServletException
	{
		// 执行必需的初始化
		String path = getServletContext().getRealPath("/");

		
//		Syslog.info("addShutdownHook.........");
//        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
//        	 
//            public void run() {
//                int n = 0;
//                while (n < 10) {
//                	Syslog.info(Thread.currentThread() + " ShutdownHook," + n++);
//                    try {
//                        TimeUnit.SECONDS.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }));
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	         throws ServletException, IOException
	{
		doRequest(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	         throws ServletException, IOException
	{
		doRequest(request, response);
	}
	
	public void destroy()
	{
	   // 什么也不做
		Syslog.out("servlet destroy....");
	}
	
	
	public void doRequest(HttpServletRequest request, HttpServletResponse response) throws IOException{
		   // 设置响应内容类型
		   request.setCharacterEncoding("UTF-8");
		   response.setCharacterEncoding("UTF-8");
		   response.setContentType("application/json;charset=UTF-8");
		   JSONResult ret = new JSONResult();
		   String retStr = JsonUtil.toJSONDef(ret);
		   
		   String path = request.getRequestURI();
		   String content = request.getContextPath();
//		   String [] pathsubs = path.split("/");
//		   String funcname = pathsubs[3];
//		   if(pathsubs.length > 3){
		   String funcname = path.replace(content+"/webservice/", "");
		   Syslog.info("网关接口被调用["+path+"],["+funcname+"] start...");
		   if(!MyStringUtil.isEmpty(funcname)){			   
			   //验证用户名密码
			   WebServiceBean wsb = BeanUtil.getBean("webServiceBean");//new WebServiceBean();
			   if(funcname.equalsIgnoreCase("userLogin")){	//用户登录
				   if(wsb.userLogin(request)){
				       //生成access_token
				       Long nowtime = System.currentTimeMillis();
				       String username = request.getParameter("username");
				       String access_token = MyMathUtil.getUUID();//UUID.nameUUIDFromBytes((username+nowtime).getBytes())+
				       userAcckenMap.put(username, access_token);//加入access——token
				       acckenExpMap.put(access_token, nowtime+expires_in);
				       Map<String,String> data = new HashMap<String,String>();//用户accessken列表
				       data.put("access_token", access_token);
				       data.put("expires_time", String.valueOf(nowtime+expires_in));
				       retStr = JsonUtil.toJSONDef(new JSONResult(data, "登录成功", true));  
				   }else{
					   ret.setMsg("登录失败.");
					   retStr = JsonUtil.toJSONDef(ret);
				   }
			   }
			   else if(funcname.equalsIgnoreCase("userLogout")){	//用户退出
				   if(checkAccessken(request)){			   
				       String username = request.getParameter("username");
				       acckenExpMap.remove(userAcckenMap.get(username));
				       userAcckenMap.remove(username);//移除access——token		       
				       retStr = JsonUtil.toJSONDef(new JSONResult(null, "退出成功", true));
				   }else{
					   ret.setMsg("access_token已失效.");
					   retStr = JsonUtil.toJSONDef(ret);
				   }			   
			   }
			   else{	   
				   retStr = wsb.handleFunc(funcname,request);
			   }
		   }
		   else{
			   ret.setMsg("未知的请求[]");
			   retStr = JsonUtil.toJSONDef(ret);
		   }
		   Syslog.info("网关接口被调用["+funcname+"] end...");
		   // 实际的逻辑是在这里
		   PrintWriter out = response.getWriter();
		   out.println(retStr);
		   out.flush();
		   out.close();
	}
	
    boolean checkAccessken(HttpServletRequest request){
    	return true;
//    	String username = request.getParameter("access_token");	   
//    	if(MyStringUtils.isEmptys(username))
//    	{
//    	   return false;
//    	}
//    	Long expires_time = acckenExpMap.get(username);
//    	if(expires_time!=null && expires_time>System.currentTimeMillis()){ //时间有效
//    	   return true;
//    	}
//    	return false;
	}
}