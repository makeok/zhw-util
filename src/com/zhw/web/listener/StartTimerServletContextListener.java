package com.zhw.web.listener;


import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.zhw.core.log.Syslog;

import sun.misc.Signal;
import sun.misc.SignalHandler;

/**
 * @功能描述: 自动启动
 * @开发人员: 
 * @创建日期: 2013-5-15 下午1:25:13
 */
@SuppressWarnings("restriction")
public class StartTimerServletContextListener implements ServletContextListener , SignalHandler {

  private ServletContext context = null;
    
  
  /*
   * This method is invoked when the Web Application has been removed and is no
   * longer able to accept requests
   */
  @Override
  public void contextDestroyed(ServletContextEvent event) {
    // Output a simple message to the server's console
	  Syslog.out("The Simple Web App. Has Been Removed");
	  Syslog.error("The Simple Web App. Has Been Removed");
	  this.context = null;
	  doExit();
  }

  
  // This method is invoked when the Web Application
  // is ready to service requests
  // WEB进程自动调用此过程启动作业调度
  @Override
  public void contextInitialized(ServletContextEvent event) {

    try {
    	Syslog.info("启动成功！");
    	//获取serveletContent
    	this.context = event.getServletContext();

    	
        /*
         * 注册JVM钩子，在JVM关闭之前做一些收尾的工作，当然也能阻止TOMCAT的关闭；必须放在contextInitialized中注册。
         */
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
 
            public void run() {
            	doExit();
            }
        }));

        //注册信号
    	StartTimerServletContextListener testSignalHandler = new StartTimerServletContextListener();  
        // install signals  
        Signal.handle(new Signal("TERM"), testSignalHandler);
        Signal.handle(new Signal("ILL"), testSignalHandler);
        Signal.handle(new Signal("ABRT"), testSignalHandler);
        Signal.handle(new Signal("INT"), testSignalHandler);
        Signal.handle(new Signal("TERM"), testSignalHandler);
    }
    catch (Exception ex) {
    	Syslog.error("启动失败");
    	ex.printStackTrace();
    }
  }


	@Override
	public void handle(Signal sn) {
		// TODO Auto-generated method stub
		System.out.println(sn.getName()+" is recevied.");
		doExit();
	}

	/*
	 * webapp 退出函数
	 */
	public void doExit(){
        int n = 0;
        while (n < 10) {
        	Syslog.info(Thread.currentThread() + "," + n++);
        	System.out.println("ShutdownHook "+n++);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
	}
	
	
    public static void main(String[] args) throws InterruptedException {  
    	StartTimerServletContextListener testSignalHandler = new StartTimerServletContextListener();  
        // install signals  
        Signal.handle(new Signal("TERM"), testSignalHandler);
        Signal.handle(new Signal("ILL"), testSignalHandler);
        Signal.handle(new Signal("ABRT"), testSignalHandler);
        Signal.handle(new Signal("INT"), testSignalHandler);
        Signal.handle(new Signal("TERM"), testSignalHandler);
//      Signal.handle(new Signal("FPE"), testSignalHandler);
//        Signal.handle(new Signal("BREAK"), testSignalHandler);
//        Signal.handle(new Signal("USR1"), testSignalHandler);  
//        Signal.handle(new Signal("USR2"), testSignalHandler);  
//        SEGV, ILL, FPE, ABRT, INT, TERM, BREAK
//        SEGV, , FPE, BUS, SYS, CPU, FSZ, ABRT, INT, TERM, HUP, USR1, USR2, QUIT, BREAK, TRAP, PIPE
        for (;;) {  
            Thread.sleep(3000);  
            System.out.println("running......");  
        }  
    }  
}
