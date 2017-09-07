package com.zhw.core.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Map2Obj {
	public static Object map2obj(Map<String, Object> map, Class<?> beanClass)
            throws Exception {
        if (map == null)
            return null;
        Object obj = beanClass.newInstance();
        org.apache.commons.beanutils.BeanUtils.populate(obj, map);
        return obj;
    }

    public static Map<?, ?> obj2map(Object obj) {
        if (obj == null) {
            return null;
        }
        return new org.apache.commons.beanutils.BeanMap(obj);
    }
    
	public static List mapToObjectList(List<Map<String, Object>> maplist, Class<?> beanClass){
		List list = new ArrayList();
		for(int i=0;maplist!=null&&i<maplist.size();i++){
			try {
				list.add(mapToObject2(maplist.get(i),beanClass));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	public static List<Map<?, ?>> objectToMapList(List<?> objlist){
		List<Map<?, ?>> list = new ArrayList<Map<?, ?>>();
		for(int i=0;objlist!=null&&i<objlist.size();i++){
			try {
				list.add(objectToMap2(objlist.get(i)));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	/** 
	 * 使用org.apache.commons.beanutils进行转换 
	 */ 
    public static Object mapToObject1(Map<String, Object> map, Class<?> beanClass) throws Exception {    
        if (map == null)  
            return null;  
   
        Object obj = beanClass.newInstance();  
   
        org.apache.commons.beanutils.BeanUtils.populate(obj, map);  
   
        return obj;  
    }    
       
    public static Map<?, ?> objectToMap1(Object obj) {  
        if(obj == null)  
            return null;   
   
        return new org.apache.commons.beanutils.BeanMap(obj);  
    }    
       

   
	/** 
	 * 使用Introspector进行转换 
	 */ 
    public static Object mapToObject2(Map<String, Object> map, Class<?> beanClass) throws Exception {    
        if (map == null)   
            return null;    
   
        Object obj = beanClass.newInstance();  
   
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());    
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();    
        for (PropertyDescriptor property : propertyDescriptors) {  
            Method setter = property.getWriteMethod();    
            if (setter != null) {  
                setter.invoke(obj, map.get(property.getName()));   
            }  
        }  
   
        return obj;  
    }    
       
    public static Map<String, Object> objectToMap2(Object obj) throws Exception {    
        if(obj == null)  
            return null;      
   
        Map<String, Object> map = new HashMap<String, Object>();   
   
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());    
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();    
        for (PropertyDescriptor property : propertyDescriptors) {    
            String key = property.getName();    
            if (key.compareToIgnoreCase("class") == 0) {   
                continue;  
            }  
            Method getter = property.getReadMethod();  
            Object value = getter!=null ? getter.invoke(obj) : null;  
            map.put(key, value);  
        }    
   
        return map;  
    }    
       

   
	/** 
	 * 使用reflect进行转换 
	 */ 
    public static Object mapToObject3(Map<String, Object> map, Class<?> beanClass) throws Exception {    
        if (map == null)  
            return null;    
   
        Object obj = beanClass.newInstance();  
   
        Field[] fields = obj.getClass().getDeclaredFields();   
        for (Field field : fields) {    
            int mod = field.getModifiers();    
            if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){    
                continue;    
            }    
   
            field.setAccessible(true);    
            field.set(obj, map.get(field.getName()));   
        }   
   
        return obj;    
    }    
   
    public static Map<String, Object> objectToMap3(Object obj) throws Exception {    
        if(obj == null){    
            return null;    
        }   
   
        Map<String, Object> map = new HashMap<String, Object>();    
   
        Field[] declaredFields = obj.getClass().getDeclaredFields();    
        for (Field field : declaredFields) {    
            field.setAccessible(true);  
            map.put(field.getName(), field.get(obj));  
        }    
   
        return map;  
    }   
}
