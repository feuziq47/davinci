package gaming;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;


public class Components {
	
	public static ArrayList<Block> blocks ;
	private int whiteCount;
	
	public Components() {
		int i;
		whiteCount = 13;
		blocks = new ArrayList<Block>();
		for (i=0;i<12;i++) { 
			blocks.add(new Num_Block(i, true));
			blocks.add(new Num_Block(i, false));
		}
		blocks.add(new Joker_Block(true));
		blocks.add(new Joker_Block(false));
	}
	
	public Block popBlock() { 
		Random r = new Random();
		int index = r.nextInt(blocks.size());
		Block b = blocks.remove(index);
		if(b.color)
			whiteCount -= 1;
		return b;
	}
	
	public int getWhiteCount() { return whiteCount; }
	public int getBlackCount() { return blocks.size() - whiteCount; }
	
}