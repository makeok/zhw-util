package com.zhw.test.guava;

import java.io.IOException;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.google.common.base.Objects;
import com.google.common.base.Throwables;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.google.common.math.LongMath;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

public class ThrowablesDemo {
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		test();
	}

	@SuppressWarnings("deprecation")
	public static void test() throws IOException {
		System.out.println(Objects.equal(null, "a"));
		Objects.firstNonNull(ThrowablesDemo.class, ThrowablesDemo.class);
		System.out.println(Objects.toStringHelper("GuavaDemo").add("x", 1).add("y", 1).toString());

		try {

		} catch (Exception e) {
			// 如果Throwable是Error或RuntimeException,否则把Throwable包装成RuntimeException抛出
			Throwables.propagate(e);
			// Throwable类型为Error或RuntimeException才抛出
			Throwables.propagateIfPossible(e, IOException.class, NullPointerException.class);
			// Throwable类型为X才抛出
			Throwables.propagateIfInstanceOf(e, IOException.class);
			Throwables.throwIfUnchecked(e);

			Lists.newArrayList();

			Multiset<String> multiset1 = HashMultiset.create();
			multiset1.add("a", 2);
			TreeMap<Integer, Integer> a = new TreeMap<Integer, Integer>();

		}
	}

	public class Explosion{
		
	}
	public static Explosion callback() {
		return null;
	}
	private static void walkAwayFrom(Explosion explosion) {
		// TODO Auto-generated method stub
		
	}
	private static void battleArchNemesis() {
		// TODO Auto-generated method stub
		
	}
	// 异步计算
	// 我们强烈地建议你在代码中多使用ListenableFuture来代替JDK的 Future, 因为：
	// 大多数Futures 方法中需要它。
	// 转到ListenableFuture 编程比较容易。
	// Guava提供的通用公共类封装了公共的操作方方法，不需要提供Future和ListenableFuture的扩展方法
	public static void Listenable() throws IOException {
		ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
		final ListenableFuture<Explosion> explosion = service.submit(new Callable<Explosion>() {
			public Explosion call() {
				return callback();
			}
		});
		//添加监听函数
		explosion.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    Explosion contents = explosion.get();
                    System.out.println(contents);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }, MoreExecutors.sameThreadExecutor());
		Futures.addCallback(explosion, new FutureCallback<Explosion>() {
			// we want this handler to run immediately after we push the big red
			// button!
			public void onSuccess(Explosion explosion) {
				walkAwayFrom(explosion);
			}

			public void onFailure(Throwable thrown) {
				battleArchNemesis(); // escaped the explosion!
			}
		});
		
	}
	public static void ListenableFuture (){

	}
}
