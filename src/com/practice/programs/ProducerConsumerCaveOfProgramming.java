package com.practice.programs;

import java.util.LinkedList;

class ProdConHandler {

	private LinkedList<Integer> queue = new LinkedList<>();
	private final int LIMIT = 4;
	private Object lock = new Object();

	public void producer() throws InterruptedException {
		int value = 0;
		while (true) {
			synchronized (lock) {

				while (queue.size() == LIMIT) {
					System.out.println(" Queue size is full: " + queue.size());
					lock.wait();
				}

				queue.add(value);
				System.out.println("Added value : " + value + " to the queue; list size is : " + queue.size());
				value++;
				lock.notify();
			}
		}
	}

	public void consumer() throws InterruptedException {

		while (true) {

			synchronized (lock) {
				while (queue.size() == 0) {
					lock.wait();
				}
				System.out.print("List size is: " + queue.size());
				int value = queue.removeFirst();
				System.out.println("; value is: " + value);
				lock.notify();
			}
			Thread.sleep(1000);
		}
	}
}

public class ProducerConsumerCaveOfProgramming {

	public static void main(String[] args) {
		ProdConHandler prodConHandler = new ProdConHandler();

		Thread t1 = new Thread(() -> {
			try {
				prodConHandler.producer();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		Thread t2 = new Thread(() -> {
			try {
				prodConHandler.consumer();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		t1.start();
		t2.start();
	}

}
