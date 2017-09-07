package com.zhw.core.map;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhw.core.net.HttpRequestUtil;



public class TrackPointConvertor {
	public static JSONArray convert(Map<String, FyPoint> points){
		List pointsList = new ArrayList();
		StringBuffer sb = new StringBuffer();
		Iterator itr = points.values().iterator();
		String strResult= "{"+"\""+"status"+"\""+":0,"+"\""+"result"+"\""+":[";
		while(itr.hasNext()){
			FyPoint fp = (FyPoint)itr.next();
			//sb.append(fp.lat+","+fp.lon+";");
			String a=EvilTransform.transform(fp.lon,fp.lat, 0, 0);
	        String b[] = a.split("###");  
	        strResult=strResult+"{"+"\""+"x"+"\""+":"+b[0]+","+"\""+"y"+"\""+":"+b[1]+"}";
	       // System.out.println(b[0]);  
	       // System.out.println(b[1]); 
		}
		strResult=strResult+"]}";
		/*for(FyPoint fp:points){
			sb.append(fp.lat+","+fp.lon+";");
		}*/
/*		String sbsString=sb.toString();
		if (sbsString.length() >2)sbsString = sbsString.substring(0, sbsString.length()-1);
//		System.err.println("sb:"+sbsString);
		//String xyUrl = "http://api.map.baidu.com/geoconv/v1/?coords=114.21892734521,29.575429778924;114.21892734521,29.575429778924;114.21892734521,29.575429778924;117.126575995835,36.6702207308909&from=1&to=5&ak=EF06cfb26173665ad80b8edf6a328192";
		String xyUrl = "http://api.map.baidu.com/geoconv/v1/?coords="+sbsString+"&from=1&to=5&ak=EF06cfb26173665ad80b8edf6a328192";
		//String xyUrl = "http://api.map.baidu.com/geoconv/v1/?";
		//"coords="+sb.toString()+"&from=1&to=5&ak=EF06cfb26173665ad80b8edf6a328192";
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("from", "1");
		jsonParam.put("to", "5");
		jsonParam.put("ak", "EF06cfb26173665ad80b8edf6a328192");
		jsonParam.put("coords", sb.toString());
		JSONObject json = HttpRequestUtils.httpGet(xyUrl);*/
		 JSONObject jsonResult = null;
		 jsonResult = JSONObject.parseObject(strResult);
		 
		JSONObject json =jsonResult;
		if (json.getIntValue("status") == 0){
			JSONArray pos = json.getJSONArray("result");
			return pos;
		}else{
			System.err.println("status:"+json.getIntValue("status"));
			return null;
		}
	}
	
	public static JSONArray convertbaidu(Map<String, FyPoint> points){
		List pointsList = new ArrayList();
		StringBuffer sb = new StringBuffer();
		Iterator itr = points.values().iterator();
		while(itr.hasNext()){
			FyPoint fp = (FyPoint)itr.next();
			sb.append(fp.lat+","+fp.lon+";");
		}
		/*for(FyPoint fp:points){
			sb.append(fp.lat+","+fp.lon+";");
		}*/
		String sbsString=sb.toString();
		if (sbsString.length() >2)sbsString = sbsString.substring(0, sbsString.length()-1);
//		System.err.println("sb:"+sbsString);
		//String xyUrl = "http://api.map.baidu.com/geoconv/v1/?coords=114.21892734521,29.575429778924;114.21892734521,29.575429778924;114.21892734521,29.575429778924;117.126575995835,36.6702207308909&from=1&to=5&ak=EF06cfb26173665ad80b8edf6a328192";
		String xyUrl = "http://api.map.baidu.com/geoconv/v1/?coords="+sbsString+"&from=1&to=5&ak=EF06cfb26173665ad80b8edf6a328192";
		//String xyUrl = "http://api.map.baidu.com/geoconv/v1/?";
		//"coords="+sb.toString()+"&from=1&to=5&ak=EF06cfb26173665ad80b8edf6a328192";
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("from", "1");
		jsonParam.put("to", "5");
		jsonParam.put("ak", "EF06cfb26173665ad80b8edf6a328192");
		jsonParam.put("coords", sb.toString());
		JSONObject json = JSONObject.parseObject(HttpRequestUtil.httpRequest(xyUrl));
		if (json.getIntValue("status") == 0){
			JSONArray pos = json.getJSONArray("result");
			return pos;
		}else{
			System.err.println("status:"+json.getIntValue("status"));
			return null;
		}
	}
	public static FyPoint convert(double lonSrc, double latSrc){
		StringBuffer sb = new StringBuffer();
		sb.append(latSrc+","+lonSrc);
		String xyUrl = "http://api.map.baidu.com/geoconv/v1/?coords=114.21892734521,29.575429778924;114.21892734521,29.575429778924;114.21892734521,29.575429778924;117.126575995835,36.6702207308909&from=1&to=5&ak=EF06cfb26173665ad80b8edf6a328192";
		//String xyUrl = "http://api.map.baidu.com/geoconv/v1/?";
		//"coords="+sb.toString()+"&from=1&to=5&ak=EF06cfb26173665ad80b8edf6a328192";
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("from", "1");
		jsonParam.put("to", "5");
		jsonParam.put("ak", "EF06cfb26173665ad80b8edf6a328192");
		jsonParam.put("coords", sb.toString());
		JSONObject json = JSONObject.parseObject(HttpRequestUtil.httpRequest(xyUrl));
		if (json.getIntValue("status") == 0){
			JSONArray pos = json.getJSONArray("result");
			double x = pos.getJSONObject(0).getDouble("x");
			double y = pos.getJSONObject(0).getDouble("y");
			FyPoint p = new FyPoint(y, x);
			return p;
		}else{
			return null;
		}
	}
	public static void main(String[] args){
		/*
		 * 将gps坐标转为百度地图识别的坐标
		 */
		Date start = new Date();
		int t =1;
		int n = 100;
		for (int i = 0 ; i < t; i ++){
			/*FyPoint[] fps = new FyPoint[n];
			for (int j=0;j<n;j++){
			FyPoint p = new FyPoint(29.575429778924, 114.21892734521);//TrackPointConvertor.convert(29.575429778924, 114.21892734521);
			fps[j]=p;
			if (p != null){
				System.out.println("x:"+p.lat+",y:"+p.lon);;
			}
			}
			TrackPointConvertor.convert(fps);*/
		}
		Date end = new Date();
//		System.out.println((end.getTime()-start.getTime())/1000);
	}
}
