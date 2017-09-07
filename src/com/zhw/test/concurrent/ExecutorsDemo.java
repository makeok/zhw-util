package com.zhw.test.concurrent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorsDemo {
    public static void main(String[] args) throws Exception {  
    	  
    	ExecutorService executorService = Executors.newSingleThreadExecutor();  
    	  
    	Set<Callable<String>> callables = new HashSet<Callable<String>>();  
    	  
    	callables.add(new Callable<String>() {  
    	    public String call() throws Exception {  
    	        return "Task 1";  
    	    }  
    	});  
    	callables.add(new Callable<String>() {  
    	    public String call() throws Exception {  
    	        return "Task 2";  
    	    }  
    	});  
    	callables.add(new Callable<String>() {  
    	    public String call() throws Exception {  
    	        return "Task 3";  
    	    }  
    	});  
    	 
//    	invokeAny() 方法要求一系列的 Callable 或者其子接口的实例对象。调用这个方法并不会返回一个 Future，但它返回其中一个 Callable 对象的结果。无法保证返回的是哪个 Callable 的结果 - 只能表明其中一个已执行结束。
//    	如果其中一个任务执行结束(或者抛了一个异常)，其他 Callable 将被取消。
    	String result = executorService.invokeAny(callables);      	  
    	System.out.println("result = " + result);  
    	
//    	invokeAll()
//    	invokeAll() 方法将调用你在集合中传给 ExecutorService 的所有 Callable 对象。invokeAll() 返回一系列的 Future 对象，通过它们你可以获取每个 Callable 的执行结果。
//    	记住，一个任务可能会由于一个异常而结束，因此它可能没有 "成功"。无法通过一个 Future 对象来告知我们是两种结束中的哪一种。
    	List<Future<String>> futures = executorService.invokeAll(callables);  
    	  
    	for(Future<String> future : futures){  
    	    System.out.println("future.get = " + future.get());  
    	}  
    	  
    	
//    	使用完 ExecutorService 之后你应该将其关闭，以使其中的线程不再运行。
//    	比如，如果你的应用是通过一个 main() 方法启动的，之后 main 方法退出了你的应用，
//    	如果你的应用有一个活动的 ExexutorService
//    	它将还会保持运行。ExecutorService 里的活动线程阻止了 JVM 的关闭。
    	executorService.shutdown(); 
    }  
}
