package com.practice.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Work implements Runnable{

	private int id;
	private CountDownLatch latch;
	
	public Work(int id, CountDownLatch latch) {
		this.id = id;
		this.latch = latch;
	}
	
	@Override
	public void run() {
		doWork();
		latch.countDown();
	}

	private void doWork() {
		System.out.println("Thread with id "+ this.id + " starts working...");
	}
	
}
public class CountDownLatchDemo {

	public static void main(String[] args) {
		CountDownLatch latch = new CountDownLatch(5);
		
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		for (int i = 1; i <= 5 ; i++) {
			executorService.submit(new Work(i, latch));
		}
		
		try {
			latch.await();
			System.out.println("Proceed Further...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("All the prerequisite are done, please proceed..");
		executorService.shutdown();
	}

}
