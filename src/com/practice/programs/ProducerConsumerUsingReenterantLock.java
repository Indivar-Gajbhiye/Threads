package com.practice.programs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class PrCo {

	private Lock lock = new ReentrantLock();
	private Condition notEmpty = lock.newCondition();
	private Condition notFull = lock.newCondition();
	private List<Integer> list = new ArrayList<>();
	private final int LIMIT = 5;
	private int counter = 0;

	public void produce() throws InterruptedException {
		lock.lock();
		try {
			while (list.size() == LIMIT) {
				System.out.println(Thread.currentThread().getName() + ": Buffer is full, waiting");
				notEmpty.await();
			}
			System.out.println("Added value:" + ++counter);
			list.add(counter);
			notFull.signal();
		} finally {
			lock.unlock();
		}

	}

	public void consume() throws InterruptedException {
		lock.lock();
		try {
			while (list.size() == 0) {
				System.out.println(Thread.currentThread().getName()+" Buffer is empty.");
				notFull.await();
			}
			System.out.println("Consumed Value: " + list.remove(--counter));
			notEmpty.signal();
		} finally {
			lock.unlock();
		}
	}
}

public class ProducerConsumerUsingReenterantLock {

	public static void main(String[] args) {
		PrCo prodCon = new PrCo();

		Thread t1 = new Thread(() -> {
			try {
				prodCon.produce();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, "Producer");
		Thread t2 = new Thread(() -> {
			try {
				prodCon.consume();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, "Consumer");

		t1.start();
		t2.start();
	}

}
