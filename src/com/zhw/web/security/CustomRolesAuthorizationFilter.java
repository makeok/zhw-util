package com.zhw.web.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;


/**
 * 自定义shiro的role-or关系的权限标识
 	需要在applicationContext-shiro.xml配置
 	<!--自定义的filter-->
	<bean id="roleOrFilter" class="com.eliteams.quick4j.web.security.CustomRolesAuthorizationFilter"> 
	</bean>
	定义权限过滤
   <bean id="shiroFilter" class="org.apache.shiro.spring.web.ShiroFilterFactoryBean">
        <property name="securityManager" ref="securityManager"/>
        <property name="loginUrl" value="/login.jsp"/>
        <property name="successUrl" value="/index.jsp"/>
        <property name="unauthorizedUrl" value="/401.jsp"/>
  		<property name="filters">  
            <map>  
                <entry key="roleOrFilter" value-ref="roleOrFilter"/>
                <entry key="myAuchc" value-ref="myAuchc"/>
            </map>
        </property>  
        <property name="filterChainDefinitions">
            <value>
				/app/sysMoney/** = authc, roleOrFilter[admin,accountant]

 * @author Administrator
 *
 */
public class CustomRolesAuthorizationFilter extends AuthorizationFilter
{

	 @Override
	 protected boolean isAccessAllowed(ServletRequest request,
	   ServletResponse response, Object mappedValue) throws Exception
	 {
	  Subject subject = getSubject(request, response);
	        String[] rolesArray = (String[]) mappedValue;  
	  
	        if (rolesArray == null || rolesArray.length == 0) 
	        {  
	            return true;  
	        }  
	        for(int i=0;i<rolesArray.length;i++)
	        {
	            if(subject.hasRole(rolesArray[i]))
	            {  
	                return true;  
	            }  
	        }  
	        return false;  
	 }
}