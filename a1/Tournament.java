import java.util.Arrays;
import java.io.*;
import java.lang.Math;
import java.util.concurrent.atomic.AtomicInteger;

public class Tournament{
	int N;
	int level;
	private static AtomicInteger[][] p_id;
	private Peterson[] lock;
	public Tournament(int numProc){
		this.N = numProc;
		this.level = (int)Math.ceil(Math.log(N) / Math.log(2));
		
		this.p_id = new AtomicInteger[N][level];
//		System.out.println("level: " + level);
		for (int i = 0; i < N; i++){
			for (int j = 0; j < level; j++){
				p_id[i][j] = new AtomicInteger(0);
			}
		}
//		Arrays.fill(p_id,0);
		this.lock = new Peterson[N];
		for (int i = 0 ; i < N; i++){
			lock[i] = new Peterson();
		}
	}	
	public void acquire_mutex(int i){
		int node_id = i + (N - 1);
//		int [] p_id = new int[level];
//		Arrays.fill(p_id,0);
		for (int k = 0; k < level; k++){
			p_id[i-1][k].set(node_id % 2);
			node_id = (int)Math.floor(node_id / 2);
/*
			System.out.println("acq_mutex: before requestCS");
			System.out.println("node_id: " + node_id);
			System.out.println("turn: " + lock[node_id].turn);
			System.out.println("wantCS[0]: " + lock[node_id].wantCS[0]);
			System.out.println("wantCS[1]: " + lock[node_id].wantCS[1]);

	*/		
			lock[node_id].requestCS(p_id[i-1][k].get());
/*
			System.out.println("acq_mutex: after requestCS");
			System.out.println("node_id: " + node_id);
			System.out.println("turn: " + lock[node_id].turn);
			System.out.println("wantCS[0]: " + lock[node_id].wantCS[0]);
			System.out.println("wantCS[1]: " + lock[node_id].wantCS[1]);
*/
		}
	}
	public void release_mutex(int i){
		int node_id = 1;
		for (int k = level - 1; k >= 0; k--){
			lock[node_id].releaseCS(p_id[i-1][k].get());
			node_id = 2 * node_id + p_id[i-1][k].get();
		}
	}
}
