package test_server_n_client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

	
    public static void main(String arg[])
    {
    	//접속한 Client와 통신하기 위한 Sockets
        Socket socket = null;   
        //Client 접속을 받기 위한 ServerSocket            
        ServerSocket server_socket=null;   
        ArrayList <Socket> sl = new ArrayList <Socket>();
        ArrayList <DataOutputStream> ol=new ArrayList <DataOutputStream>();
                                     
        try { 
            server_socket = new ServerSocket(8000);
            //Server의 메인쓰레드는 게속해서 사용자의 접속을 받음
            while(true)
            {  
                socket = server_socket.accept();
                sl.add(socket);
                System.out.println("Access - IP: " + socket.getInetAddress() + ", port: " + socket.getPort());
                new Thread(new ServerReceiver(socket, sl, ol)).start();
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
	ArrayList<Socket> sl;
	ArrayList<DataOutputStream> ol;
	UserDB_manager dbm;
	int access=0;
	public ServerReceiver(Socket socket, ArrayList<Socket> sl, ArrayList<DataOutputStream> ol) throws Exception{
		this.socket=socket;
		this.sl = sl;
		this.ol = ol;
		dbm = UserDB_manager.getInstance();
		in=new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());
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
				if(cmds[0].equals("[LOGIN]")) {
					if(dbm.contain_check(cmds[1],cmds[2])) {
						out.writeUTF("[LGSUC]");
						break;
					}
					else {
						out.writeUTF("[LGFAL]");
					}
					 
				}else if(cmds[0].equals("[SGNUP]")) {
					System.out.println("id와 pw를 입력하세요");
					// todo implement
				}
			}
			String msg;
			String op;
			while(true) {
				msg = in.readUTF();
				op = msg.substring(0, 3);
				msg = msg.substring(3);
				if (op.equals("CHT"))
					sendMsgAll(op + msg);
//				if (msg.charAt(0).eqauls('C')) {
//					// chating
//				}else if(msg.charAt(0).equals('G')) {
//					
//				}
//				else if (msg.charAt(0).eqauls()) {
//					// 시그널
//					
//				}
				
				
			}
		}catch(Exception e) {
			sl.remove(this.socket);
			ol.remove(this.out);
		}
		
	}
	
	public void sendMsgAll(String msg) {
		for (DataOutputStream o : ol) {
			try {
				o.writeUTF(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
