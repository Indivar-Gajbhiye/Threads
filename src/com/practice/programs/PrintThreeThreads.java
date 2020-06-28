package com.practice.programs;

import java.util.concurrent.atomic.AtomicInteger;

class Display {
	private Object lock;
	private AtomicInteger integer;
	private int threadId;
	
	/*public Display(AtomicInteger integer, Object lock, int threadId) {
		this.integer = integer;
		this.lock = lock;
		this.threadId = threadId;
	}*/
 
	
	public void printFirst() throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			synchronized (lock) {
				while (integer.get() % 3 != 0) {
					lock.wait();
				}
				System.out.print("My name ");
				integer.incrementAndGet();
				lock.notifyAll();
			}
		}
	}

	public void printSecond() throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			synchronized (lock) {
				while (integer.get() % 3 != 1) {
					lock.wait();
				}
				System.out.print("is ");
				integer.incrementAndGet();
				lock.notifyAll();
			}
		}

	}

	public void printThird() throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			synchronized (lock) {
				while (integer.get() % 3 != 2) {
					lock.wait();
				}
				System.out.print("Indivar");
				integer.incrementAndGet();
				lock.notifyAll();
				System.out.println();
			}
		}
	}
}

public class PrintThreeThreads {

	public static void main(String[] args) {
		Display display = new Display();

		Thread t1 = new Thread(() -> {
			try {
				display.printFirst();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		Thread t2 = new Thread(() -> {
			try {
				display.printSecond();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		Thread t3 = new Thread(() -> {
			try {
				display.printThird();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		t1.start();
		t2.start();
		t3.start();

		try {
			t1.join();
			t2.join();
			t3.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}