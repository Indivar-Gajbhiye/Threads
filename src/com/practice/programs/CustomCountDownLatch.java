package com.practice.programs;

public class CustomCountDownLatch {
	
	private int countOfParties;
	
	public CustomCountDownLatch(int countOfParties) {
		this.countOfParties = countOfParties;
	}
	
	public synchronized void await() throws InterruptedException {
		if (countOfParties > 0) {
			this.wait();
		}
	}
	
	public synchronized void countDown() {
		countOfParties--;
		
		if (countOfParties == 0) {
			this.notify();
		}
	}
	public static void main(String[] args) {

	}

}
