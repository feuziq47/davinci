package gaming;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Referee implements LocationSetter{
	
	private ArrayList <Player> turnList;
	
	public Referee() {
		init();
	}
	
	public void init() {
		turnList = new ArrayList<Player>();
	}
	
	@Override
	public void set_Location() {
		
	}

	public int giveBlock(Player p, Block b) {
		int index;
		if (b instanceof Joker_Block) {
			Random r = new Random();
			index = r.nextInt(p.getBlocks().size() + 1);
			p.addBlock(index, b);
		}
		else {
			Iterator<Block> itr = p.getBlocks().iterator();
			index = 0;
			Block bt;
			while(itr.hasNext()) {
				bt = itr.next();
				if(bt instanceof Num_Block) {
					if (((Num_Block) bt).getNum() > ((Num_Block)b).getNum()) {
						break;
					} else if (((Num_Block) bt).getNum() == ((Num_Block)b).getNum() &&  !((Num_Block)b).color){
						break;
					}
				}
				index ++;
			}
			p.addBlock(index, b);
		}
		return index;
	}

	public List<Integer> getRandomTurns(int size) {
		// TODO Auto-generated method stub
		List<Integer> turns = new ArrayList <Integer> ();
		int i;
		for(i=1;i<=size;i++)
			turns.add(i);
		Collections.shuffle(turns);
		return turns;
	}
	
	public void setTurn(Player p, int turn) {
		int index=0;
		p.setTurn(turn);
		for(Player cp : turnList) {
			if (cp.getTurn() < turn)
				index ++;
			else 
				break;
		}
		turnList.add(index, p);
	}
	
	public int pointingPlayer(Player p, int index, boolean predictBlack, int predict) {
		// status
		// 0: wrong, 1: correct, 2: player_dead, 3: game_end 
		int result=0;
		if (checkPlayerBlock(p, index, predictBlack, predict)) {
			result = 1;
			if(deadCheck(p)) {
				result = 2;
				if(gameEndCheck())
					result = 3;
			}
		}
		return result;
				
	}
	
	public Player getWinner() {
		if(gameEndCheck())
			return turnList.get(0);
		return null;
		
	}
	
	public boolean gameEndCheck() {
		if(turnList.size() == 1) 
			return true;
		return false;
	}
	
	public boolean deadCheck(Player p) {
		for (Block b : p.getBlocks()) {
			if (!b.stand)
				return false;
		}
		p.setDead();
		turnList.remove(p);
		return true;
	}
	
	public boolean checkPlayerBlock(Player p, int index, boolean predictBlack, int predict) { // Á¶Ä¿ predict value = 12
		Block target = p.getBlocks().get(index);
		if (target instanceof Num_Block) {
			if(((Num_Block) target).getNum() == predict && (!target.color) == predictBlack) {
				target.stand = true;
				return true;
			}
			else
				return false;
		}
		else if (predict == 12) {
			if((target instanceof Joker_Block) && (!target.color) == predictBlack) {
				target.stand = true;
				return true;
			}
			else
				return false;
		}
		else
			return false;
	} 

	public Player getCurrentPlayer() {
		// TODO Auto-generated method stub
		return turnList.get(0);
	}

	public void nextTurn() {
		Player temp = turnList.remove(0);
		turnList.add(turnList.size(), temp);
	}
	
}