package com.practice.programs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

class ProducerConsumer {

	private List<Integer> list;
	private CountDownLatch latch;

	ProducerConsumer(List<Integer> list, CountDownLatch latch) {
		this.list = list;
		this.latch = latch;
	}

	public void produce() {
		for (int i = 0; i < 10; i++) {
			while (list.size() == 10) {
				try {
					latch.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			list.add(i);
			System.out.println("Added :"+i);
			latch.countDown();
		}
	}

	public void consume() {
		for (int i = 0; i < 10; i++) {
			while (list.size() == 0) {
				try {
					latch.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (list.size() > 0) {
				int number = list.get(i);
				System.out.print(number + ", ");
			}
		}
	}
}

public class ProducerConsumerWithCountDownLatch {

	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		CountDownLatch latch = new CountDownLatch(10);
		ProducerConsumer prodCon = new ProducerConsumer(list, latch);

		Thread t1 = new Thread(() -> prodCon.produce());
		Thread t2 = new Thread(() -> prodCon.consume());

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