package com.zhw.core.jobPlan.jobs;

import java.util.Date;
import java.util.Properties;

import com.zhw.core.jobPlan.IJobProcessor;
import com.zhw.core.log.Syslog;

public class SystemMoniterJob implements IJobProcessor  {
	  public Properties process(Properties prop) throws Exception {
		  Syslog.info("---系统监控任务开始..."+new Date());
		  // TODO 开始任务
		  Syslog.info("---系统监控任务完成..."+new Date());
	  	  return null;    
	  }
}
