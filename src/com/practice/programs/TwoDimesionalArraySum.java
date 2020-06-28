package com.practice.programs;

class ArraySum2D implements Runnable {
	
	private int[][] arr;
	private int threadId;
	private int sum;
	
	public ArraySum2D(int[][] arr, int threadId) {
		super();
		this.arr = arr;
		this.threadId = threadId;
	}

	@Override
	public void run() {
		int arrayCol = arr[0].length;
	    int arrayRow = arr.length;
	    int rowStart = (int)((int)(threadId/2) * (arrayRow/2));
	    int rowEnd = rowStart + (int)(arrayRow/2);
	    int colStart = (int)((threadId%2) * (arrayCol/2));
	    int colEnd = colStart + (int)(arrayCol/2);
	    
		for (int i = rowStart; i < rowEnd; i++) {
			for (int j = colStart; j < colEnd; j++) {
				setSum(getSum() + arr[i][j]);
			}
		}
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}
	
	
}

public class TwoDimesionalArraySum {

}
