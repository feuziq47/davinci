package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class DAO {
	
	//connection은 과부하가 크므로 한번 연결된 객체를 계속 사용하는것이 좋다.
	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet rs;

	String jdbcDriver="com.mysql.cj.jdbc.Driver";
	String jdbcUrl="jdbc:mysql://localhost:3306/davinci?serverTimezone=Asia/Seoul";

	
	public DAO() {
		connectDB();
	}
	
	//singletone으로 선언 시.
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
			System.out.println("연결 성공");
		}catch(Exception e) {
			System.out.println("연결 실패");
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
			System.out.println("DB연결실패");
			System.exit(0);
		}
	}
	
	public void closeDB() {
		try {
			if(rs!=null) rs.close();
			if(stmt!=null) stmt.close();
			if(conn!=null) conn.close();

		}catch(SQLException e) {
			System.out.println("DB close 실패");
		}
	}
	public boolean signUp(String id, String pw) {			//계정생성
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
			System.out.println("회원가입완료");
			conn.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void signIn(String id, String pw) {				//로그인
		
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
						System.out.println("로그인 성공");
					}else {
						System.out.println("패스워드가 틀렸습니다.");
					}
				}else {
					System.out.println("없는 id입니다.");
				}
			}
		}catch(Exception e) {e.printStackTrace();}	
	}
	public void checkState(String id) {					//전적조회
		try {
			String sql="SELECT * FROM UserList WHERE ID = ?";
			stmt=conn.prepareStatement(sql);
			stmt.setString(1,id);
			rs=stmt.executeQuery();
			if(rs==null) {
				System.out.println("없는 id입니다.");
			}
			while(rs.next()) {
				System.out.println("승: "+rs.getInt("win"));
				System.out.println("패: "+rs.getInt("lose"));
			}
			stmt.close();
			rs.close();
			conn.close();
		}catch(Exception e) {
			System.out.println("조회 실패");
		}
	}
	
	public void updateWin(String id) {				//승리 플레이어 승리수 올림
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
	
	public void updateLose(String id) {					//패배플레이어 패배수 올림
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
