package com.zhw.core.net.snmp;

public class SysCPUMonInfor {
	private String id;
	private String ip;
	private String recordTime;
	private String loadPercent;
	public SysCPUMonInfor() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}
	public String getLoadPercent() {
		return loadPercent;
	}
	public void setLoadPercent(String loadPercent) {
		this.loadPercent = loadPercent;
	}
	
	
}
