package com.zhw.test.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class ForkJoinPoolDemo {
	static ForkJoinPool forkJoinPool = new ForkJoinPool(4);  
	public static void main(String[] args) throws Exception {

	}

	public static void MyRecursiveActionTest() {
		MyRecursiveAction myRecursiveAction = new MyRecursiveAction(24);

		forkJoinPool.invoke(myRecursiveAction);
	}

	public static void MyRecursiveTaskTest() {
		MyRecursiveTask myRecursiveTask = new MyRecursiveTask(128);

		long mergedResult = forkJoinPool.invoke(myRecursiveTask);

		System.out.println("mergedResult = " + mergedResult);
	}

	// RecursiveAction 是一种没有任何返回值的任务。它只是做一些工作，比如写数据到磁盘，然后就退出了。
	// 一个 RecursiveAction 可以把自己的工作分割成更小的几块，这样它们可以由独立的线程或者 CPU 执行。
	public static class MyRecursiveTask extends RecursiveTask<Long> {

		private long workLoad = 0;

		public MyRecursiveTask(long workLoad) {
			this.workLoad = workLoad;
		}

		protected Long compute() {

			// if work is above threshold, break tasks up into smaller tasks
			if (this.workLoad > 16) {
				System.out.println("Splitting workLoad : " + this.workLoad);

				List<MyRecursiveTask> subtasks = new ArrayList<MyRecursiveTask>();
				subtasks.addAll(createSubtasks());

				for (MyRecursiveTask subtask : subtasks) {
					subtask.fork();
				}

				long result = 0;
				for (MyRecursiveTask subtask : subtasks) {
					result += subtask.join();
				}
				return result;

			} else {
				System.out.println("Doing workLoad myself: " + this.workLoad);
				return workLoad * 3;
			}
		}

		private List<MyRecursiveTask> createSubtasks() {
			List<MyRecursiveTask> subtasks = new ArrayList<MyRecursiveTask>();

			MyRecursiveTask subtask1 = new MyRecursiveTask(this.workLoad / 2);
			MyRecursiveTask subtask2 = new MyRecursiveTask(this.workLoad / 2);

			subtasks.add(subtask1);
			subtasks.add(subtask2);

			return subtasks;
		}
	}

	// RecursiveTask 是一种会返回结果的任务。它可以将自己的工作分割为若干更小任务，
	// 并将这些子任务的执行结果合并到一个集体结果。可以有几个水平的分割和合并。
	public static class MyRecursiveAction extends RecursiveAction {

		private long workLoad = 0;

		public MyRecursiveAction(long workLoad) {
			this.workLoad = workLoad;
		}

		@Override
		protected void compute() {

			// if work is above threshold, break tasks up into smaller tasks
			if (this.workLoad > 16) {
				System.out.println("Splitting workLoad : " + this.workLoad);

				List<MyRecursiveAction> subtasks = new ArrayList<MyRecursiveAction>();

				subtasks.addAll(createSubtasks());

				for (RecursiveAction subtask : subtasks) {
					subtask.fork();
				}

			} else {
				System.out.println("Doing workLoad myself: " + this.workLoad);
			}
		}

		private List<MyRecursiveAction> createSubtasks() {
			List<MyRecursiveAction> subtasks = new ArrayList<MyRecursiveAction>();

			MyRecursiveAction subtask1 = new MyRecursiveAction(this.workLoad / 2);
			MyRecursiveAction subtask2 = new MyRecursiveAction(this.workLoad / 2);

			subtasks.add(subtask1);
			subtasks.add(subtask2);

			return subtasks;
		}

	}
}
