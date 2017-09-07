package com.zhw.web.security.dynAuth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.config.Ini;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.config.IniFilterChainResolverFactory;



/*
 * 
1、配置文件里配置的filterchains都是静态的，但实际开发中更多的是从数据库中动态的获取filterchains。
我们都知道ShiroFilterFactoryBean中的setFilterChainDefinitions()
是读取配置文件里默认的filterchains，所以我们的思路是重写这个方法，才能达到我们想要的目的：
配置文件需要做相应调整，如下：
<!-- <bean id="shiroFilter" class="org.apache.shiro.spring.web.ShiroFilterFactoryBean">  --> 
    <bean id="shiroFilter" class="com.zhw.web.security.dynAuth.ShiroPermissionFactory">
 */
public class ShiroPermissionFactory extends ShiroFilterFactoryBean {

	private permissService permissService;

	/** 记录配置中的过滤链 */
	//这里定义的一个静态属性definition，作用是记录下配置文件中的filterchains，这个属性在等下更新权限的时候会用到。
	public static String definition = "";

	public permissService getPermissService() {
		return permissService;
	}

	public void setPermissService(permissService permissService) {
		this.permissService = permissService;
	}

	/**
	 * 初始化设置过滤链
	 */
	@Override
	public void setFilterChainDefinitions(String definitions) {
		definition = definitions;// 记录配置的静态过滤链
		List<Permission> permissions = permissService.findAll();
		Map<String, String> otherChains = new HashMap<String, String>();
		permissions.forEach(permiss -> {
			// perms.add(e)
			otherChains.put(permiss.getPermissionUrl(), permiss.getPermissionvalue());
		});
		// 加载配置默认的过滤链
		Ini ini = new Ini();
		ini.load(definitions);
		Ini.Section section = ini.getSection(IniFilterChainResolverFactory.URLS);
		if (CollectionUtils.isEmpty(section)) {
			section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
		}
		// 加上数据库中过滤链
		section.putAll(otherChains);
		setFilterChainDefinitionMap(section);
	}

}
