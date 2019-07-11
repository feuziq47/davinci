package gaming;

import java.util.ArrayList;
import java.util.List;

public class Game {

	Components comp;
	Participants part;
//	ReadyChecker readyCheaker;
	
	public Game (int mem) {
		comp = new Components();
		part = new Participants(mem);
//		readyCheaker = new ReadyChecker(part);
//		new Thread(readyCheaker).start();
	}

	public boolean joinPlayer(Player p) {
		return part.joinPlayer(p);
	}
	public void exitPlayer(Player p) {
		part.exitPlayer(p);
	}
	
	public boolean checkReady() {
		return part.isAllReady();
	}
	
	private void init() {
		comp = new Components();
		part.resetExceptPlayers();
	}
	
	public void gameStart() {
		if (!checkReady())
			return;
		init();
		setTurn();
	}
	
//	public ArrayList<Integer> giveBlockAll() {
//		ArrayList<Integer> result = new ArrayList<Integer>();
//		for(Player p : part.getPlayers()) {
//			result.add(giveBlock(p));
//		}
//		return result;
//	}
	
	public int giveBlock(Player p) {
		Block b = comp.popBlock();
		return part.ref.giveBlock(p, b);
	}
	
	public void setTurn() {
		List<Integer> turns = part.ref.getRandomTurns(part.getPlayers().size());
		for (Player p : part.getPlayers()) {
			part.ref.setTurn(p, turns.remove(0));
		}
	}
	
	public Player getCurrentPlayer() {
		return part.ref.getCurrentPlayer();
	}

	public ArrayList<Player> getPlayers() {
		return part.getPlayers();
	}
	
	public Player getPlayer(int index) {
		return part.getPlayer(index);
	}

	public int checkBlock(int pidx, int cidx, boolean isBlack, int num) {
		return part.ref.pointingPlayer(part.getPlayer(pidx), cidx, isBlack, num);
	}
	
	public void nextTurn() {
		part.ref.nextTurn();
//		giveBlock((getCurrentPlayer()));
	}

	
}