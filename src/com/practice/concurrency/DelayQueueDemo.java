package com.practice.concurrency;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueDemo {

	public static void main(String[] args) {
		DelayQueue<DelayedWorker> delayQueue = new DelayQueue<>();

		delayQueue.put(new DelayedWorker(1000, "This is the first message.."));
		delayQueue.put(new DelayedWorker(10000, "This is the second message.."));
		delayQueue.put(new DelayedWorker(5000, "This is the third message.."));

		while (!delayQueue.isEmpty()) {
			try {
				System.out.println(delayQueue.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class DelayedWorker implements Delayed {

	private long duration;
	private String message;

	public DelayedWorker(long duration, String message) {
		super();
		this.duration = System.currentTimeMillis() + duration;
		this.message = message;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public int compareTo(Delayed otherDelayed) {
		if (this.getDuration() < ((DelayedWorker) otherDelayed).getDuration()) {
			return -1;
		} else if (this.getDuration() > ((DelayedWorker) otherDelayed).getDuration()) {
			return 1;
		}

		return 0;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(duration - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
	}

	@Override
	public String toString() {
		return this.getMessage();
	}
}
