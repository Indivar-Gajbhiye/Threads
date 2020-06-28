package com.practice.concurrency;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueDemo {

	public static void main(String[] args) {

		BlockingQueue<Employee> queue = new PriorityBlockingQueue<>(5, (emp1, emp2) -> {
			return emp2.getName().compareTo(emp1.getName());
		});

		new Thread(new FirstWork(queue)).start();
		new Thread(new SecondWork(queue)).start();
	}
}

class FirstWork implements Runnable {

	private BlockingQueue<Employee> blockingQueue;

	public FirstWork(BlockingQueue<Employee> blockingQueue) {
		super();
		this.blockingQueue = blockingQueue;
	}

	@Override
	public void run() {
		try {
			blockingQueue.put(new Employee(101, "Daniel"));
			blockingQueue.put(new Employee(121, "Thomas"));
			blockingQueue.put(new Employee(123, "Michael"));
			Thread.sleep(500);
			blockingQueue.put(new Employee(111, "Jurgen"));
			Thread.sleep(1000);
			blockingQueue.put(new Employee(110, "Jorg"));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

class SecondWork implements Runnable {

	private BlockingQueue<Employee> blockingQueue;

	public SecondWork(BlockingQueue<Employee> blockingQueue) {
		super();
		this.blockingQueue = blockingQueue;
	}

	@Override
	public void run() {
		try {
			System.out.println(blockingQueue.take());
			System.out.println(blockingQueue.take());
			Thread.sleep(1000);
			System.out.println(blockingQueue.take());
			Thread.sleep(1000);
			System.out.println(blockingQueue.take());
			Thread.sleep(1000);
			System.out.println(blockingQueue.take());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class Employee {
	private int id;
	private String name;

	public Employee(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name + " - " + id;
	}
}