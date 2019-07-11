package davinchiSnC;

import java.awt.Color;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gaming.Block;
import gaming.Game;
import gaming.Num_Block;
import gaming.Player;

public class RoomServer {
	
	private static final int static_max_member = 4;
	private int cur_mem;
	private int max_mem;
	private int room_num;
	private boolean isGaming;
	private Game g;
	
	private ArrayList<DataOutputStream> connector;
	private Map<DataOutputStream, User> users;
	private ArrayList<Integer> numList;
	
	
	public RoomServer(int room_num) {
		this.room_num = room_num;
		isGaming = false;
		setCur_mem(0);
		setMax_mem(static_max_member);
		connector = new ArrayList<DataOutputStream>();
		g = new Game(static_max_member);
		users = new HashMap<DataOutputStream, User>();
		numList = new ArrayList<Integer>();
		for(int i=1;i<=static_max_member;i++)
			numList.add(i);
		
	}
	public int getCur_mem() {
		return cur_mem;
	}
	public void setCur_mem(int cur_mem) {
		this.cur_mem = cur_mem;
	}
	public int getMax_mem() {
		return max_mem;
	}
	public void setMax_mem(int max_mem) {
		this.max_mem = max_mem;
	}
	
	public boolean enterRoom(String id, DataOutputStream out) {
		int num = popPlayerIndex();
		if(isGaming || cur_mem >= max_mem || num == 0) {
			return false;
		}
		sendMsgAll(protocol.userEnterSignal + Integer.toString(num) + id);
		connector.add(out);
		Player p = new Player();
		users.put(out, new User(id, num, p));
		g.joinPlayer(p);
		cur_mem += 1;
		return true;
	}
	
	public String getRoomInfo() {
		String result = "";
		for(User u : users.values()) {
			result = result.concat(Integer.toString(u.num));
			if(u.player.getReady())
				result= result.concat("T");
			else
				result =result.concat("F");
			result=result.concat(u.id);
			result=result.concat(" ");
		}
		return result;
	}
	
	public void exitRoom(DataOutputStream out) {
		if(isGaming)
			gameEnd();
		connector.remove(out);
		User user = users.remove(out);
		numList.add(user.num);
		g.exitPlayer(user.player);
		cur_mem --;
		sendMsgAll(protocol.userExitSignal + Integer.toString(user.num));
	}
	
	private int popPlayerIndex () {
		if(numList.isEmpty())
			return 0;
		return numList.remove(0);
	}
	
	public void sendMsgAll(String msg) {
		for(DataOutputStream out : connector) {
			try {
				out.writeUTF(msg);
			} catch (IOException e) {
			}
		}
	}

	public void sendChatAll(DataOutputStream src, String msg) {
		User user = users.get(src);
		for(DataOutputStream out : connector) {
			try {
				out.writeUTF(protocol.chatSignal + "[" + user.num + "] " + user.id + " : " + msg);
			} catch (IOException e) {
			}
		}
	}

	public boolean checkOwnNum(DataOutputStream src, int index) {
		User user = users.get(src);
		return user.num == index;
	}

	public void resetReady() {
		User user ;
		for(DataOutputStream out : connector) {
			user = users.get(out);
			user.player.resetReady();
			sendMsgAll(protocol.gameReadyFalse + Integer.toString(user.num));
		}
	}
	
	public boolean ready(DataOutputStream src) {
		if(isGaming)
			return false;
		User user = users.get(src);
		user.player.ready();
		if(user.player.getReady()) {
			sendMsgAll(protocol.gameReadyTrue + Integer.toString(user.num));
			if(cur_mem > 1 && allReady()) {
				gameStart();
			}
		} else {
			sendMsgAll(protocol.gameReadyFalse + Integer.toString(user.num));
		}
		return user.player.getReady();
	}
	
	public boolean allReady() {
		User user;
		boolean result = true;
		for(DataOutputStream out : connector) {
			user = users.get(out);
			if (!user.player.getReady()) {
				result = false;
				break;
			}
		}
		return result;
	}
	
	public void gameStart() {
		isGaming = true;
//		sendMsgAll(protocol.refereeAnounce + "======== 게임 시작 ========");
		sendMsgAll(protocol.gameStartSignal);
		g.gameStart();
		resetReady();
		
		for(int i=0;i<4;i++) {
			for(DataOutputStream out : connector) {
				giveBlock(out);				
			}
		}		
		User user = findUser(g.getCurrentPlayer());
		sendMsgAll(protocol.refereeAnounce + "======== " + user.id + " 턴 ========");
	}
	
	private void giveBlock(DataOutputStream out) {
		User user = users.get(out);
		int index = g.giveBlock(user.player);
		Block b = user.player.getBlock(index);
		sendMsgAll(protocol.gameAddBlockSignal + Integer.toString(user.num) + Integer.toString(index));
		String color, num;
		if (b.color)
			color = "W";
		else
			color = "B";
		if (b instanceof Num_Block)
			num = Integer.toString(((Num_Block) b).getNum());
		else
			num = "12";
		try {
			out.writeUTF(protocol.gameBlockCorrect + Integer.toString(user.num - 1) + Integer.toString(index) + color + num);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void gameEnd() {
		isGaming= false;
		sendMsgAll(protocol.refereeAnounce + "======== [무승부] 게임이 종료 되었습니다. ========");
	}
	
	public void gameEnd(User user) {
		isGaming= false;
		sendMsgAll(protocol.refereeAnounce + "======== " + user.id + " 님이 승리했습니다. ========");
	}
	class User{
		public String id;
		public int num;
		public Player player;
		public User(String id, int num, Player player) {
			this.id = id;
			this.num = num;
			this.player = player;
		}
	}

	public void checkBlock(DataOutputStream out, int pidx, int cidx, boolean isBlack, int num) {
		User user = users.get(out);
		if (!isGaming)
			return;
		if (!user.player.equals(g.getCurrentPlayer()))
			return;
		Player ptgt = g.getPlayer(pidx);
		if (user.player.equals(ptgt)) {
			// pass to next turn
			nextTurn();
			return;
		}
		int result = g.checkBlock(pidx, cidx,isBlack, num); // 0: wrong, 1: correct, 2: player_dead, 3: game_end
//		System.out.println("Result : "+ Integer.toString(result));
		if(result == 0)
			nextTurn();
		else {
			String color;
			if(isBlack)
				color = "B";
			else
				color = "W";
			sendMsgAll(protocol.gameBlockCorrect + Integer.toString(pidx) + Integer.toString(cidx) + color + Integer.toString(num));
			if(result >= 2) {
				sendMsgAll(protocol.gamePlayerDead + Integer.toString(pidx));
				if (result == 3) {
					sendMsgAll(protocol.gameEndSignal);					
					gameEnd(user);
				}
			}
			
		}
	}
	
	public void nextTurn() {
		g.nextTurn();
		User user = findUser(g.getCurrentPlayer());
		sendMsgAll(protocol.refereeAnounce + "======== " + user.id + " 턴 ========");
		giveBlock(findOut(user));
	}
	
	public User findUser(Player p) {
		for(DataOutputStream out : connector) {
			User user = users.get(out);
			if (user.player.equals(p))
				return user;
		}
		return null;
	}

	public DataOutputStream findOut(User tgt) {
		for(DataOutputStream out : connector) {
			User user = users.get(out);
			if (user.equals(tgt))
				return out;
		}
		return null;
	}
}
