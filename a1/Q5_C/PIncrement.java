import java.util.*;
import java.io.*;
import java.util.concurrent.*;

public class PIncrement extends Thread{
	int incremental;
	protected Increment increment = null;

	public PIncrement(int c, Increment increment){
		this.incremental = c;
		this.increment = increment;
	}

	public void run(){
		for(int i = 0; i < incremental; i++){
			increment.add();
		}
	}


	public static int parallelIncrement(int c, int numThreads) {
        // your implementation goes here	
		Increment increment = new Increment(c);
		List<Thread> threadList = new ArrayList<Thread>();

		for (int i = 0; i < numThreads; i++){
			Thread p = new PIncrement(1200000/numThreads, increment);
			threadList.add(p);
		}	
		for (int i = 0; i < threadList.size(); i++){
			threadList.get(i).start();
		}

		try{
			for (int i = 0; i < threadList.size(); i++){
				threadList.get(i).join();
			}
		} catch(Exception e){System.err.println(e);return 1;}
		return increment.c_value;
	}

	public static void main(String [] args){
		int c = 521;
		int numThreads = 8;
		System.out.println("C: " + parallelIncrement(c,numThreads));
	}
}
