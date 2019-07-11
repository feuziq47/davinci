package gaming;
import java.util.ArrayList;
import java.util.Iterator;

public class Participants {
	private ArrayList<Player> players;	
	private int member;
	private int cur_mem;
	public Referee ref;
	
	public Participants(int mem) {
		member = mem;
		cur_mem = 0;
		players = new ArrayList<Player>();
		ref = new Referee();
	}

	public boolean joinPlayer(Player p) {
		if (cur_mem < member) {
			players.add(p);
			cur_mem ++;
			return true;
		}
		return false;
	}
	
	public void resetExceptPlayers() {
		ref.init();
		for(Player p : players)
			p.dropAllBlocks();
	}

	public void exitPlayer(Player p) {
		cur_mem --;
		players.remove(p);
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}

	public Player getPlayer(int index) {
		return players.get(index);
	}
	
	public boolean isAllReady() {
		for(Player p : players) {
			if(!p.getReady())
				return false;
		}
		return true;
	}
	
}