package com.zhw.web.security;

import org.apache.shiro.session.mgt.ValidatingSessionManager;  
import org.quartz.Job;  
import org.quartz.JobDataMap;  
import org.quartz.JobExecutionContext;  
import org.quartz.JobExecutionException;  

import com.zhw.core.log.Syslog;  
  

/*
 * 缘由：在使用shiro时，如果Quartz 为Quartz 2的版本，
 * 则抛出异常java.lang.InstantiationError: org.quartz.SimpleTrigger。
 * 原因是默认的shiro-quartz1.2.3中的实现是针对quartz1.6版本的实现（详细源码请查看
 * org.apache.shiro.session.mgt.quartz.QuartzSessionValidationScheduler），
 * 在quartz2.2.1中，SimppleTrigger为接口，所以无法实例化。如果使用Quartz 2，则需要自定义Quartz 2的实现
 * 基于Quartz 2.* 版本的实现 
 */ 
public class QuartzSessionValidationJob implements Job {  
  
    /** 
     * Key used to store the session manager in the job data map for this job. 
     */  
    public static final String SESSION_MANAGER_KEY = "sessionManager";  
  
    /*-------------------------------------------- 
    |    I N S T A N C E   V A R I A B L E S    | 
    ============================================*/  
  
    /*-------------------------------------------- 
    |         C O N S T R U C T O R S           | 
    ============================================*/  
  
    /*-------------------------------------------- 
    |  A C C E S S O R S / M O D I F I E R S    | 
    ============================================*/  
  
    /*-------------------------------------------- 
    |               M E T H O D S               | 
    ============================================*/  
  
    /** 
     * Called when the job is executed by quartz. This method delegates to the <tt>validateSessions()</tt> method on the 
     * associated session manager. 
     *  
     * @param context 
     *            the Quartz job execution context for this execution. 
     */  
    public void execute(JobExecutionContext context) throws JobExecutionException {  
  
        JobDataMap jobDataMap = context.getMergedJobDataMap();  
        ValidatingSessionManager sessionManager = (ValidatingSessionManager) jobDataMap.get(SESSION_MANAGER_KEY);  

        Syslog.debug("Executing session validation Quartz job...");
  
        sessionManager.validateSessions();  
  
        Syslog.debug("Session validation Quartz job complete.");
 
    }  
  
}  
