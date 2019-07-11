package gaming;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		
		String tg = "123";
		System.out.println(tg.substring(0,1));
		
		Game g = new Game(3);
		
		Player p1 = new Player();
		Player p2 = new Player();
		Player p3 = new Player();

		g.joinPlayer(p1);
		g.joinPlayer(p2);
		g.joinPlayer(p3);
		
		p1.ready();
		p2.ready();
		p3.ready();
		if(g.checkReady()) {
			g.gameStart();
			int index, predict, gs;
			Player t;
			t = p1;
			while(true) {
				showBlocks(p1.getBlocks());
				System.out.println();
				showBlocks(p2.getBlocks());
				System.out.print(g.getCurrentPlayer().toString() + " >> ");
				index = scan.nextInt();
				predict = scan.nextInt();
				gs = g.part.ref.pointingPlayer(t, index, false, predict);
				if (gs==3)
					break;
				else if(gs==2) {
					System.out.println("kill!");
					index = scan.nextInt();
					if (index != 0)
						g.nextTurn();
					t = p2;
				}
				else if(gs==1) {
					index = scan.nextInt();
					if (index != 0)
						g.nextTurn();
				}else
					g.nextTurn();
			}
		}
	}
	
	public static void showBlocks(ArrayList <Block> bl) {
		Iterator<Block> itr = bl.iterator();
		while(itr.hasNext()) {
			System.out.println(" " + itr.next().toString() + " ");
		}
	}

}
