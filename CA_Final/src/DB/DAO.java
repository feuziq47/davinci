package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class DAO {
	
	//connection�� �����ϰ� ũ�Ƿ� �ѹ� ����� ��ü�� ��� ����ϴ°��� ����.
	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet rs;

	String jdbcDriver="com.mysql.cj.jdbc.Driver";
	String jdbcUrl="jdbc:mysql://localhost:3306/davinci?serverTimezone=Asia/Seoul";

	
	public DAO() {
		connectDB();
	}
	
	//singletone���� ���� ��.
	/*private DAO() {}
	private static DAO dao;
	public static DAO getInstance() {
		if(dao==null)
			dao=new DAO();
		return dao;
	}*/
	
	
	public boolean connectDB() {
		boolean result=false;
		try {
			Class.forName(jdbcDriver);
			conn=DriverManager.getConnection(jdbcUrl,"jhs","1234");
			result=true;
			System.out.println("���� ����");
		}catch(Exception e) {
			System.out.println("���� ����");
			e.printStackTrace();
		}
		return result;
	}
	
	public void showDB() {
		System.out.println("----------User List-----------");
		String sql="select * from UserList";
		if(connectDB()) {
			try {
				stmt=conn.prepareStatement(sql);
				synchronized(this) {
					rs=stmt.executeQuery();
					while(rs.next()) {
						System.out.println(rs.getString("id")+" | "+rs.getString("Password")+" | "+rs.getInt("win")+" | "+rs.getInt("lose"));
					}
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("DB�������");
			System.exit(0);
		}
	}
	
	public void closeDB() {
		try {
			if(rs!=null) rs.close();
			if(stmt!=null) stmt.close();
			if(conn!=null) conn.close();

		}catch(SQLException e) {
			System.out.println("DB close ����");
		}
	}
	public boolean signUp(String id, String pw) {			//��������
		boolean result=false;
		try {
			String sql="INSERT INTO UserList Values(?,?,?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(2, id);
			pstmt.setString(3, pw);
			pstmt.setInt(4, 0);
			pstmt.setInt(5, 0);
			synchronized(this) {
				int k=pstmt.executeUpdate();
				if(k>0) {
					result=true;
				}
			}
			pstmt.close();
			System.out.println("ȸ�����ԿϷ�");
			conn.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void signIn(String id, String pw) {				//�α���
		
		try {
			String sql="SELECT * FROM UserList WHERE ID=?";
			stmt=conn.prepareStatement(sql);
			stmt.setString(1,id);
			synchronized(this) {
				rs=stmt.executeQuery();
			}
			while(rs.next()==true) {
				if(rs.getString(2).equals(id)) {
					if(rs.getString(3).equals(pw)) {
						System.out.println("�α��� ����");
					}else {
						System.out.println("�н����尡 Ʋ�Ƚ��ϴ�.");
					}
				}else {
					System.out.println("���� id�Դϴ�.");
				}
			}
		}catch(Exception e) {e.printStackTrace();}	
	}
	public void checkState(String id) {					//������ȸ
		try {
			String sql="SELECT * FROM UserList WHERE ID = ?";
			stmt=conn.prepareStatement(sql);
			stmt.setString(1,id);
			rs=stmt.executeQuery();
			if(rs==null) {
				System.out.println("���� id�Դϴ�.");
			}
			while(rs.next()) {
				System.out.println("��: "+rs.getInt("win"));
				System.out.println("��: "+rs.getInt("lose"));
			}
			stmt.close();
			rs.close();
			conn.close();
		}catch(Exception e) {
			System.out.println("��ȸ ����");
		}
	}
	
	public void updateWin(String id) {				//�¸� �÷��̾� �¸��� �ø�
		try {
			String sql="SELECT * FROM UserList WHERE ID= ?";
			stmt=conn.prepareStatement(sql);
			stmt.setString(1,id);
			int temp=0;
			synchronized(this) {
				rs= stmt.executeQuery();
				while(rs.next()) {
					temp=rs.getInt("win");
				}
			}
			stmt.close();
			rs.close();
			String sql1="UPDATE UserList SET win=? WHERE id=?";
			stmt=conn.prepareStatement(sql1);
			stmt.setInt(1, temp+1);
			stmt.setString(2,id);
			synchronized(this) {
				int r=stmt.executeUpdate();
				if(r>0) {
					stmt.close();
				}
			}
		conn.close();	
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void updateLose(String id) {					//�й��÷��̾� �й�� �ø�
		try {
			String sql="SELECT * FROM UserList WHERE ID= ?";
			stmt=conn.prepareStatement(sql);
			stmt.setString(1,id);
			int temp=0;
			synchronized(this) {
				rs= stmt.executeQuery();
				while(rs.next()) {
					temp=rs.getInt("lose");
				}
			}
			stmt.close();
			rs.close();
			String sql1="UPDATE UserList SET lose=? WHERE id=?";
			stmt=conn.prepareStatement(sql1);
			stmt.setInt(1, temp+1);
			stmt.setString(2,id);
			synchronized(this) {
				int r=stmt.executeUpdate();
				if(r>0) {
					stmt.close();
				}
			}
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DAO dao=new DAO();
		DTO dto=new DTO();
		dao.signIn("rlatjsdn","rlatjsdn");
		//dto.setId("rlatjsdn");
		//dto.setPassword("rlatjsdn");
		//dao.signUp("rlatjsdn","rlatjsdn");
		//dao.updateWin("rlatjsdn");
		//dao.showDB();
		//dao.closeDB();
	}
}
