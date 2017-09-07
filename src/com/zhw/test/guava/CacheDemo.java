package com.zhw.test.guava;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.zhw.core.log.Syslog;

public class CacheDemo {
    

    private static LoadingCache<String,String> cahceBuilder=CacheBuilder
    .newBuilder()
	.maximumSize(10000)
	.expireAfterWrite(5, TimeUnit.MINUTES)
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

    //获取
    public static String getValue(final String key){
    	String value = cahceBuilder.getIfPresent(key);
		return value;
    }

    public static void main(String [] args) throws ExecutionException{
    	cahceBuilder.put("harry", "ssdded");
    	Syslog.out(cahceBuilder.get("harry"));
    	Syslog.out(cahceBuilder.size());
    	
    }
    
}
