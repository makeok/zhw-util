package com.zhw.core.util;

import java.sql.Timestamp;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

public class JsonUtil {  
    private static SerializeConfig mapping = new SerializeConfig();  
    private static String dateFormat;  
    static {  
        dateFormat = "yyyy-MM-dd HH:mm:ss";  
        mapping.put(Date.class, new SimpleDateFormatSerializer(dateFormat));
        mapping.put(Timestamp.class, new SimpleDateFormatSerializer(dateFormat));
    }  
  
    /** 
     * 默认的处理时间 
     *  
     * @param jsonText 
     * @return 
     */  
    public static String toJSON(Object jsonText) {  
        return JSON.toJSONString(jsonText,  
                SerializerFeature.WriteDateUseDateFormat);  
    }  
  
    /** 
     * 自定义时间格式 
     *  
     * @param jsonText 
     * @return 
     */  
    public static String toJSONDef(Object jsonText) {   
        return JSON.toJSONString(jsonText, mapping);  
    }  
    
    /** 
     * 自定义时间格式 
     *  
     * @param jsonText 
     * @return 
     */  
    public static String toJSON(Object jsonText,String format) {  
    	SerializeConfig map = new SerializeConfig();
    	map.put(Date.class, new SimpleDateFormatSerializer(format));
    	map.put(Timestamp.class, new SimpleDateFormatSerializer(format));
        return JSON.toJSONString(jsonText, map);  
    }  
 
    
    
}  