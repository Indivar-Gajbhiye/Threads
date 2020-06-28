package com.practice.concurrency;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Process implements Runnable {

	private int id;
	private CyclicBarrier barrier;
	private Random random;

	public Process(int id, CyclicBarrier barrier) {
		super();
		this.id = id;
		this.barrier = barrier;
		this.random = new Random();
	}

	@Override
	public void run() {
		doSomeWork();
	}

	private void doSomeWork() {
		System.out.println("Thread with Id " + this.id + " starts the task...");
		try {
			// Sleep indicates some work which is being done by each thread.
			Thread.sleep(random.nextInt(3000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Thread with Id " + this.id + " finished...");
		try {
			barrier.await();
			System.out.println("After the barrier is broken, each thread will execute further...");
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "" + this.id;
	}
}

public class CyclicBarrierDemo {

	public static void main(String[] args) {
		CyclicBarrier barrier = new CyclicBarrier(5, () -> System.out.println("All the tasks are finised"));
		ExecutorService service = Executors.newFixedThreadPool(5);

		for (int i = 1; i <= 5; i++) {
			service.execute(new Process(i, barrier));
		}

		service.shutdown();
	}

}
