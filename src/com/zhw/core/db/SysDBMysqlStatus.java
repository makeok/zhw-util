package com.zhw.core.db;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhw.core.db.model.InfoSysDBMonInfo;
import com.zhw.core.log.Syslog;

public class SysDBMysqlStatus {
	// 连接数
	private static String con_num_sql = "show status like 'Threads_connected';";
	// 打开表数量
	private static  String open_t_num_sql = "show status like 'Open_tables';";
	// 查询数量
	private static  String query_num_sql = "show global status like 'Queries';";
	// 发送字节数
	private static  String send_byte_sql = "show global status like 'Bytes_sent';";
	// 接收字节数
	private static  String rcv_byte_sql = "show global status like 'Bytes_received';";
	// 查询缓存
	private static  String query_cache_sql = "SHOW VARIABLES LIKE 'query_cache_size';";
	// 使用量
	private static  String spqace_usage_sql = "SELECT TRUNCATE(SUM(data_length)/SUM(max_data_length),2) AS 'Value' FROM information_schema.tables;";
	private static  String spqace_usemb_sql = "SELECT ROUND(SUM(DATA_LENGTH/1024/1024),2) AS Value FROM information_schema.TABLES;";
	private static  String spqace_indexmb_sql = "SELECT ROUND(SUM(INDEX_LENGTH/1024/1024),2) AS Value FROM information_schema.TABLES;";
	// key 缓存命中率=Key_read_requests-Key_reads/Key_read_requests
	private static  String Key_reads_sql = "SHOW /*!50000 GLOBAL */ STATUS LIKE 'Key_reads';";
	private static  String Key_read_requests_sql = "SHOW /*!50000 GLOBAL */ STATUS LIKE 'Key_read_requests';";
	// Innodb 缓存命中率=Innodb_buffer_pool_read_requests-Innodb_buffer_pool_reads/Innodb_buffer_pool_read_requests
	private static  String Innodb_buffer_pool_reads_sql = "SHOW /*!50000 GLOBAL */ STATUS LIKE 'Innodb_buffer_pool_reads';";
	private static  String Innodb_buffer_pool_read_requests_sql = "SHOW /*!50000 GLOBAL */ STATUS LIKE 'Innodb_buffer_pool_read_requests';";
	// innodb buffer usage=Innodb_buffer_pool_pages_data/Innodb_buffer_pool_pages_total
	private static  String Innodb_buffer_pool_pages_data_sql = "SHOW /*!50000 GLOBAL */ STATUS LIKE 'Innodb_buffer_pool_pages_data';";
	private static  String Innodb_buffer_pool_pages_total_sql = "SHOW /*!50000 GLOBAL */ STATUS LIKE 'Innodb_buffer_pool_pages_total';";
	
	// 连接信息
	private String srvip;
	private String user;
	private String pass;
	private String db;
	private String port;
	// 获取到的信息
	public String status = "0";
	public String msg = "";
	public Integer con_num;
	public Integer open_t_num;
	public Long query_num;
	public Long query_cache;	
	public Long send_byte;
	public Long rcv_byte;
	public Double spqace_usemb;
	public Double spqace_indexmb;
	public Long Key_reads;
	public Long Key_read_requests;	
	public Long Innodb_buffer_pool_reads;
	public Long Innodb_buffer_pool_read_requests;
	public Long Innodb_buffer_pool_pages_data;
	public Long Innodb_buffer_pool_pages_total;
	public Double Key_efficiency ;
	public Double Innodb_buffer_efficiency ;
	public Double Innodb_buffer_usage ;
	
	public SysDBMysqlStatus(String srvip,String port,String user,String pass,String db){
		this.srvip = srvip;
		this.port = port;
		this.user = user;
		this.pass = pass;
		this.db = db;		
	}

