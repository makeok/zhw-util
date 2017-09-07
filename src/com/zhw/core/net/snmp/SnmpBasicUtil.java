package com.zhw.core.net.snmp;

import java.util.ArrayList;
import java.util.List;

public class SnmpBasicUtil {
	public static void main(String[] args) {
		try {
			SnmpUtil util = new SnmpUtil();
			util.initComm("127.0.0.1",null);
			
			util.sendPDUbyGetBulk(new String[]{".1.3.6.1.2.1.1.1.0"},3000);
//			
//			//获取系统基本信息	SysDesc
//			util.sendPDUByGet(new String[]{".1.3.6.1.2.1.1.1.0"});
//			
//			//监控时间系统运行时长	sysUptime
//			util.sendPDUByGet(new String[]{".1.3.6.1.2.1.1.3.0"});
//			
//			//.1.3.6.1.2.1.1.4.0	系统联系人	sysContact	GET
//			util.sendPDUByGet(new String[]{".1.3.6.1.2.1.1.4.0"});
//			
//			//.1.3.6.1.2.1.1.5.0	获取机器名	SysName	GET
//			util.sendPDUByGet(new String[]{".1.3.6.1.2.1.1.5.0"});
//			
//			//.1.3.6.1.2.1.1.6.0	机器坐在位置	SysLocation	GET
//			util.sendPDUByGet(new String[]{".1.3.6.1.2.1.1.6.0"});
//			
//			//.1.3.6.1.2.1.1.7.0	机器提供的服务	SysService	GET
//			util.sendPDUByGet(new String[]{".1.3.6.1.2.1.1.7.0"});
//			util.sendPDUbyGetBulk(new String[]{"1.3.6.1.2.1.1.7.0"},79);
//			
//			//.1.3.6.1.2.1.25.4.2.1.2	系统运行的进程列表	hrSWRunName	WALK
//			util.sendPDUbyGetBulk(new String[]{".1.3.6.1.2.1.25.4.2.1.2"},10);
//			
//			//.1.3.6.1.2.1.25.6.3.1.2	系统安装的软件列表	hrSWInstalledName	WALK
//			util.sendPDUbyGetBulk(new String[]{".1.3.6.1.2.1.25.6.3.1.2"},10);

			

//#hrStorage 包含 A:\ 、Physical Memory 和 Virtual Memory
//#1.3.6.1.2.1.25.2.3.1.3;盘符名称  host.hrStorage.hrStorageTable.hrStorageEntry.hrStorageDescr
//#1.3.6.1.2.1.25.2.3.1.4;盘符单位 host.hrStorage.hrStorageTable.hrStorageEntry.hrStorageAllocationUnits
//#1.3.6.1.2.1.25.2.3.1.5;盘符总大小 host.hrStorage.hrStorageTable.hrStorageEntry.hrStorageSize
//#1.3.6.1.2.1.25.2.3.1.6;盘符使用量 host.hrStorage.hrStorageTable.hrStorageEntry.hrStorageUsed
//#1.3.6.1.2.1.25.2.3.1.2;盘符类型 host.hrStorage.hrStorageTable.hrStorageEntry.hrStorageType
//#ObjectID 1.3.6.1.2.1.25.2.1.4 表示硬盘
//#ObjectID 1.3.6.1.2.1.25.2.1.5 表示可移动磁盘
//#ObjectID 1.3.6.1.2.1.25.2.1.3 虚拟内存
//#ObjectID 1.3.6.1.2.1.25.2.1.2 物理内存
//
//#hrProcessorLoad
//#1.3.6.1.2.1.25.1.6;系统进程数
//#1.3.6.1.2.1.25.4.2.1.1 进程pid
//#1.3.6.1.2.1.25.4.2.1.2 进程名
//#1.3.6.1.2.1.25.4.2.1.4 进程path
//#1.3.6.1.2.1.25.4.2.1.5 进程参数
//
//#centi-seconds为百分之一秒。它表示从运行开始，smss.exe进程总共用了2688.61秒CPU。
//#.1.3.6.1.2.1.25.5.1.1.1 进程cpu使用量 host.hrSWRunPerf.hrSWRunPerfTable.hrSWRunPerfEntry.hrSWRunPerfCPU
//#.1.3.6.1.2.1.25.5.1.1.2 进程内存使用量 host.hrSWRunPerf.hrSWRunPerfTable.hrSWRunPerfEntry.hrSWRunPerfMem
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
