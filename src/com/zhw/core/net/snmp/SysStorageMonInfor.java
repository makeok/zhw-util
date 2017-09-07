package com.zhw.core.net.snmp;
/**
 * 系统存储信息的监控
 * @author ifZhou
 *
 */
public class SysStorageMonInfor {
	private String id; 
	private String ip; 
	private String recordTime; 
	private String storageName; 
	private double storageSize; 
	private double storageUsed; 
	private String usedPercent;
	
	public SysStorageMonInfor() {
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
	public String getStorageName() {
		return storageName;
	}
	public void setStorageName(String storageName) {
		this.storageName = storageName;
	}
	public double getStorageSize() {
		return storageSize;
	}
	public void setStorageSize(double storageSize) {
		this.storageSize = storageSize;
	}
	public double getStorageUsed() {
		return storageUsed;
	}
	public void setStorageUsed(double storageUsed) {
		this.storageUsed = storageUsed;
	}
	public String getUsedPercent() {
		return usedPercent;
	}
	public void setUsedPercent(String usedPercent) {
		this.usedPercent = usedPercent;
	} 
	
	
	
	
}
