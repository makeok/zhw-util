package com.zhw.test.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class QueueDemo {

    
    public class Producer implements Runnable{  
    	  
        protected BlockingQueue<Object> queue = null;  
      
        public Producer(BlockingQueue<Object> queue) {  
            this.queue = queue;  
        }  
      
        public void run() {  
            try {  
                queue.put("1");  
                //如果队列里面没有空间，等待一段时间
                queue.offer("1", 10, TimeUnit.SECONDS);
                Thread.sleep(1000);  
                queue.put("2");  
                Thread.sleep(1000);  
                queue.put("3");  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
    
    public class Consumer implements Runnable{  
    	  
        protected BlockingQueue<Object> queue = null;  
      
        public Consumer(BlockingQueue<Object> queue) {  
            this.queue = queue;  
        }  
      
        public void run() {  
            try {  
                System.out.println(queue.take());  
                System.out.println(queue.take());  
                System.out.println(queue.take());  
                //如果队列无效，等待一段时间
                System.out.println(queue.poll(10, TimeUnit.SECONDS));  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
    
    public static void ArrayBlockingQueue() throws InterruptedException {
        BlockingQueue<Object> queue = new ArrayBlockingQueue<Object>(1024);  
        
        Producer producer = new QueueDemo().new Producer(queue);  
        Consumer consumer = new QueueDemo().new Consumer(queue);  
  
        new Thread(producer).start();  
        new Thread(consumer).start();  
  
        Thread.sleep(4000);  
    }
    //DelayQueue 对元素进行持有直到一个特定的延迟到期。
    public static void DelayQueue() throws InterruptedException {  
        DelayQueue queue = new DelayQueue();  
  
        Delayed element1 = new DelayedElement();  
  
        queue.put(element1);  
  
        Delayed element2 = queue.take();  
    }  
    
    public static class DelayedElement implements Delayed {

    	@Override
    	public int compareTo(Delayed o) {
    		// TODO Auto-generated method stub
    		return 0;
    	}

    	@Override
    	public long getDelay(TimeUnit unit) {
    		// TODO Auto-generated method stub
    		return 0;
    	}

    }
    
    
//    LinkedBlockingQueue 类实现了 BlockingQueue 接口。
//    LinkedBlockingQueue 内部以一个链式结构(链接节点)对其元素进行存储。如果需要的话，这一链式结构可以选择一个上限。如果没有定义上限，将使用 Integer.MAX_VALUE 作为上限。
    public static void LinkedBlockingQueue() throws InterruptedException{
    	BlockingQueue<String> unbounded = new LinkedBlockingQueue<String>();  
    	BlockingQueue<String> bounded   = new LinkedBlockingQueue<String>(1024);  
    	  
    	bounded.put("Value");  
    	  
    	String value = bounded.take(); 
    }
//    PriorityBlockingQueue 类实现了 BlockingQueue 接口。
//    PriorityBlockingQueue 是一个无界的并发队列。它使用了和类 java.util.PriorityQueue 一样的排序规则。你无法向这个队列中插入 null 值。
//    所有插入到 PriorityBlockingQueue 的元素必须实现 java.lang.Comparable 接口。因此该队列中元素的排序就取决于你自己的 Comparable 实现。
//    注意 PriorityBlockingQueue 对于具有相等优先级(compare() == 0)的元素并不强制任何特定行为。
//    同时注意，如果你从一个 PriorityBlockingQueue 获得一个 Iterator 的话，该 Iterator 并不能保证它对元素的遍历是以优先级为序的。
    public static void PriorityBlockingQueue() throws InterruptedException {
    	BlockingQueue queue   = new PriorityBlockingQueue();  
    	  
        //String implements java.lang.Comparable  
        queue.put("Value");  
      
        Object value = queue.take();  
    }
    public static void SynchronousQueue() throws InterruptedException {
    	BlockingQueue queue   = new SynchronousQueue();  
  	  
        //String implements java.lang.Comparable  
        queue.put("Value");  
      
        Object value = queue.take();  
    }
    
    public static void LinkedBlockingDeque() throws InterruptedException {
    	BlockingDeque<String> deque = new LinkedBlockingDeque<String>();  
    	  
    	deque.addFirst("1");  
    	deque.addLast("2");  
    	  
    	String two = deque.takeLast();  
    	String one = deque.takeFirst();  
    }
    

}
