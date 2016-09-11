import java.util.*;
import java.io.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class PIncrement implements Callable<Integer> {
	int incremental;
	int N;
	private static AtomicInteger c_value;
//	static Tournament t;
	public PIncrement(int x, int numThreads){
		this.incremental = x;
		this.N = numThreads;
	}
	public Integer call() {
		int threadID = (int)(Thread.currentThread().getId() % N) + 1;
		for (int i = 0; i < incremental; i++){
//			t.acquire_mutex(threadID);	
		//	c_value = c_value + 1;
			c_value.addAndGet(1);
		//	System.out.println("I am thread " + threadID);
		//	System.out.println("after_increnment c_value: " + c_value);
//			t.release_mutex(threadID);
		}

		return 0;
	}

	public static int parallelIncrement(int c, int numThreads) {
        // your implementation goes here
		try{	
		System.out.println("To debug:");
		c_value = new AtomicInteger(c);
		System.out.println("c_value: " + c_value.get());
		ExecutorService es = Executors.newCachedThreadPool();
		List<Future<Integer>> futureList = new ArrayList<Future<Integer>>();
//		t = new Tournament(numThreads);
		//PetersonN pn = new PetersonN(numThreads);
		for (int i = 0; i < numThreads; i++){
			PIncrement p = new PIncrement(1200000/numThreads,numThreads);
			futureList.add(es.submit(p));
		}	
//		System.out.println("aftersubmite ");
		for (int i = 0; i < futureList.size(); i++){
			futureList.get(i).get();
		}
//		System.out.println("afterget");
		es.shutdown();
		//Peterson pa = new Peterson();
		return c_value.get();
		} catch(Exception e){System.err.println(e);return 1;}
	}

	public static void main(String [] args){
		int c = 10;
		int numThreads = 4;
		System.out.println("C: " + parallelIncrement(c,numThreads));
	}
}
