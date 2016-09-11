import java.util.Arrays;
import java.io.*;

class Peterson implements Lock {
    private volatile boolean wantCS[];
    private volatile int turn;
    public Peterson(){
    	this.wantCS = new boolean[] {false, false};
    	this.turn = 1;
    }
    public void requestCS(int i) {
        int j = 1 - i; 
        wantCS[i] = true; 
        turn = j;
        while (wantCS[j] && (turn == j));
    }
    public void releaseCS(int i) {
        wantCS[i] = false;
    }
}
