package com.zhw.core.db;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhw.core.log.Syslog;

public class MysqlUtil {
	private static final String filename = "jdbc.properties";
	private static boolean is_readed = false;
	private static String Susername = "";
	private static String Spassword = "";
	private static String Surl;
	private String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=GBK";//characterEncoding=GBK
	private String username = "root";
    private String password = "root";
    private Connection con;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private int err_code = 0;
    private String status = "0";
    private String fail_msg = "";

	//private String propertiesFileName;// = "using_which_dbms";
	public MysqlUtil(String srcip,String user,String passwd,String db){
		url = "jdbc:mysql://"+srcip+":3306/"+db+"?useUnicode=true&characterEncoding=UTF-8";//characterEncoding=GBK
		username = user;
		password = passwd;
		getMyConnnect();
//		test();
//      stmt.close();
//      con.close();
	}
	//private String propertiesFileName;// = "using_which_dbms";
	public MysqlUtil(String srcip,String port,String user,String passwd,String db){
		url = "jdbc:mysql://"+srcip+":"+port+"/"+db+"?useUnicode=true&characterEncoding=UTF-8";//characterEncoding=GBK
		username = user;
		password = passwd;
		getMyConnnect();
//		test();
//      stmt.close();
//      con.close();
	}
	//private String propertiesFileName;// = "using_which_dbms";
	public MysqlUtil(){
		if(getproper()){
			Syslog.debug("get config file success!");
			getMyConnnect();
		}
		
	}
	public Connection getConnn(){
		return con;
	}
	public void discon(){
		try {
			stmt.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		status = "0";
	}
    public void test() {
        String sql_select = "select * from tablename where id=1";
        String sql_insert = "insert into tablename (col1,col2..) values('1','2'...)";
        String sql_update = "update tablename set colname='update' where id=1";
        insert(sql_insert);
        select(sql_select);
        update(sql_update);
    }
	
	public void getMyConnnect(){
        // 第一次定位驱动
		if(!is_readed){
	        try {
	            Class.forName("com.mysql.jdbc.Driver");
	            Syslog.debug("加载驱动成功!"); 
	        } catch (ClassNotFoundException e) {
	        	Syslog.error("加载驱动失败!");
	            e.printStackTrace();
	            //fail_msg = Constants.GetExceptMsg64(e.toString());
	            return;
	        }	
		}

        // 建立连接
        try {
        	DriverManager.setLoginTimeout(30);
            con = DriverManager.getConnection(url, username, password);
            con.setAutoCommit(true);
            stmt = con.createStatement();
            status = "1";
            Syslog.debug("数据库连接成功!"); 
        } catch(SQLException e) {
        	Syslog.error("数据库连接失败!"); 
        	e.printStackTrace();
            //fail_msg = Constants.GetExceptMsg64(e.toString());
            return;
        }

	}
    public String select(String sql) {
    	//数据库是否已经连接
    	if(status.compareToIgnoreCase("0") == 0){
    		return null;
    	}
    	
    	String str = "[]";
//    	StringBuffer sb = new StringBuffer();
    	JSONArray array = new JSONArray(); 
        try {
        	
            rs = stmt.executeQuery(sql);
            ResultSetMetaData meta_data = rs.getMetaData();//列名
            int colNum = meta_data.getColumnCount();
            String [] colNameArray = new String[colNum+1];
            String [] colTypeArray = new String[colNum+1];
            for (int i_col = 1; i_col <= meta_data.getColumnCount(); i_col++) {
                //System.out.print(meta_data.getColumnLabel(i_col) + "   ");
            	colNameArray[i_col] = meta_data.getColumnLabel(i_col);
            	colTypeArray[i_col] = meta_data.getColumnTypeName(i_col);
            }

            while (rs.next()) {
            	JSONObject jsonObj = new JSONObject(); 
                for (int i_col = 1; i_col <= meta_data.getColumnCount(); i_col++) {
                    String columnName = colNameArray[i_col];//meta_data.getColumnLabel(i_col);  
                    String value = rs.getString(i_col);//rs.getString(columnName); 
                    String ColumnTypeName = colTypeArray[i_col];//meta_data.getColumnTypeName(i_col);
                    if(value == null){
                    	if(ColumnTypeName.compareToIgnoreCase("VARCHAR") == 0){
                    		value = "";
                    	}
                    	else if(ColumnTypeName.compareToIgnoreCase("INT") == 0){
                    		value = "0";
                    	}
                    	else if(ColumnTypeName.compareToIgnoreCase("BIGINT") == 0){
                    		value = "0";
                    	}else{
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
            //return sb.toString();
            return array.toString();  
        }catch (Exception e) {
        	Syslog.error(sql);
        	e.printStackTrace();
        	Syslog.error("数据查询失败!");
        }
		return str;
    }

	
//    public void doManyInsert(){
//    	int batchSize = 1000;
//    	PreparedStatement ps = connection.prepareStatement("insert into tb1 (c1,c2,c3...) values (?,?,?...)");
//
//    	for (int i = 0; i < list.size(); i++) {
//
//    	    ps.setXXX(list.get(i).getC1());
//    	    ps.setYYY(list.get(i).getC2());
//    	    ps.setZZZ(list.get(i).getC3());
//
//    	    ps.addBatch();
//
//    	    if ((i + 1) % batchSize == 0) {
//    	        ps.executeBatch();
//    	    }
//    	}
//
//    	if (list.size() % batchSize != 0) {
//    	    ps.executeBatch();
//    	}
//    }
    public void insert(String sql) {
    	//数据库是否已经连接
    	if(status.compareToIgnoreCase("0") == 0){
    		return ;
    	}
    	
        try {
            stmt.clearBatch();
            stmt.addBatch(sql);
            stmt.executeBatch();
            Syslog.debug("数据插入成功!");
        }catch (Exception e) {
        	Syslog.error("数据插入失败!");
        }
        
    }
    public void update(String sql) {
    	//数据库是否已经连接
    	if(status.compareToIgnoreCase("0") == 0){
    		return ;
    	}
    	
        try {
            stmt.executeUpdate(sql);
            Syslog.debug("数据更新成功!");
        }catch (Exception e) {
        	Syslog.error("数据更新失败!");
        }
    }
    
	public boolean updateByPreparedStatement(String sql, List<Object> params) throws SQLException
	{
    	//数据库是否已经连接
    	if(status.compareToIgnoreCase("0") == 0){
    		return false;
    	}
		boolean flag = false;
		int result = -1;
		pstmt = con.prepareStatement(sql);
		int index = 1;
		if(params != null && !params.isEmpty())
		{
			for (int i = 0; i < params.size(); i++)
			{
				pstmt.setObject(index++, params.get(i));
			}
		}
		result = pstmt.executeUpdate();
		flag = result > 0 ? true : false;
		return flag;
	}
	public Map<String, Object> findSimpleResult(String sql, List<Object> params) throws SQLException
	{
		Map<String, Object> map = new HashMap<String, Object>();
		int index = 1;
		pstmt = con.prepareStatement(sql);
		if(params != null && !params.isEmpty())
		{
			for (int i = 0; i < params.size(); i++)
			{
				pstmt.setObject(index++, params.get(i));
			}
		}
		rs = pstmt.executeQuery();// ���ز�ѯ���
		ResultSetMetaData metaData = rs.getMetaData();
		int col_len = metaData.getColumnCount();
		while (rs.next())
		{
			for (int i = 0; i < col_len; i++)
			{
				String cols_name = metaData.getColumnName(i + 1);
				Object cols_value = rs.getObject(cols_name);
				if(cols_value == null)
				{
					cols_value = "";
				}
				map.put(cols_name, cols_value);
			}
		}
		return map;
	}
	/**
	 * ͨ������Ʋ�ѯ������¼
	 * 
	 * @param sql
	 * @param params
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public <T> T findSimpleRefResult(String sql, List<Object> params, Class<T> cls) throws Exception
	{
		T resultObject = null;
		int index = 1;
		pstmt = con.prepareStatement(sql);
		if(params != null && !params.isEmpty())
		{
			for (int i = 0; i < params.size(); i++)
			{
				pstmt.setObject(index++, params.get(i));
			}
		}
		rs = pstmt.executeQuery();
		ResultSetMetaData metaData = rs.getMetaData();
		int cols_len = metaData.getColumnCount();
		while (rs.next())
		{
			// ͨ������ƴ���һ��ʵ��
			resultObject = cls.newInstance();
			for (int i = 0; i < cols_len; i++)
			{
				String cols_name = metaData.getColumnName(i + 1);
				Object cols_value = rs.getObject(cols_name);
				if(cols_value == null)
				{
					cols_value = "";
				}
				Field field = cls.getDeclaredField(cols_name);
				field.setAccessible(true); // ��javabean�ķ���Ȩ��
				field.set(resultObject, cols_value);
			}
		}
		return resultObject;

	}
	public Boolean getproper(){
		if(is_readed){
			this.url = Surl;
			this.username = Susername;
			this.password = Spassword;
			return true;
		}
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filename);
		Properties p = new Properties();
		try {
			p.load(inputStream);
			MysqlUtil.Surl = p.getProperty("url");
			MysqlUtil.Susername = p.getProperty("username");
			MysqlUtil.Spassword = p.getProperty("password");
			this.url = Surl;
			this.username = Susername;
			this.password = Spassword;
			is_readed = true;
//			System.out.println(this.url);
//			System.out.println(this.username);
//			System.out.println(this.password);
			return true;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	
	
    public int getErr_code() {
		return err_code;
	}
	public void setErr_code(int err_code) {
		this.err_code = err_code;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFail_msg() {
		return fail_msg;
	}
	public void setFail_msg(String fail_msg) {
		this.fail_msg = fail_msg;
	}
	
	
	public static void main (String[] args){
    	//System.out.println(MessageFormat.format("{0}", "asdf"));
		MysqlUtil dd = new MysqlUtil("127.0.0.1","user","passwd","dbname");
    	String sql = "select * from user_t;";
    	try {
    		System.out.println(dd.select(sql));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	

	}
}

