package com.practice.programs;

import java.util.concurrent.atomic.AtomicInteger;

class Viewer implements Runnable {
	
	private AtomicInteger integer;
	private int threadId;
	private static Object lock = new Object();
	
	public Viewer(AtomicInteger integer, int threadId) {
		this.integer = integer;
		this.threadId = threadId;
	}
	
	@Override
	public void run() {
		while (integer.get() <= 9) {
			synchronized (lock) {
				if (integer.get() % 3 == this.threadId) {
					// if (integer.get() <= 10) {
					System.out.println(Thread.currentThread().getName() + " " + integer.getAndIncrement() + " Hello");
					// }
				}
			}
		}
	}
}

public class PrintAlternateUsingAtomic {

	public static void main(String[] args) {
		AtomicInteger integer = new AtomicInteger(1);
		
		Thread t1 = new Thread(new Viewer(integer, 1), "T1");
		Thread t2 = new Thread(new Viewer(integer, 2), "T2");
		Thread t3 = new Thread(new Viewer(integer, 0), "T3");

		t1.start();
		t2.start();
		t3.start();
	}

}