	// 获取
	public InfoSysDBMonInfo check_mysql(){
		InfoSysDBMonInfo rt = new InfoSysDBMonInfo();
		MysqlUtil dd = new MysqlUtil(srvip,port,user,pass,db);
		status = (dd.getStatus());
		msg = (dd.getFail_msg());
		if(dd.getStatus().compareToIgnoreCase("0") == 0){
			Syslog.info(srvip+":"+port+","+user+","+pass+","+db+","+dd.getFail_msg());
			return rt;
		}
		String Value = "0";
		//db连接数
		Value = getValueBySql(dd,con_num_sql);
		con_num = Integer.parseInt(Value);
		//打开表数
		Value = getValueBySql(dd,open_t_num_sql);
		open_t_num = Integer.parseInt(Value);
		//查询sql数
		Value = getValueBySql(dd,query_num_sql);
		query_num = Long.parseLong(Value);
		//查询缓存
		Value = getValueBySql(dd,query_cache_sql);
		query_cache = Long.parseLong(Value);
		//发送字节数
		Value = getValueBySql(dd,send_byte_sql);
		send_byte = Long.parseLong(Value);
		// 接收字节数
		Value = getValueBySql(dd,rcv_byte_sql);
		rcv_byte = Long.parseLong(Value);
		// 接收字节数
		Value = getValueBySql(dd,spqace_usemb_sql);
		spqace_usemb = Double.parseDouble(Value);
		// 接收字节数
		Value = getValueBySql(dd,spqace_indexmb_sql);
		spqace_indexmb = Double.parseDouble(Value);
		// 接收字节数
		Value = getValueBySql(dd,Key_reads_sql);
		Key_reads = Long.parseLong(Value);
		// 接收字节数
		Value = getValueBySql(dd,Key_read_requests_sql);
		Key_read_requests = Long.parseLong(Value);
		// 接收字节数
		Value = getValueBySql(dd,Innodb_buffer_pool_reads_sql);
		Innodb_buffer_pool_reads = Long.parseLong(Value);
		// 接收字节数
		Value = getValueBySql(dd,Innodb_buffer_pool_read_requests_sql);
		Innodb_buffer_pool_read_requests = Long.parseLong(Value);
		// 接收字节数
		Value = getValueBySql(dd,Innodb_buffer_pool_pages_data_sql);
		Innodb_buffer_pool_pages_data = Long.parseLong(Value);
		// 接收字节数
		Value = getValueBySql(dd,Innodb_buffer_pool_pages_total_sql);
		Innodb_buffer_pool_pages_total = Long.parseLong(Value);
		
		// 计算命中率
		Key_efficiency = getPercent(Key_read_requests-Key_reads,Key_read_requests);//(double) (100*(Key_read_requests-Key_reads)/Key_read_requests);
		Innodb_buffer_efficiency = getPercent(Innodb_buffer_pool_read_requests-Innodb_buffer_pool_reads,Innodb_buffer_pool_read_requests);//(double) (100*(Innodb_buffer_pool_read_requests-Innodb_buffer_pool_reads)/Innodb_buffer_pool_read_requests);
		Innodb_buffer_usage = getPercent(Innodb_buffer_pool_pages_data,Innodb_buffer_pool_pages_total);//(double) (100*Innodb_buffer_pool_pages_data/Innodb_buffer_pool_pages_total);
//		Syslog.info(rt.getSql_connectnum());
//		Syslog.info(rt.getOpen_tablenum());
//		Syslog.info(rt.getSql_query_num());
//		Syslog.info(rt.getSql_send_byte());
//		Syslog.info(rt.getCache());
//		Syslog.info(rt.getSpace_use());
//		Syslog.info(rt.getStatus());

		// 释放连接
		dd.discon();
		status = "1";
		return rt;
	}
	public String getValueBySql(MysqlUtil dd,String sql){
		String str = dd.select(sql);
		JSONArray jsonArray = JSONArray.parseArray(str);
		String Value = "0";
		if(jsonArray.size()>0){
			Value = ((JSONObject)jsonArray.get(0)).getString("Value");
		}
		return Value;
	}
	
	public static Double getPercent(Long num1,Long num2){
		Double a1 = (double) (100*100*num1/num2);
		return (double) (Math.round(a1)/100.0D);
	}
	

	@Override
	public String toString() {
		return "SysDBMysqlStatus [srvip=" + srvip + ", user=" + user
				+ ", pass=" + pass + ", db=" + db + ", port=" + port
				+ ", status=" + status + ", con_num=" + con_num
				+ ", open_t_num=" + open_t_num + ", query_num=" + query_num
				+ ", query_cache=" + query_cache + ", send_byte=" + send_byte
				+ ", rcv_byte=" + rcv_byte + ", spqace_usemb=" + spqace_usemb
				+ ", spqace_indexmb=" + spqace_indexmb + ", Key_reads="
				+ Key_reads + ", Key_read_requests=" + Key_read_requests
				+ ", Innodb_buffer_pool_reads=" + Innodb_buffer_pool_reads
				+ ", Innodb_buffer_pool_read_requests="
				+ Innodb_buffer_pool_read_requests
				+ ", Innodb_buffer_pool_pages_data="
				+ Innodb_buffer_pool_pages_data
				+ ", Innodb_buffer_pool_pages_total="
				+ Innodb_buffer_pool_pages_total + ", Key_efficiency="
				+ Key_efficiency + ", Innodb_buffer_efficiency="
				+ Innodb_buffer_efficiency + ", Innodb_buffer_usage="
				+ Innodb_buffer_usage + "]";
	}

	public static void main(String [] args){
		Syslog.out(getPercent(3L,9L)+"");
		SysDBMysqlStatus dbb = new SysDBMysqlStatus("127.0.0.1","3306","user","passwd","dbname");
		dbb.check_mysql();
		Syslog.out(dbb.toString());
	}
}
