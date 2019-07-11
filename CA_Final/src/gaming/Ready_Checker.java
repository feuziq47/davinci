package gaming;

import java.util.ArrayList;
import java.util.Iterator;

class ReadyChecker implements Runnable{
	private int cnt;
	private boolean notBroken;
	Participants part;
	public ReadyChecker(Participants part){
		cnt = 0;
		notBroken = true;
		this.part = part;
	}
	@Override
	public void run() {
		while(notBroken) {
			System.out.println(part.isAllReady());
			System.out.println("Running : " + cnt++);
			
		}
	}
	
	public void stop() {
		notBroken = false;
	}
}