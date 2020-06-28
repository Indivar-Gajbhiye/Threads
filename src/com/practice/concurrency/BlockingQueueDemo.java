package com.practice.concurrency;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class FirstWorker implements Runnable {

	private BlockingQueue<Integer> blockingQueue;

	public FirstWorker(BlockingQueue<Integer> blockingQueue) {
		this.blockingQueue = blockingQueue;
	}

	@Override
	public void run() {

		int counter = 0;

		while (counter <=10) {
			try {
				blockingQueue.put(counter);
				System.out.println("Putting item into the queue:" + counter);
				counter++;
				//Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}
}

class SecondWorker implements Runnable {

	private BlockingQueue<Integer> blockingQueue;

	public SecondWorker(BlockingQueue<Integer> blockingQueue) {
		this.blockingQueue = blockingQueue;
	}

	@Override
	public void run() {

		while (true) {
			try {
				Integer number = blockingQueue.take();
				System.out.println("Removed item from the queue:" + number);
				// counter++;
				//Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

public class BlockingQueueDemo {

	public static void main(String[] args) {
		BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(5);

		FirstWorker firstWorker = new FirstWorker(blockingQueue);
		SecondWorker secondWorker = new SecondWorker(blockingQueue);

		new Thread(firstWorker).start();
		new Thread(secondWorker).start();
	}

}
