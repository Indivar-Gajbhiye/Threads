package com.practice.programs;

import java.util.ArrayList;
import java.util.List;

class Processor {

	private List<Integer> list = new ArrayList<>();
	private final int LIMIT = 5;
	
	private final int BOTTOM = 0;
	private final Object lock = new Object();
	private int value = 0;

	public void producer() throws InterruptedException {

		synchronized (lock) {

			while (true) {
				while (list.size() == LIMIT) {
					System.out.println("The list is full. Waiting for consumer to consume");
					lock.wait();
				}
				System.out.println("Adding value:" + value);
				list.add(value);
				value++;
				lock.notify();

				Thread.sleep(500);
			}

		}
	}

	public void consumer() throws InterruptedException {

		synchronized (lock) {

			while (true) {
				while (list.size() == BOTTOM) {
					System.out.println("List is empty. Cannot consume any elements");
					lock.wait();
				} 
					System.out.println("Consumed Value:" + list.remove(--value));
					lock.notify();
					Thread.sleep(500);
			}
		}
	}
}

public class ProducerConsumerUsingWaitNotify {

	public static void main(String[] args) {
		Processor processor = new Processor();
		Thread t1 = new Thread(() -> {
			try {
				processor.producer();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		Thread t2 = new Thread(() -> {
			try {
				processor.consumer();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		t1.start();
		t2.start();
	}

}
