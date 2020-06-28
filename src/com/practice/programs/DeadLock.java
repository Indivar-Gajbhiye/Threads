package com.practice.programs;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Demo {
	
	String str1 = "Java";
	String str2 = "SQL";
	
	Lock lock = new ReentrantLock();
	
	public void method1() throws InterruptedException {
		while (true) {
		lock.lock();
			//synchronized (str1) {
				Thread.sleep(1);
			lock.lock();
					System.out.println(str1+str2);
			lock.unlock();
			lock.unlock();
			//}
		}
	}
	
	public void method2() {
		while (true) {
//			synchronized (str2) {
	//			synchronized (str1) {
		lock.lock();
		lock.lock();
					System.out.println(str2+str1);
					lock.unlock();
					lock.unlock();
				}
		//	}
		//}
	}
}

public class DeadLock {

	public static void main(String[] args) {
		Demo demo = new Demo();
		new Thread(() -> {
			try {
				demo.method1();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
		new Thread(() -> demo.method2()).start();
	}
}
