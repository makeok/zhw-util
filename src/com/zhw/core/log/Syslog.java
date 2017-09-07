package com.zhw.core.log;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;
public class Syslog {
	private static Logger logger = Logger.getLogger("SYSLOG");
//	private static Log logger = LogFactory.getLog("SYSLOG");

	 private static String _FILE_LINE_()   
	 {     
	  StackTraceElement stackTraces[] = (new Throwable()).getStackTrace();     
	  StringBuffer strBuffer = new StringBuffer("[");  
	  //strBuffer.append(Constants.df.format(new Date())).append("][");
	  strBuffer.append(stackTraces[2].getClassName()).append(".java:");    
	  strBuffer.append(stackTraces[2].getLineNumber());
	  //strBuffer.append(stackTraces[0].getMethodName()).append("]");      
	  //strBuffer.append(stackTraces[0].getClassName()).append(".");  
	  strBuffer.append("行]"); 
	  return strBuffer.toString();     
	 }
	 public static boolean isDebugEnabled(){
		 return logger.isDebugEnabled();
	 }
	 public static boolean isWarnEnabled(){
		 return logger.isTraceEnabled();
	 }
	 public static void debug(Object msg){
		 logger.debug(_FILE_LINE_()+msg);
	 }	 
	 public static void info(Object msg){
		 logger.info(_FILE_LINE_()+msg);
	 }
	 public static void warn(Object msg){
		 logger.warn(_FILE_LINE_()+msg);
	 }
	 public static void trace(Object msg){
		 logger.trace(_FILE_LINE_()+msg);
	 }
	 public static void error(Object msg){
		 logger.error(_FILE_LINE_()+msg);
	 }
	 public static void out(Object msg) {
		 System.out.println(msg);		
	 }
	 public static void printTrace(Throwable t) {
		 StringWriter stringWriter= new StringWriter();
		 PrintWriter writer= new PrintWriter(stringWriter);
		 t.printStackTrace(writer);
		 StringBuffer buffer= stringWriter.getBuffer();
		 error(buffer.toString());
	 }
	 
	 //将异常的printStackTrace输出转换为字符串
	 public static String getTrace(Throwable t) {
        StringWriter stringWriter= new StringWriter();
        PrintWriter writer= new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer= stringWriter.getBuffer();
        return buffer.toString();
    }

}
