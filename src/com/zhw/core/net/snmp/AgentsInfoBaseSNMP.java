package com.zhw.core.net.snmp;

public class AgentsInfoBaseSNMP {
	private String ip;
	private String serverName;
	private String importProc;
	private int hrStorageNum;
	private String hrStorage;
	private String hrProcessorLoad;
	private String hrProcess;
	public AgentsInfoBaseSNMP() {
		super();
	}
	
	public String getImportProc() {
		return importProc;
	}

	public void setImportProc(String importProc) {
		this.importProc = importProc;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public int getHrStorageNum() {
		return hrStorageNum;
	}

	public void setHrStorageNum(int hrStorageNum) {
		this.hrStorageNum = hrStorageNum;
	}

	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getHrStorage() {
		return hrStorage;
	}
	public void setHrStorage(String hrStorage) {
		this.hrStorage = hrStorage;
	}
	public String getHrProcessorLoad() {
		return hrProcessorLoad;
	}
	public void setHrProcessorLoad(String hrProcessorLoad) {
		this.hrProcessorLoad = hrProcessorLoad;
	}
	public String getHrProcess() {
		return hrProcess;
	}
	public void setHrProcess(String hrProcess) {
		this.hrProcess = hrProcess;
	}
	
	
}
