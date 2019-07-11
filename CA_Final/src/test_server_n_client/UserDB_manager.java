package test_server_n_client;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class UserDB_manager {
	private static UserDB_manager inst=null;
	
	File file_name=new File("C:\\userDB.txt");
	ArrayList<String> idlist;
	ArrayList<String> pwlist;
	String msg;
	String [] db_one = new String [4];
	private UserDB_manager() throws FileNotFoundException {
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
		System.out.println("ID : " + id + ", PW : " + pw);
		if(!(idlist.contains(id))) {
			System.out.println("id를 확인하세요.");
			return false;
		}
		else {
			int index = idlist.indexOf(id);
			if(pwlist.get(index).equals(pw)) {
				System.out.println("pw를 확인하세요.");
				return false;
			}
		}
		return true;
	}
}