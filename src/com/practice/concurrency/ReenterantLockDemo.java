package com.practice.concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReenterantLockDemo {

	private static int counter = 0;
	private static Lock lock = new ReentrantLock();

	private static void increment() {
		try {
			lock.lock();
			for (int i = 0; i < 10000; i++) {
				counter++;
			}
		} finally {
			lock.unlock();
		}

	}

	public static void main(String[] args) {

		Thread t1 = new Thread(() -> increment());
		Thread t2 = new Thread(() -> increment());

		t1.start();
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("The counter is:" + counter);
	}

}
