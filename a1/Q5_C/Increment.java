public class Increment{
	int c_value = 0;
	public Increment(int c){
		c_value = c;
	}

	public synchronized void add(){
       c_value++;
    }
}