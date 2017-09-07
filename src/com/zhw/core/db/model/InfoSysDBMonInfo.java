package com.zhw.core.db.model;

public class InfoSysDBMonInfo {
	private String	id;//	id 
	private String	updated;//	updated 
	private String	monitor_id;//	monitor_id 
	private Float space_use = 0F;
	private long sql_query_num = 0;
	private long sql_send_byte = 0;
	private Float cpu_use = 0F;
	private Float mem_use = 0F;
	private long cache = 0;
	private int sql_connectnum = 0;
	private int open_tablenum = 0;
	private String	status = "0";//	status 	
	private int	return_code = 0;//	return_code 	
	private String	fail_msg = "";//	fail_msg 	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	public String getMonitor_id() {
		return monitor_id;
	}
	public void setMonitor_id(String monitor_id) {
		this.monitor_id = monitor_id;
	}
	public Float getSpace_use() {
		return space_use;
	}
	public void setSpace_use(Float space_use) {
		this.space_use = space_use;
	}
	public long getSql_query_num() {
		return sql_query_num;
	}
	public void setSql_query_num(long sql_query_num) {
		this.sql_query_num = sql_query_num;
	}
	public long getSql_send_byte() {
		return sql_send_byte;
	}
	public void setSql_send_byte(long sql_send_byte) {
		this.sql_send_byte = sql_send_byte;
	}
	public Float getCpu_use() {
		return cpu_use;
	}
	public void setCpu_use(Float cpu_use) {
		this.cpu_use = cpu_use;
	}
	public Float getMem_use() {
		return mem_use;
	}
	public void setMem_use(Float mem_use) {
		this.mem_use = mem_use;
	}
	public long getCache() {
		return cache;
	}
	public void setCache(long cache) {
		this.cache = cache;
	}
	public int getSql_connectnum() {
		return sql_connectnum;
	}
	public void setSql_connectnum(int sql_connectnum) {
		this.sql_connectnum = sql_connectnum;
	}
	public int getOpen_tablenum() {
		return open_tablenum;
	}
	public void setOpen_tablenum(int open_tablenum) {
		this.open_tablenum = open_tablenum;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getReturn_code() {
		return return_code;
	}
	public void setReturn_code(int return_code) {
		this.return_code = return_code;
	}
	public String getFail_msg() {
		return fail_msg;
	}
	public void setFail_msg(String fail_msg) {
		this.fail_msg = fail_msg;
	}
	
	

	
	
	
}
