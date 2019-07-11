package gaming;
import java.util.ArrayList;
import java.util.Scanner;

public class Player implements LocationSetter, Pointable{
	
	private int turn;
	private boolean isReady;
	private boolean isAlive;
	private ArrayList <Block> blocks;
	private State st;
	
	public Player() {
		init();
	}
	
	public void init() {
		isReady = false;
		blocks = new ArrayList <Block>();
		isAlive = true;
	}
	

	public boolean ready() {
		isReady = !isReady;
		return isReady;
	}
	public void resetReady() {
		isReady = false;
	}
	
	public void dropAllBlocks() {
		blocks = new ArrayList <Block>();
	}
	
	public void setTurn(int turn) { this.turn = turn; }

	public boolean getReady() {
		return isReady;
	}
	public int getTurn() {		return turn;	}
	
	public ArrayList<Block> getBlocks() {
		return blocks;
	}

	public void addBlock (Block b) {
		blocks.add(b);
	}
	
	public void setDead() { isAlive = false;	}
	
	public void addBlock (int index, Block b) {
		blocks.add(index, b);
	}
	
	@Override
	public void pointing() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set_Location() {
		// TODO Auto-generated method stub
		
	}

	public Block getBlock(int index) {
		return blocks.get(index);
	}
}