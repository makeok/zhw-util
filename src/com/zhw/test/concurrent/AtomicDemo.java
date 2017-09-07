package com.zhw.test.concurrent;



import java.util.concurrent.TimeUnit;  
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;  

public class AtomicDemo implements Runnable {  
    
  private static AtomicBoolean exists = new AtomicBoolean(false);  
  
  AtomicReference<String> atomicReference = new AtomicReference<String>("first value referenced");  
      
   private String name;    
       
   public AtomicDemo(String name) {     
        this.name = name;    
   }    
     
   @Override    
   public void run() {     
       if (exists.compareAndSet(false, true)) {    
                  
           System.out.println(name + " enter");    
           try {    
                System.out.println(name + " working");    
                TimeUnit.SECONDS.sleep(2);    
           } catch (InterruptedException e) {    
                // do nothing    
           }    
           System.out.println(name + " leave");    
           exists.set(false);      
      } else {    
           System.out.println(name + " give up");    
      }    
  
  }   
     
     
   public static void main(String[] args) {  
         
	   AtomicDemo bar1 = new AtomicDemo("bar1");  
	   AtomicDemo bar2 = new AtomicDemo("bar2");  
       new Thread(bar1).start();  
       new Thread(bar2).start();  
         
         
        
  }  


}  