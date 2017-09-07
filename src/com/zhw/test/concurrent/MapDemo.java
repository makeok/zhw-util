package com.zhw.test.concurrent;

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class MapDemo {
	
//	java.util.concurrent.ConcurrentNavigableMap 是一个支持并发访问的 java.util.NavigableMap，它还能让它的子 map 具备并发访问的能力。所谓的 "子 map" 指的是诸如 headMap()，subMap()，tailMap() 之类的方法返回的 map。
    public static void ConcurrentNavigableMap() throws InterruptedException {
    	ConcurrentNavigableMap map = new ConcurrentSkipListMap();  
    	  
    	map.put("1", "one");  
    	map.put("2", "two");  
    	map.put("3", "three");  
    	//headMap 将指向一个只含有键 "1" 的 ConcurrentNavigableMap，因为只有这一个键小于 "2"。
    	ConcurrentNavigableMap headMap = map.headMap("2");  
    	//tailMap 将拥有键 "2" 和 "3"，因为它们不小于给定键 "2"。
    	ConcurrentNavigableMap tailMap = map.tailMap("2"); 
    	//返回的 submap 只包含键 "2"，因为只有它满足不小于 "2"，比 "3" 小。
    	ConcurrentNavigableMap subMap = map.subMap("2", "3"); 
    	
    	// 获取降序的keyset
    	map.descendingKeySet();
    	// 获取降序的map
    	map.descendingMap();
    	// 获取升序的keyset
    	map.navigableKeySet();
    }
}
