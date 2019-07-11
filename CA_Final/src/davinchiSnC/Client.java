package davinchiSnC;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.ButtonModel;

import gui.ClientGUI;
import tts.Clovatts;

public class Client {
	
	private static final int max_room_count = 6;
	
	Socket socket=null;
	DataInputStream in=null;
	DataOutputStream out=null;
	int [] RoomInfoC = new int [max_room_count];
	int [] RoomInfoM = new int [max_room_count];
	int [] RoomNum = new int [1];
	Thread th;
	ClientGUI view;
	
	public Client(ClientGUI view) {
		this.view = view;
		connect("localhost", 8000);
	}
	
	public void connect(String ip, int port) {
		try {
			socket = new Socket(ip, port);
			in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream()); 
			th = new Thread(new ClientReceiver(in, out, view, RoomInfoC, RoomInfoM, RoomNum));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean login(String id, String pw) {
		String msg, op;
		try {
			out.writeUTF(protocol.loginSignal + " " + id + " " + pw);
			msg = in.readUTF();
			if (msg.equals(protocol.loginFailure)) {
				return false;
			}
			else {
				op = msg.substring(0, 7);
				if (op.equals(protocol.loginSuccess)) {
					msg = msg.substring(7);
					String room_info = msg.substring(0);	
					for(int i=0;i < max_room_count*2;i+=2) {
						RoomInfoC[i/2] = Integer.parseInt(room_info.substring(i, i+1)); 
						RoomInfoM[i/2] = Integer.parseInt(room_info.substring(i+1, i+2));
						view.changeRoom(i/2, RoomInfoC[i/2], RoomInfoM[i/2]);
					}
					th.start();
					return true;
				}		
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	public boolean checkDuplicate(String id) {
		String msg;
		try {
			out.writeUTF(protocol.idCheckSignal + " " + id);
			msg = in.readUTF();
			System.out.println(msg);
			if (msg.equals(protocol.idDuplicated)) {
				return true;
			}
			else if(msg.equals(protocol.idNotDuplicated)){
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean signup(String id, String pw) {
		String msg;
		try {
			out.writeUTF(protocol.signupSingal + " " + id + " " + pw);
			msg = in.readUTF();
			if (msg.equals(protocol.idDuplicated)) {
				return false;
			}
			else if(msg.equals(protocol.signupSuccess)){
				return true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	public void chatSend(String msg) throws IOException {
		out.writeUTF(protocol.chatSignal + Integer.toString(RoomNum[0]) + msg);
	}

	public void roomEnter(int room_num) throws IOException {
		out.writeUTF(protocol.roomEnterSignal + Integer.toString(room_num));
	}

	public void exitRoom() throws IOException {
		out.writeUTF(protocol.roomExitSignal);
	}

	public void sendReady (int index) throws IOException {
		out.writeUTF(protocol.gameReadySignal + Integer.toString(index));
	}
	
	public void sendButton (int player_idx, int card_idx) throws IOException {
		if(!view.gPanel.selectB.isSelected() && !view.gPanel.selectW.isSelected())
			return;
		String num = (String)view.gPanel.numBox.getSelectedItem();
		String color ;
		if (view.gPanel.selectB.isSelected())
			color = "B";
		else
			color = "W";
		 out.writeUTF(protocol.gameBlockSendSignal + Integer.toString(RoomNum[0]) + Integer.toString(player_idx) + Integer.toString(card_idx) + color + num);
	}
	
		
}

class ClientReceiver implements Runnable{
	DataInputStream in;
	DataOutputStream out;
	ClientGUI view;
	
	int [] RoomInfoC;
	int [] RoomInfoM;
	int [] RoomNum;
	boolean inRoom;
	
	public ClientReceiver(DataInputStream in, DataOutputStream out, ClientGUI view, int [] RoomInfoC, int [] RoomInfoM, int [] RoomNum) {
		this.in = in;
		this.out=out;
		this.view = view;

		this.RoomInfoC = RoomInfoC;
		this.RoomInfoM = RoomInfoM;
		this.RoomNum = RoomNum;
		inRoom = false;
	}
	
	
	
	@Override
	public void run() {
		String msg, op;
		while(true) {
			try {
				msg=in.readUTF();
				System.out.println(msg);
				op = msg.substring(0, 3);
				msg = msg.substring(3);
				if (op.equals(protocol.chatSignal)) {
					view.gPanel.chatBoard.append(msg + "\n");
				}
				else if(op.equals(protocol.roomEnterSuccessSignal)) {
					// view -> enter room
					if(!inRoom) {
						inRoom = true;
						RoomNum[0] = Integer.parseInt(msg.substring(0, 1));
						msg = msg.substring(1);
						view.enterRoom(msg);
					}
				}
				else if(op.equals(protocol.roomExitSuccessSignal)) {
					inRoom = false;
					RoomNum[0] = 0;
				}
				else if(op.equals(protocol.roomChangeSignal)) {
					int index, cur, mmem;
					index = Integer.parseInt(msg.substring(0, 1));
					cur = Integer.parseInt(msg.substring(1, 2));
					mmem = Integer.parseInt(msg.substring(2, 3));
					RoomInfoC[index -1] = cur;
					RoomInfoM[index -1] = mmem;
					// cur n->0 // 0->n
					view.changeRoom(index -1, cur, mmem);
				}
				else if(op.equals(protocol.gameReadyTrue)) {
					int index;
					index = Integer.parseInt(msg.substring(0, 1));
					view.gPanel.changeReady(index - 1, true);
				}
				else if(op.equals(protocol.gameReadyFalse)) {
					int index;
					index = Integer.parseInt(msg.substring(0, 1));
					view.gPanel.changeReady(index - 1, false);
				}
				else if(op.equals(protocol.userEnterSignal)) {
					int index;
					String name;
					index = Integer.parseInt(msg.substring(0, 1));
					name = msg.substring(1); 
					view.gPanel.setUserImgSpace(index - 1, 0);
					view.gPanel.setUserNameSpace(index - 1, name);
				}
				else if(op.equals(protocol.userExitSignal)) {
					int index;
					index = Integer.parseInt(msg.substring(0, 1)); 
					view.gPanel.clearUserNameSpace(index - 1);
					view.gPanel.setUserImgSpace(index - 1, 2);
				}
				else if(op.equals(protocol.refereeAnounce)) {
					// ========================================================================================================================= add tts 
					view.gPanel.chatBoard.append(msg + "\n");
					new Thread(new Clovatts(msg)).start();
				}
				else if(op.equals(protocol.gameAddBlockSignal)) {
					int p_idx, index;
					p_idx = Integer.parseInt(msg.substring(0, 1)); 
					index = Integer.parseInt(msg.substring(1, 2)); 
					view.gPanel.addBlock(p_idx - 1, index);
				}
				else if(op.equals(protocol.gameBlockCorrect)) {
					String pidx, cidx, color, num;
					pidx = msg.substring(0, 1);
					cidx = msg.substring(1, 2);
					color = msg.substring(2, 3);
					num = msg.substring(3);
					view.gPanel.openBlock(Integer.parseInt(pidx), Integer.parseInt(cidx), color.equals("B"), num);
				}
				else if(op.equals(protocol.gamePlayerDead)) {
					int pidx;
					pidx = Integer.parseInt(msg.substring(0, 1));
					view.gPanel.playerDead(pidx);
				}
				else if(op.equals(protocol.gameStartSignal)) {
					view.gPanel.clearAll4Game();
				}
			}catch(Exception e) {
				break;
			}
		}
		
	}
}