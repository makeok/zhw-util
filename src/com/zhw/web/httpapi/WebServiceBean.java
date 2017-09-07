package com.zhw.web.httpapi;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.zhw.core.util.JSONResult;
import com.zhw.core.util.JsonUtil;
import com.zhw.core.util.MyStringUtil;



@Component
public class WebServiceBean {

	   /*
     * 处理函数
     */
	public String handleFunc(String funcname, HttpServletRequest request) {
		String retStr = "";
		
		Boolean success = false;
		Integer total = null;
		Object data = null;
		String msg = "";
		
		//调用方法
		try {
//			Method[] mms = this.getClass().getMethods();
//			for(Method each:mms){
//				Syslog.out(each.getName()+"\t"+each.getParameterTypes());
//			}			
			Method m = WebServiceBean.class.getMethod(funcname, HttpServletRequest.class);
			retStr = (String) m.invoke(this, request);
		} 
		catch (NoSuchMethodException e) {//没有找到方法
			e.printStackTrace();
			msg = "未知的请求["+funcname+"]";	
			retStr = JsonUtil.toJSONDef(new JSONResult(data, msg, success, total));
		}catch (Exception e) {
			e.printStackTrace();
			msg = e.getMessage();	
			retStr = JsonUtil.toJSONDef(new JSONResult(data, msg, success, total));
		}
	
		return retStr;
	}
    
    public boolean userLogin(HttpServletRequest request){
	   String username = request.getParameter("username");
	   String password = request.getParameter("password");
	   if(MyStringUtil.isEmptys(username,password))
	   {
		   return false;
	   }
		// 
		try {

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}    

	
	
}
