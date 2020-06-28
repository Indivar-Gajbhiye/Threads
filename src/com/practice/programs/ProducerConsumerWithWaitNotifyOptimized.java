package com.practice.programs;

import java.util.LinkedList;
import java.util.List;

class ProduceConsume {
	private List<Integer> list = new LinkedList<>();
	private final int capacity = 5;
	private Object lock = new Object();
	private int counter = 0;
	
	public void produce() throws InterruptedException {
		while (true) {
		synchronized (lock) {
			while (list.size() == capacity) {
				lock.wait();
			}
			System.out.println("Added element:"+ ++counter);
			Thread.sleep(200);
			list.add(counter);
			if (list.size() == capacity) {
				lock.notify();
			}
		}
		}
	}
	
	public void consume() throws InterruptedException {
		while (true) {
		synchronized (lock) {
			while (list.size() == 0) {
				lock.wait();
			}
			System.out.println("Removed element:" + list.remove(0));
			Thread.sleep(200);
			//list.add(counter);
			if (list.size() == 0) {
				lock.notify();
			}
		}
		}
	}

}

public class ProducerConsumerWithWaitNotifyOptimized {

		public static void main(String[] args) {
			
			ProduceConsume pc = new ProduceConsume();
			
			Thread t1 = new Thread(() -> {
				try {
					pc.produce();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
			
			Thread t2 = new Thread(() -> {
				try {
					pc.consume();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
			
			t1.start();
			t2.start();

	}

}
