package test_server_n_client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	
	public static void main(String[] args) {
		Socket socket=null;
		System.out.println("Client");
		DataInputStream in=null;
		BufferedReader sysin=null; 
		DataOutputStream out=null;
		Thread th;
		try {
			socket = new Socket("localhost",8000);    //서버로 접속
			System.out.println("connected!");
            
            in = new DataInputStream(socket.getInputStream());            
            sysin = new BufferedReader(new InputStreamReader(System.in)); 
            out = new DataOutputStream(socket.getOutputStream()); 
			th = new Thread(new ClientSend(in, out));
		}catch (Exception e) { return; } 

		String msg, op;

		while(true) {
			try {
				System.out.print("ID >> ");
				String id = sysin.readLine();
				System.out.print("PW >> ");
				String pw = sysin.readLine();
				if (id.contains(" ") || pw.contains(" ")) {
					System.out.println("Wrong container!");
					continue;
				}
				out.writeUTF("[LOGIN] " + id + " " + pw); // or [SIGUP]
				msg = in.readUTF();
				System.out.println(msg);
				if (msg.equals("[LGSUC]"))
					break;
			}
			catch(Exception e) {}
		}
		

		th.start();
		
		try {
			while(true)
            {
				msg = in.readUTF();
				op = msg.substring(0, 3);
				msg = msg.substring(3);
				if (op.equals("CHT"))
					System.out.println(msg);
            }
        }catch(Exception e) {} 
		}

}

class ClientSend implements Runnable{
	DataInputStream in;
	DataOutputStream out;
	BufferedReader sysin=new BufferedReader(new InputStreamReader(System.in));
	
	public ClientSend(DataInputStream in, DataOutputStream out) {
		this.in = in;
		this.out=out;
	}

	@Override
	public void run() {
		String msg;
		while(true) {
			try {
				msg=sysin.readLine();
				out.writeUTF("CHT" + msg);
			}catch(Exception e) {}
		}
		
	}
}
