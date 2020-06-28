package com.practice.programs;

import java.util.concurrent.atomic.AtomicInteger;

class Pacifier implements Runnable {

	private int threadId;
	private volatile boolean flag;
	private AtomicInteger integer;
	private Object lock;

	public Pacifier(int threadId, boolean flag, AtomicInteger integer, Object lock) {
		this.threadId = threadId;
		this.flag = flag;
		this.integer = integer;
		this.lock = lock;
	}

	@Override
	public void run() {

		synchronized (lock) {
			while (integer.get() % 4 != this.threadId && flag) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName() + " started...");
			System.out.println(Thread.currentThread().getName() + " is Working...");
			integer.getAndIncrement();
			if (integer.get() == 2) {
				flag = false;
			}
			System.out.println(Thread.currentThread().getName() + " has completed his task...");
			lock.notifyAll();
		}
	}
}

public class PrintTwoThreadsInSequence {

	public static void main(String[] args) {
		Object lock = new Object();
		AtomicInteger integer = new AtomicInteger(1);
		Thread t1 = new Thread(new Pacifier(1, false, integer, lock), "T1");
		Thread t2 = new Thread(new Pacifier(2, true, integer, lock), "T2");
		Thread t3 = new Thread(new Pacifier(3, true, integer, lock), "T3");
		Thread t4 = new Thread(new Pacifier(0, true, integer, lock), "T4");

		t1.start();
		t4.start();
		t3.start();
		t2.start();
	}

}
