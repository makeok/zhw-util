package com.zhw.core.util;

import java.util.List;

public class MyListUtil {
	public static Object getFirstFromList(List<?> list){
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public static boolean isEmpty(List<?> list){
		if(list!=null && list.size()>0){
			return false;
		}
		return true;
	}
}
