package com.zhw.web.security;



import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;


/*
 * 自定义shiro form认证
 */
public class CustomAuthcAuthorizationFilter extends FormAuthenticationFilter
{
	Log log = LogFactory.getLog(CustomAuthcAuthorizationFilter.class);
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		
	    if (isLoginRequest(request, response)) {
	        if (isLoginSubmission(request, response)) {
	            
				if (log.isTraceEnabled()) {
	                log.trace("Login submission detected.  Attempting to execute login.");
	            }
	            return executeLogin(request, response);
	        } else {
	            if (log.isTraceEnabled()) {
	                log.trace("Login page view.");
	            }
	            //allow them to see the login page ;)
	            return true;
	        }
	    } else {
	        if (log.isTraceEnabled()) {
	            log.trace("Attempting to access a path which requires authentication.  Forwarding to the " +
	                    "Authentication url [" + getLoginUrl() + "]");
	        }
	
//	        saveRequestAndRedirectToLogin(request, response);
	        saveRequest(request);	 
	        //自定义未经过授权的页面
	        HttpServletRequest hrequest = WebUtils.toHttp(request);
	        String reqUrl = hrequest.getRequestURI();
	        String loginUrl = getLoginUrl();
	        WebUtils.issueRedirect(request, response, loginUrl);
	        return false;
	    }
	}
}