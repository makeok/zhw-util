package com.zhw.test.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockDemo {
//	synchronized 代码块不能够保证进入访问等待的线程的先后顺序。
//	你不能够传递任何参数给一个 synchronized 代码块的入口。因此，对于 synchronized 代码块的访问等待设置超时时间是不可能的事情。
//	synchronized 块必须被完整地包含在单个方法里。而一个 Lock 对象可以把它的 lock() 和 unlock() 方法的调用放在不同的方法里。
	public static void LockTest() throws InterruptedException {
		Lock lock = new ReentrantLock();  
		  
		lock.lock();  
		//加锁直到成功为止
		lock.lockInterruptibly();		
		lock.tryLock();
		lock.tryLock( 10, TimeUnit.SECONDS);
		
		//critical section  
		
		lock.unlock();  
	}
	
	
	public static void ReadWriteLockTest(){
		ReadWriteLock readWriteLock = new ReentrantReadWriteLock();  
		  
		  
		readWriteLock.readLock().lock();  
		  
		    // multiple readers can enter this section  
		    // if not locked for writing, and not writers waiting  
		    // to lock for writing.  
		  
		readWriteLock.readLock().unlock();  
		  
		  
		readWriteLock.writeLock().lock();  
		  
		    // only one writer can enter this section,  
		    // and only if no threads are currently reading.  
		  
		readWriteLock.writeLock().unlock();  


	}
}
