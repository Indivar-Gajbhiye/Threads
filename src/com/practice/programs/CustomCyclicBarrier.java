package com.practice.programs;

class CustomCyclicBarrier {
	  
    int initialParties; //total parties
    int partiesAwait; //parties yet to arrive
    Runnable cyclicBarrrierEvent;
    
    /**
     * New CyclicBarrier is created where parties number of thread wait for each
     * other to reach common barrier point, when all threads have reached common
     * barrier point, parties number of waiting threads are released and
     * barrierAction (event) is triggered.
     */
	public CustomCyclicBarrier(int parties, Runnable cyclicBarrrierEvent) {
		initialParties = parties;
		partiesAwait = parties;
		this.cyclicBarrrierEvent = cyclicBarrrierEvent;
	}
 
    /**
     *  If the current thread is not the last to arrive(i.e. call await() method) then
     it waits until one of the following things happens -
                  - The last thread to call arrive(i,.e. call await() method), or
                  - Some other thread interrupts the current thread, or
                  - Some other thread interrupts one of the other waiting threads, or
                  - Some other thread times out while waiting for barrier, or
                  - Some other thread invokes reset() method on this cyclicBarrier.
     */
    public synchronized void await() throws InterruptedException {
           //decrements awaiting parties by 1.
           partiesAwait--;
           
           //If the current thread is not the last to arrive, thread will wait.
		if (partiesAwait > 0) {
			wait();
		}
           /*If the current thread is last to arrive, notify all waiting threads, and
            launch event*/
		else {
			/*
			 * All parties have arrive, make partiesAwait equal to
			 * initialParties, so that CyclicBarrier could become cyclic.
			 */
			partiesAwait = initialParties;

			notifyAll(); // notify all waiting threads

			cyclicBarrrierEvent.run(); // launch event
		}
    }
    
	public static void main(String[] args) {
		CustomCyclicBarrier customCyclicBarrier = new CustomCyclicBarrier(3,
				() -> System.out.println("Barrier reached, trigger the runnable action"));
		
		Thread t1 = new Thread(() -> {
			try {
				customCyclicBarrier.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, "T1");
		Thread t2 = new Thread(() -> {
			try {
				customCyclicBarrier.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, "T2");
		Thread t3 = new Thread(() -> {
			try {
				customCyclicBarrier.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, "T3");
		
		t1.start();
		t2.start();
		t3.start();
	}
}