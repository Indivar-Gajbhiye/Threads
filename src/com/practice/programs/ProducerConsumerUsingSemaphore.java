package com.practice.programs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

class ProCon {
	private List<Integer> list = new ArrayList<>();
	private Semaphore producerSemaphore = new Semaphore(1);
	private Semaphore consumerSemaphore = new Semaphore(0);
	private int counter;

	public void produce() throws InterruptedException {
		for (;;) {
			producerSemaphore.acquire();
			System.out.println("Added :" + ++counter);
			list.add(counter);
			consumerSemaphore.release();
		}
	}

	public void consume() throws InterruptedException {
		for (;;) {
			consumerSemaphore.acquire();
			System.out.println("Removed: " + list.remove(--counter));
			producerSemaphore.release();
		}
	}
}

public class ProducerConsumerUsingSemaphore {

	public static void main(String[] args) {
		ProCon proCon = new ProCon();

		Thread t1 = new Thread(() -> {
			try {
				proCon.produce();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		Thread t2 = new Thread(() -> {
			try {
				proCon.consume();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		t1.start();
		t2.start();
	}

}
