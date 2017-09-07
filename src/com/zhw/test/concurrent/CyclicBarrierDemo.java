package com.zhw.test.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

//java.util.concurrent.CyclicBarrier 类是一种同步机制，它能够对处理一些算法的线程实现同步。
//换句话讲，它就是一个所有线程必须等待的一个栅栏，直到所有线程都到达这里，然后所有线程才可以继续做其他事情
public class CyclicBarrierDemo {

	static Runnable barrier1Action = new Runnable() {
		public void run() {
			System.out.println("BarrierAction 1 executed ");
		}
	};
	static Runnable barrier2Action = new Runnable() {
		public void run() {
			System.out.println("BarrierAction 2 executed ");
		}
	};
	public static void main(String[] args) throws Exception {
		//2个阻塞点
		CyclicBarrier barrier1 = new CyclicBarrier(2, barrier1Action);
		CyclicBarrier barrier2 = new CyclicBarrier(2, barrier2Action);

		CyclicBarrierRunnable barrierRunnable1 = new CyclicBarrierRunnable(barrier1, barrier2);

		CyclicBarrierRunnable barrierRunnable2 = new CyclicBarrierRunnable(barrier1, barrier2);

		new Thread(barrierRunnable1).start();
		new Thread(barrierRunnable2).start();
	}
	
	
	public static class CyclicBarrierRunnable implements Runnable{  
		  
	    CyclicBarrier barrier1 = null;  
	    CyclicBarrier barrier2 = null;  
	  
	    public CyclicBarrierRunnable(  
	            CyclicBarrier barrier1,  
	            CyclicBarrier barrier2) {  
	  
	        this.barrier1 = barrier1;  
	        this.barrier2 = barrier2;  
	    }  
	  
//	    满足以下任何条件都可以让等待 CyclicBarrier 的线程释放：
//	    最后一个线程也到达 CyclicBarrier(调用 await())
//	    当前线程被其他线程打断(其他线程调用了这个线程的 interrupt() 方法)
//	    其他等待栅栏的线程被打断
//	    其他等待栅栏的线程因超时而被释放
//	    外部线程调用了栅栏的 CyclicBarrier.reset() 方法
	    public void run() {  
	        try {  
	            Thread.sleep(1000);  
	            System.out.println(Thread.currentThread().getName() +  
	                                " waiting at barrier 1");  
	            this.barrier1.await();  
	  
	            Thread.sleep(1000);  
	            System.out.println(Thread.currentThread().getName() +  
	                                " waiting at barrier 2");  
	            this.barrier2.await();  
	            this.barrier2.await(10, TimeUnit.SECONDS);  
	  
	            System.out.println(Thread.currentThread().getName() +  
	                                " done!");  
	  
	        } catch (InterruptedException e) {  
	            e.printStackTrace();  
	        } catch (BrokenBarrierException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				e.printStackTrace();
			}  
	    }  
	}  
}
