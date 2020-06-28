package com.practice.programs;

import java.util.stream.IntStream;

public class CustomArrayBlockingQueue {

	private int[] arr;
	private int size = 0;
	private int limit = 7;
	private Object lock = new Object();
	
	public CustomArrayBlockingQueue(int size) {
		arr = new int[size];
		this.limit = size;
	}

	public void put(int num) throws InterruptedException {
		synchronized (lock) {
			while (size == limit) {
				System.out.println("Queue is full," + Thread.currentThread().getName() + " is waiting to add.");
				lock.wait();
			}
			System.out.println(Thread.currentThread().getName() + " is adding the element " + num + " to the queue.");
			arr[size] = num;
			size++;
			lock.notifyAll();
		}
	}

	public int take() throws InterruptedException {
		int num = 0;
		synchronized (lock) {
			while (size == 0) {
				System.out.println("Queue is empty, " + Thread.currentThread().getName() + " is waiting to consume.");
				lock.wait();
			}
			System.out.println(Thread.currentThread().getName() + " is consuming from the queue");
			num = arr[--size];
			lock.notifyAll();
		}
		
		return num;
	}
	public static void main(String[] args) {
		CustomArrayBlockingQueue queue = new CustomArrayBlockingQueue(2);
		Thread producer1 = new Thread(() -> IntStream.range(0, 4).forEach(num -> {
			try {
				queue.put(num);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}), "Producer Thread 1");
		
		Thread consumer1 = new Thread(() -> IntStream.range(0, 4).forEach(num -> {
			try {
				System.out.println(queue.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}), "Consumer Thread 1");
		
		Thread producer2 = new Thread(() -> IntStream.range(0, 4).forEach(num -> {
			try {
				queue.put(num);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}), "Producer Thread 2");
		Thread consumer2 = new Thread(() -> IntStream.range(0, 3).forEach(num -> {
			try {
				System.out.println(queue.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}), "Consumer Thread 2");
		
		producer1.start();
		consumer1.start();
		producer2.start();
		consumer2.start();

	}
}
