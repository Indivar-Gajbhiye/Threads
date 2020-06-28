package com.practice.programs;

import java.util.concurrent.atomic.AtomicInteger;

class Displayer {
	private AtomicInteger integer = new AtomicInteger(1);
	private volatile boolean flag = true;
	private Object lock = new Object();

	public void printFirst() throws InterruptedException {
		while (integer.get() < 10) {
			synchronized (lock) {
				while (!flag) {
					lock.wait();
				}
				System.out.println("0"+ Thread.currentThread().getName());
				Thread.sleep(200);
				flag = false;
				lock.notifyAll();
			}
		}
	}

	public void printEven() throws InterruptedException {
		while (integer.get() < 10) {
			synchronized (lock) {
				while (flag || (!flag && integer.get() % 2 != 0)) {
					lock.wait();
				}
				System.out.println(integer.getAndIncrement() + Thread.currentThread().getName());
				Thread.sleep(200);
				flag = true;
				lock.notifyAll();
			}
		}
	}

	public void printOdd() throws InterruptedException {
		while (integer.get() < 10) {
			synchronized (lock) {
				while (flag || (!flag && integer.get() % 2 == 0)) {
					lock.wait();
				}
				System.out.println(integer.getAndIncrement()+ Thread.currentThread().getName());
				Thread.sleep(200);
				flag = true;
				lock.notifyAll();
			}
		}
	}
}

public class ThreadSequence {

	public static void main(String[] args) {
		Displayer displayer = new Displayer();

		Thread t1 = new Thread(() -> {
			try {
				displayer.printFirst();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, "T1");
		Thread t2 = new Thread(() -> {
			try {
				displayer.printOdd();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, "T2");
		Thread t3 = new Thread(() -> {
			try {
				displayer.printEven();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, "T3");

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
