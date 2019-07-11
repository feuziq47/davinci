package davinchiSnC;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Server {

	private static final int max_room_count = 6;
	
    public static void main(String arg[])
    {
    	//접속한 Client와 통신하기 위한 Sockets
        Socket socket = null;   
        //Client 접속을 받기 위한 ServerSocket            
        ServerSocket server_socket=null;   
        ArrayList <Socket> sl = new ArrayList <Socket>();
        ArrayList <DataOutputStream> ol=new ArrayList <DataOutputStream>();
        RoomServer [] rsl = new RoomServer [max_room_count];
        Map<DataOutputStream, Boolean> isLogin = new HashMap<DataOutputStream, Boolean> ();
        
        for (int i=0;i<max_room_count;i++) {
        	rsl[i] = new RoomServer(i+1);
        }
                                     
        try { 
            server_socket = new ServerSocket(8000);
            //Server의 메인쓰레드는 게속해서 사용자의 접속을 받음
            while(true)
            {  
                socket = server_socket.accept();
                sl.add(socket);
                System.out.println("Access - IP: " + socket.getInetAddress() + ", port: " + socket.getPort());
                new Thread(new ServerReceiver(socket, ol, rsl, isLogin)).start();
            }
        }catch(Exception e) {
        	System.out.println("Main Server Error");
        	System.out.println(e.getStackTrace());
        }
    }
}
    

class ServerReceiver implements Runnable{
	Socket socket;
	DataInputStream in;
	DataOutputStream out;
	ArrayList<DataOutputStream> ol;
	UserDB_manager dbm;
	RoomServer [] rsl;
	Map<DataOutputStream, Boolean> isLogin;
	String id;
	int roomNum;
	int access=0;
	public ServerReceiver(Socket socket, ArrayList<DataOutputStream> ol, RoomServer [] rsl, Map<DataOutputStream, Boolean> isLogin) throws Exception{
		this.socket=socket;
		this.ol = ol;
		this.rsl = rsl;
		this.isLogin = isLogin;
		roomNum = 0;
		dbm = UserDB_manager.getInstance();
		in=new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());
		isLogin.put(out, false);
		ol.add(out);
	}
	@Override
	public void run() {
		try {
			while(true) {
				//login or sign up				
				String msg=in.readUTF();
				String[] cmds=new String[3];
				cmds = msg.split(" ");
				
				System.out.println(msg);
				if(cmds[0].equals(protocol.loginSignal)) {
					if(dbm.contain_check(cmds[1],cmds[2])) {
						String room_info = "";
						for (RoomServer r: rsl) {
							room_info = room_info.concat(Integer.toString(r.getCur_mem()) + Integer.toString(r.getMax_mem()));
						}
						out.writeUTF(protocol.loginSuccess + room_info);
						id = cmds[1];
						break;
					}
					else {
						out.writeUTF(protocol.loginFailure);
					}
					 
				}else if(cmds[0].equals(protocol.signupSingal)) {
					if(dbm.checkDuplicated(cmds[1]))
						out.writeUTF(protocol.idDuplicated);
					else {
						dbm.addUser(cmds[1], cmds[2]);
						out.writeUTF(protocol.signupSuccess);
					}
				}else if(cmds[0].equals(protocol.idCheckSignal)) {
					if(dbm.checkDuplicated(cmds[1]))
						out.writeUTF(protocol.idDuplicated);
					else
						out.writeUTF(protocol.idNotDuplicated);
				}
			}
			isLogin.replace(out, true);
			String msg, op, rn;
			while(true) {
				msg = in.readUTF();
				System.out.println(msg);
				op = msg.substring(0, 3);
				msg = msg.substring(3);
				if (op.equals(protocol.chatSignal)) {
					rn = msg.substring(0, 1);
					msg = msg.substring(1);
					sendChatRoom(Integer.parseInt(rn), msg);
				}
				else if (op.equals(protocol.roomEnterSignal)) {
					rn = msg.substring(0, 1);
					if(enterRoom(Integer.parseInt(rn)))
						roomNum = Integer.parseInt(rn);
				}
				else if (op.equals(protocol.roomExitSignal)) {
					exitRoom(roomNum);				
					roomNum = 0;
				}
				else if (op.equals(protocol.gameReadySignal)) {
					int index;
					index = Integer.parseInt(msg.substring(0, 1));
					readyGame(roomNum, index);
				}
				else if (op.equals(protocol.gameBlockSendSignal)) {
					rn = msg.substring(0, 1);
					String pidx, cidx, color, num;
					pidx = msg.substring(1, 2);
					cidx = msg.substring(2, 3);
					color = msg.substring(3, 4);
					num = msg.substring(4);
					if (num.equals("-"))
						num = "12";
					checkBlock(Integer.parseInt(rn), Integer.parseInt(pidx), Integer.parseInt(cidx), color.equals("B"),  Integer.parseInt(num));					
				}				
				
			}
		}catch(Exception e) {
			isLogin.remove(out);
			ol.remove(this.out);
			dbm.userDisconnect(id);
			if(roomNum != 0)
				exitRoom(roomNum);
		}
		
	}

	private void sendMsgAll(String msg) {
		for (DataOutputStream o : ol) {
			try {
				if(isLogin.get(o))
					o.writeUTF(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void sendChatRoom(int rn, String msg) {
		rsl[rn - 1].sendChatAll(out, msg);
	}

	private boolean enterRoom(int room_num) throws IOException {
		if(rsl[room_num - 1].enterRoom(id, out)) {
			String roomInfo = rsl[room_num - 1].getRoomInfo();
			out.writeUTF(protocol.roomEnterSuccessSignal + Integer.toString(room_num) + roomInfo);
			sendMsgAll(protocol.roomChangeSignal + Integer.toString(room_num) + rsl[room_num - 1].getCur_mem() + rsl[room_num - 1].getMax_mem());
			return true;
		}
		return false;
	}
	private void exitRoom(int room_num) {
		rsl[room_num - 1].exitRoom(out);
		try {
			out.writeUTF(protocol.roomExitSuccessSignal);
		} catch(IOException e) {}
		sendMsgAll(protocol.roomChangeSignal + Integer.toString(room_num) + rsl[room_num - 1].getCur_mem() + rsl[room_num - 1].getMax_mem());
	}
	
	private void readyGame(int room_num, int index) {
		if (!rsl[room_num - 1].checkOwnNum(out, index))
			return;
		rsl[room_num - 1].ready(out);
	}
	
	private void checkBlock(int room_num, int pidx, int cidx, boolean isBlack, int num)  {
		rsl[room_num - 1].checkBlock(out, pidx, cidx, isBlack, num);
	}
}
