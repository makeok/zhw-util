package com.zhw.core.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhw.core.log.Syslog;

public class OracleUtil {
	private static boolean firstFlg = true;
	//链接地址
	private String url;
	//用户名
	private String user;
	//密码
	private String password;
	//DB名
	private String db;
	
	public Connection con;
	public Statement pre;
	public ResultSet rs;
	
	private String status = "0";
	private int err_code = 0;
	public String fail_msg = "";
	
	public OracleUtil(String url, String user, String password, String db) {
		this.url = url;
		this.user = user;
		this.password = password;
		this.db = db;
		
		getConnnect(url , user, password, db);
	}
	
	private void getConnnect(String url, String user, String password, String db) {
		
		try {
			if(firstFlg){
				Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
				firstFlg = false;
			}
			
			Syslog.debug("数据库开始链接！");			
			String urlStr = "jdbc:oracle:thin:@" + url + ":1521:" +db;//链接地址
			String userStr = user;//登录名
			String passwordStr = password;//密码	
			DriverManager.setLoginTimeout(3);
			con = DriverManager.getConnection(urlStr, userStr, passwordStr);// 获取连接			 
			pre = con.createStatement();
			status = "1";
			
			Syslog.debug("连接成功！");
		} catch (Exception e) {
			e.printStackTrace();
			Syslog.error("数据库链接失败！");
			fail_msg = e.toString();
		}
		
	}
	
	public String select(String sql) {
		//数据库是否已经连接
    	if(status.compareToIgnoreCase("0") == 0){
    		return null;
    	}
    	
    	String str = "[]";
    	JSONArray array = new JSONArray(); 
    	
    	try {
    		 rs = pre.executeQuery(sql);
    		 ResultSetMetaData metaData = rs.getMetaData();//列名
             int colNum = metaData.getColumnCount();
             String [] colNameArray = new String[colNum+1];
             String [] colTypeArray = new String[colNum+1];
             
             for (int i = 1; i <= metaData.getColumnCount(); i++) {
             	colNameArray[i] = metaData.getColumnLabel(i);
             	colTypeArray[i] = metaData.getColumnTypeName(i);
             }

             while (rs.next()) {
            	 JSONObject jsonObj = new JSONObject(); 
            	 for (int i = 1; i <= metaData.getColumnCount(); i++) {
            		 String columnName = colNameArray[i];
            		 String value = rs.getString(i);
            		 String ColumnTypeName = colTypeArray[i];
            	 
            		 if (value == null) {
            			 if(ColumnTypeName.compareToIgnoreCase("VARCHAR") == 0){
                     		value = "";
                     	} else if (ColumnTypeName.compareToIgnoreCase("INT") == 0) {
                     		value = "0";
                     	} else if (ColumnTypeName.compareToIgnoreCase("BIGINT") == 0) {
                     		value = "1";
                     	} else {
                     		value = "";
                     	}
            		 }
            		 jsonObj.put(columnName, value);
            	 }
            	 array.add(jsonObj);
             }
             
             rs.close();
             colNameArray = null;
             colTypeArray = null;
            
             return array.toString();  
             
    	} catch (Exception e) {
    		e.printStackTrace();
    		Syslog.info("查询数据失败！");
    	}
    	
    	return str;
	}
	
	public void discon(){
		try {
			pre.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		status = "0";
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}

	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}

	public Statement getPre() {
		return pre;
	}

	public void setPre(PreparedStatement pre) {
		this.pre = pre;
	}

	public ResultSet getRs() {
		return rs;
	}

	public void setRs(ResultSet rs) {
		this.rs = rs;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getErr_code() {
		return err_code;
	}

	public void setErr_code(int err_code) {
		this.err_code = err_code;
	}

	public String getFail_msg() {
		return fail_msg;
	}

	public void setFail_msg(String fail_msg) {
		this.fail_msg = fail_msg;
	}
}
