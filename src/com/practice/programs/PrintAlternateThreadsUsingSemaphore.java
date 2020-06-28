package com.practice.programs;

import java.util.concurrent.Semaphore;

class PC {

	private Semaphore oneSemaphore = new Semaphore(1);
	private Semaphore twoSemaphore = new Semaphore(0);
	private Semaphore threeSemaphore = new Semaphore(0);

	public void printOne() throws InterruptedException {
		for (int i = 1;; i += 3) {
			oneSemaphore.acquire();
			Thread.sleep(200);
			System.out.print(i + ", ");
			twoSemaphore.release();
		}
	}

	public void printTwo() throws InterruptedException {
		for (int i = 2;; i += 3) {
			twoSemaphore.acquire();
			Thread.sleep(200);
			System.out.print(i + ", ");
			threeSemaphore.release();
		}
	}

	public void printThree() throws InterruptedException {
		for (int i = 3;; i += 3) {
			threeSemaphore.acquire();
			Thread.sleep(200);
			System.out.print(i + ", ");
			oneSemaphore.release();
		}
	}

}

public class PrintAlternateThreadsUsingSemaphore {

	public static void main(String[] args) {
		PC pc = new PC();
		Thread t1 = new Thread(() -> {
			try {
				pc.printOne();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		Thread t2 = new Thread(() -> {
			try {
				pc.printTwo();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		Thread t3 = new Thread(() -> {
			try {
				pc.printThree();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		t1.start();
		t2.start();
		t3.start();
	}

}
