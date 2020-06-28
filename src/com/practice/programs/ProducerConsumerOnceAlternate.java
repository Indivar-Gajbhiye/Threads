package com.practice.programs;

import java.util.ArrayList;
import java.util.List;

class Generator {

	private final Object lock = new Object();
	private List<Integer> list = new ArrayList<>();

	public void produce() throws InterruptedException {

		synchronized (lock) {
			for (int i = 1; i <= 10; i++) {
				while (list.size() >= 1) {
					lock.wait();
				}
				System.out.println("Produced value:" + i);
				list.add(i);
				lock.notify();
			}
		}
	}

	public void consume() throws InterruptedException {
		synchronized (lock) {
			for (int i = 1; i <= 10; i++) {
				while (list.isEmpty()) {
					lock.wait();
				}
				System.out.println("Consumed value:" + list.remove(0));
				lock.notify();
			}
		}
	}
}

public class ProducerConsumerOnceAlternate {

	public static void main(String[] args) {
		Generator generator = new Generator();

		Thread t1 = new Thread(() -> {
			try {
				generator.produce();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		Thread t2 = new Thread(() -> {
			try {
				generator.consume();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		t1.start();
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
