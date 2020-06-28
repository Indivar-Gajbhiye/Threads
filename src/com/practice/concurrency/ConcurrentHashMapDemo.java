package com.practice.concurrency;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConcurrentHashMapDemo {

	public static void main(String[] args) {
		ConcurrentMap<String, Integer> concurrentMap = new ConcurrentHashMap<>();
		new Thread(new Writer(concurrentMap)).start();
		new Thread(new Reader(concurrentMap)).start();
	}

}

class Writer implements Runnable {

	private ConcurrentMap<String, Integer> concurrentMap;

	public Writer(ConcurrentMap<String, Integer> concurrentMap) {
		super();
		this.concurrentMap = concurrentMap;
	}

	@Override
	public void run() {
		try {
			concurrentMap.put("F", 1);
			concurrentMap.put("B", 2);
			concurrentMap.put("E", 3);
			Thread.sleep(1000);
			concurrentMap.put("D", 4);
			Thread.sleep(1000);
			concurrentMap.put("A", 5);
		} catch (InterruptedException exception) {
			exception.printStackTrace();
		}

	}

}

class Reader implements Runnable {

	private ConcurrentMap<String, Integer> concurrentMap;

	public Reader(ConcurrentMap<String, Integer> concurrentMap) {
		super();
		this.concurrentMap = concurrentMap;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(1000);
			System.out.println(concurrentMap.get("E"));
			Thread.sleep(1000);
			System.out.println(concurrentMap.get("A"));
			System.out.println(concurrentMap.get("D"));
		} catch (InterruptedException exception) {
			exception.printStackTrace();
		}

	}
}