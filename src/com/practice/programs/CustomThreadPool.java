package com.practice.programs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomThreadPool {
	
	private AtomicBoolean execute; 
	
	/**
	 *  Queue of Runnables
	 */
	private BlockingQueue<Runnable> runnables;
    private List<PoolThread> threads ;

    public CustomThreadPool(int noOfThread) {
    	
        this.runnables = new LinkedBlockingQueue<>();
        this.execute = new AtomicBoolean(true);
        this.threads = new ArrayList<>();
        
		for (int i = 0; i < noOfThread; i++) {
			this.threads.add(new PoolThread(execute, runnables));
		}
        for(PoolThread thread : threads){
            thread.start();
        }
    }

    public synchronized void  execute(Runnable task) throws Exception{
		if (this.execute.get()) {
			runnables.put(task);
		}
    }

    public void terminate() {
        runnables.clear();
        stop();
    }
    
    public void stop() {
    	execute.set(false);
    }
}


class PoolThread extends Thread {

    private BlockingQueue<Runnable> runnables = null;
    private AtomicBoolean execute;

    public PoolThread(AtomicBoolean execute, BlockingQueue<Runnable> runnables) {
    	this.execute = execute;
        this.runnables = runnables;
    }

    public void run(){
        while(execute.get() || !runnables.isEmpty()){
            try{
            	Runnable runnable = null;
            	while ((runnable = runnables.take()) != null) {
                runnable.run();
            	}
            } catch(Exception e) {
                //log or otherwise report exception,
                //but keep pool thread alive.
            }
        }
    }

    /*public synchronized void doStop(){
        isStopped = true;
        this.interrupt(); //break pool thread out of dequeue() call.
    }

    public synchronized boolean isStopped(){
        return isStopped;
    }*/
}