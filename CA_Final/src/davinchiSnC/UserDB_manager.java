package davinchiSnC;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import DB.DAO;
import DB.DTO;

public class UserDB_manager {
	private static UserDB_manager inst=null;
	
//	DAO dao=new DAO();
//	DTO dto=new DTO();
	
	ArrayList<String> onlineList;
	
	File file_name=new File("C:\\userDB.txt");
	ArrayList<String> idlist;
	ArrayList<String> pwlist;
	String msg;
	String [] db_one = new String [4];
	private UserDB_manager() throws FileNotFoundException {
		onlineList = new ArrayList<String>();
		Scanner scan=new Scanner(file_name);
		idlist = new ArrayList<String>();
		pwlist = new ArrayList<String>();
		while(scan.hasNextLine()) {
			msg = scan.nextLine();
			db_one = msg.split(" ");
			idlist.add(db_one[0]);
			pwlist.add(db_one[1]);
		}
		scan.close();
	}
	
	public static UserDB_manager getInstance() throws FileNotFoundException {
		if (inst == null)
			inst = new UserDB_manager();
		return inst;		
	}
	
	
	public boolean contain_check(String id,String pw) {
		if(!(idlist.contains(id))) {
			return false;
		}
		else {
			if (onlineList.contains(id))
				return false;
			int index = idlist.indexOf(id);
			if(!pwlist.get(index).equals(pw)) {
				return false;
			}
		}
		onlineList.add(id);
		return true;
	}

	public boolean checkDuplicated (String id) {
		return idlist.contains(id);
	}
	public boolean checkConnected (String id) {
		return idlist.contains(id);
	}
	
	public void addUser(String id, String pw) {
		idlist.add(id);
		pwlist.add(pw);
		FileWriter writer;
		try {
			writer = new FileWriter(file_name, true);
			writer.write(id + " " + pw + " 0 0\n");
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addNewConnector(String id) {
		onlineList.add(id);
	}
	public void userDisconnect(String id) {
		onlineList.remove(id);
	}
	
	
}