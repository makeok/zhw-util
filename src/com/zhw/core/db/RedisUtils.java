package com.zhw.core.db;

import com.zhw.core.util.MyStringUtil;
import com.zhw.web.util.BeanUtil;

public class RedisUtils {
	/**
	 * 
	 */
	private static final Long	defaultTime	= 24 * 60 * 60L;
	
	public static void cacheR(String key, Object result) {
		RedisSupport sup = BeanUtil.getBean("redisSupport");
		sup.set(key, result, defaultTime);
	}
	
	/**
	 * 
	 * @param key
	 * @param result
	 * @param time 单位s
	 */
	public static void cacheR(String key, Object result, Long time) {
		RedisSupport sup = BeanUtil.getBean("redisSupport");
		sup.set(key, result, time);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T get(String key) {
		if(!MyStringUtil.isEmpty(key)){
			return null;
		}
		RedisSupport sup = BeanUtil.getBean("redisSupport");
		String r = (String)sup.get(key);
		return MyStringUtil.isEmpty(r) ? null : (T) r;
	}
	
	public static void del(String key){
		RedisSupport sup = BeanUtil.getBean("redisSupport");
		sup.delete(key);
	}
}
