package com.practice.concurrency;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Worker {

	private List<Integer> sharedQueue = new LinkedList<>();
	private final int LIMIT = 4;
	private int value = 0;
	private Lock lock = new ReentrantLock();
	private Condition producerCondition = lock.newCondition();
	private Condition consumerCondition = lock.newCondition();

	public void producer() throws InterruptedException {
		while(true) {
			lock.lock();
			while (sharedQueue.size() == LIMIT) {
				producerCondition.await();
			}
			System.out.println("Produced value:" + value);
			sharedQueue.add(value++);
			consumerCondition.signal();
			lock.unlock();
		}
	}

	public void consumer() throws InterruptedException {
		while (true) {
			lock.lock();
			Thread.sleep(500);
			while (sharedQueue.size() == 0) {
				consumerCondition.await();
			}
			System.out.println("Consumed value:" + sharedQueue.remove(0));
			producerCondition.signal();
			lock.unlock();

		}
	}
}

public class ProducerConsumerUsingLocks {

	public static void main(String[] args) {
		Worker worker = new Worker();
		Thread t1 = new Thread(() -> {
			try {
				worker.producer();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		});

		Thread t2 = new Thread(() -> {
			try {
				worker.consumer();
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
