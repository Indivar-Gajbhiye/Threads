package com.practice.programs;

class Handler implements Runnable {

	private final int threadId;
	private Object lock;
	private static int num = 1;

	public Handler(Object lock, int threadId) {
		this.lock = lock;
		this.threadId = threadId;
	}

	@Override
	public void run() {
		while (num < 9)
			synchronized (lock) {
				while (num % 3 != this.threadId) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println(Thread.currentThread().getName() + " - " + num + " Hi");
				num++;
				lock.notifyAll();
			}
	}
}

public class PrintThreadSequence {

	public static void main(String[] args) {
		Object lock = new Object();
		Thread t1 = new Thread(new Handler(lock, 1), "T1");
		Thread t2 = new Thread(new Handler(lock, 2), "T2");
		Thread t3 = new Thread(new Handler(lock, 0), "T3");

		t1.start();
		t2.start();
		t3.start();
	}
}
