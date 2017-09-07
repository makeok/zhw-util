package com.zhw.web.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RequestSqlFilter  implements Filter{
	public class RequestWrapper implements HttpServletRequest{
		HttpServletRequest httpservletrequest;
		public RequestWrapper(){
	        super();
	    }
	 
	    public RequestWrapper(HttpServletRequest httpservletrequest) {
	        super();
	        this.httpservletrequest = httpservletrequest;
	    }
	 
	    public String[] getParameterValues(String s) {
	        String str[] = this.getParameterValues(s);
	        if (str == null) {
	            return null;
	        }
	        int i = str.length;
	        String as1[] = new String[i];
	        for (int j = 0; j < i; j++) {
	            as1[j] = cleanXSS(cleanSQLInject(str[j]));
	        }
	 
	        return as1;
	    }
	 
	    public String getParameter(String s) {
	        String s1 = this.getParameter(s);
	        if (s1 == null) {
	            return null;
	        } else {
	            return cleanXSS(cleanSQLInject(s1));
	        }
	    }
	 
	    public String getHeader(String s) {
	        String s1 = this.getHeader(s);
	        if (s1 == null) {
	            return null;
	        } else {
	            return cleanXSS(cleanSQLInject(s1));
	        }
	    }
	 
	    public String cleanXSS(String src) {
	        String temp =src;
	 
	        System.out.println("xss---temp-->"+src);
	    src = src.replaceAll("<", "<").replaceAll(">", ">");
	    // if (src.indexOf("address")==-1)
	    //  {
	     src = src.replaceAll("\\(", "(").replaceAll("\\)", ")");
	        //}
	    
	    src = src.replaceAll("'", "'");
	     
	    Pattern pattern=Pattern.compile("(eval\\((.*)\\)|script)",Pattern.CASE_INSENSITIVE);  
	      Matcher matcher=pattern.matcher(src);  
	      src = matcher.replaceAll("");
	 
	      pattern=Pattern.compile("[\\\"\\'][\\s]*javascript:(.*)[\\\"\\']",Pattern.CASE_INSENSITIVE); 
	      matcher=pattern.matcher(src);
	      src = matcher.replaceAll("\"\"");
	       
	      //增加脚本 
	      src = src.replaceAll("script", "").replaceAll(";", "")
	        .replaceAll("\"", "").replaceAll("@", "")
	        .replaceAll("0x0d", "")
	        .replaceAll("0x0a", "").replaceAll(",", "");
	 
	        if(!temp.equals(src)){
	            System.out.println("输入信息存在xss攻击！");
	            System.out.println("原始输入信息-->"+temp);
	            System.out.println("处理后信息-->"+src);
	        }
	        return src;
	    }
	     
	    //需要增加通配，过滤大小写组合
	    public String cleanSQLInject(String src) {
	        String temp =src;
	    src = src.replaceAll("insert", "forbidI")
	        .replaceAll("select", "forbidS")
	        .replaceAll("update", "forbidU")
	        .replaceAll("delete", "forbidD")
	        .replaceAll("and", "forbidA")
	        .replaceAll("or", "forbidO");
	     
	        if(!temp.equals(src)){
	            System.out.println("输入信息存在SQL攻击！");
	            System.out.println("原始输入信息-->"+temp);
	            System.out.println("处理后信息-->"+src);
	        }
	        return src;
	    }

		@Override
		public Object getAttribute(String name) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Enumeration getAttributeNames() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getCharacterEncoding() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public int getContentLength() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public String getContentType() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ServletInputStream getInputStream() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Enumeration getParameterNames() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Map getParameterMap() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getProtocol() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getScheme() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getServerName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getServerPort() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public BufferedReader getReader() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getRemoteAddr() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getRemoteHost() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setAttribute(String name, Object o) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void removeAttribute(String name) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Locale getLocale() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Enumeration getLocales() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isSecure() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public RequestDispatcher getRequestDispatcher(String path) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getRealPath(String path) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getRemotePort() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public String getLocalName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getLocalAddr() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getLocalPort() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public String getAuthType() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Cookie[] getCookies() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getDateHeader(String name) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Enumeration getHeaders(String name) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Enumeration getHeaderNames() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getIntHeader(String name) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public String getMethod() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getPathInfo() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getPathTranslated() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getContextPath() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getQueryString() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getRemoteUser() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isUserInRole(String role) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Principal getUserPrincipal() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getRequestedSessionId() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getRequestURI() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public StringBuffer getRequestURL() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getServletPath() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public HttpSession getSession(boolean create) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public HttpSession getSession() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isRequestedSessionIdValid() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isRequestedSessionIdFromCookie() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isRequestedSessionIdFromURL() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isRequestedSessionIdFromUrl() {
			// TODO Auto-generated method stub
			return false;
		}
	}
	public void doFilter(ServletRequest servletrequest,
            ServletResponse servletresponse, FilterChain filterchain)
            throws IOException, ServletException {
         
 
        //flag = true 只做URL验证; flag = false 做所有字段的验证;
        boolean flag = true;
        if(flag){
            //只对URL做xss校验
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletrequest;
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletresponse;
             
            String requesturi = httpServletRequest.getRequestURL().toString();
            requesturi = URLDecoder.decode(requesturi, "UTF-8");
            if(requesturi!=null&&requesturi.indexOf("alipay_hotel_book_return.html")!=-1){
                filterchain.doFilter(servletrequest, servletresponse);
                return;
            }
            if(requesturi!=null&&requesturi.indexOf("account_bank_return.html")!=-1){
                filterchain.doFilter(servletrequest, servletresponse);
                return;
            }
            if(requesturi!=null&&requesturi.indexOf("/alipay/activity.html")!=-1){
                filterchain.doFilter(servletrequest, servletresponse);
                return ;
            }
            if(requesturi!=null&&requesturi.indexOf("/alipayLogin.html")!=-1){
                filterchain.doFilter(servletrequest, servletresponse);
                return ;
            }
            RequestWrapper rw = new RequestWrapper(httpServletRequest);
            String param = httpServletRequest.getQueryString();
            if(!"".equals(param) && param != null) {
                param = URLDecoder.decode(param, "UTF-8");
                String originalurl = requesturi + param;
                 
                String sqlParam = param;
                //添加sql注入的判断
                if(requesturi.endsWith("/askQuestion.html") || requesturi.endsWith("/member/answer.html")){
                    sqlParam = rw.cleanSQLInject(param);
                }
                 
                String xssParam = rw.cleanXSS(sqlParam);
                requesturi += "?"+xssParam;
                 
                 
                if(!xssParam.equals(param)){
                    System.out.println("requesturi::::::"+requesturi);
                    httpServletResponse.sendRedirect(requesturi);
                    System.out.println("no entered.");
//                  filterchain.doFilter(new RequestWrapper((HttpServletRequest) servletrequest), servletresponse);
                    return ;
                }
            }
            filterchain.doFilter(servletrequest, servletresponse);
        }else{
             
            //对请求中的所有东西都做校验，包括表单。此功能校验比较严格容易屏蔽表单正常输入，使用此功能请注意。
            filterchain.doFilter((ServletRequest) new RequestWrapper((HttpServletRequest) servletrequest), servletresponse);
        }
    }
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}
