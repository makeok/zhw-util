package com.zhw.core.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.zhw.core.log.Syslog;



public class MyThreadPool {
	private static int fixedThreadNum = 5;
	private static int scheduledThreadNum = 5;
	
	//创建一个缓冲池，缓冲池容量大小为Integer.MAX_VALUE,如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
	private static ExecutorService cachedThreadPool;
	//创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
	private static ExecutorService fixedThreadPool;
	//创建一个定长线程池，支持定时及周期性任务执行。延迟执行示例代码如下：
	private static ScheduledExecutorService scheduledThreadPool; 
	//创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
	private static ExecutorService singleThreadExecutor;

	//初始化线程池
	static{
		cachedThreadPool = Executors.newCachedThreadPool();
//		fixedThreadPool = Executors.newFixedThreadPool(fixedThreadNum);
//		scheduledThreadPool = Executors.newScheduledThreadPool(scheduledThreadNum); 
//		singleThreadExecutor = Executors.newSingleThreadExecutor(); 
	}
	
	public static void ThreadPoolExecutorTest(Runnable execClass){
		 // 构造一个线程池  
	    ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 4, 3,  
	            TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3),  
	            new ThreadPoolExecutor.DiscardOldestPolicy());  
	    long produceTaskSleepTime = 1000;
	    int produceTaskMaxNumber = 3;
		for (int i = 1; i <= produceTaskMaxNumber; i++) {  
	        try {  
	            String task = "task@ " + i;  
	            System.out.println("创建任务并提交到线程池中：" + task);  
	            threadPool.execute(execClass);  
				Thread.sleep(produceTaskSleepTime);  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	    }  
	}
	//执行方法
	public static void cachedPoolExecute(Runnable execClass){
		if(cachedThreadPool != null){
			cachedThreadPool.execute(execClass);
		}else{
			Syslog.error("cachedThreadPool==null");
		}	
	}
	
	//执行方法
	public static void fixedPoolExecute(Runnable execClass){
		if(fixedThreadPool != null){
			fixedThreadPool.execute(execClass);
		}else{
			Syslog.error("cachedThreadPool==null");
		}	
	}
	//执行方法
	public static void schedulePoolExecute(Runnable execClass){
		if(scheduledThreadPool != null){
			scheduledThreadPool.execute(execClass);
		}else{
			Syslog.error("cachedThreadPool==null");
		}	
	}
	//执行延迟
	public static void schedulePoolExecuteDelay(Runnable execClass,long delayMs,TimeUnit timeunit){
		if(scheduledThreadPool != null){
			scheduledThreadPool.schedule(execClass, delayMs, timeunit);
		}else{
			Syslog.error("cachedThreadPool==null");
		}	
		
	}
	//延迟1秒后每3秒执行一次。
	public static void schedulePoolExecuteRate(Runnable execClass,long delayMs,long period,TimeUnit timeunit){
		if(scheduledThreadPool != null){
			scheduledThreadPool.scheduleAtFixedRate(execClass, delayMs, period, timeunit);
		}else{
			Syslog.error("cachedThreadPool==null");
		}		
	}

	//执行方法
	public static void singlePoolExecute(Runnable execClass){
		if(singleThreadExecutor != null){
			singleThreadExecutor.execute(execClass);
		}else{
			Syslog.error("singleThreadExecutor==null");
		}	
	}

	public static void main(String[] args) { 
		cachedThreadPool.execute(new Runnable() {  
		   	public void run() {  
		   		System.out.println();  
		   	}  
		}); 
	}
}
