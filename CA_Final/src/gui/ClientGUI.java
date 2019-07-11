package gui;

import javax.swing.*;

import davinchiSnC.Client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class ClientGUI extends JFrame{


	public RoomListGUI_p rlPanel; //방 목록 패널 클래스
	public LoginGUI_p lgPanel; //로그인 패널 클래스
	public GameGUI_p gPanel; //게임방 패널 클래스
	Client c;
	public int room_num;
	
	public ClientGUI(){
		
		this.setTitle("Davinci Code");
		this.setSize(540,960);
		this.setResizable(false);
		pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		room_num = 0;
		c = new Client(this);
		
		rlPanel = new RoomListGUI_p(c); //방 목록 패널 클래스
		lgPanel = new LoginGUI_p(c); //로그인 패널 클래스
		gPanel = new GameGUI_p(c); //게임방 패널 클래스
		
		lgPanel.loginB.addActionListener(new ActionListener() { // 로그인 -> 방 목록 패널
			public void actionPerformed(ActionEvent e) {
				if (lgPanel.login()) {
					setContentPane(rlPanel);
					getContentPane().revalidate();
				}
			}
		});
		lgPanel.pwField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                	if (lgPanel.login()) {
    					setContentPane(rlPanel);
    					getContentPane().revalidate();
    				}
                }                
            }
        });
		
		
		gPanel.backB.addActionListener(new ActionListener() { // 게임방 패널 Backpointer
            public void actionPerformed(ActionEvent e) {
                setContentPane(rlPanel);                
                try {
					c.exitRoom();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
        
        
		
		this.add(lgPanel); 
		this.add(rlPanel); 
		this.add(gPanel);  
		setContentPane(lgPanel);
		this.repaint();
	}

	 @Override
	public Dimension getPreferredSize(){
		 return new Dimension(540,960);
	}
	public static void main(String[] args) {
		new ClientGUI();
	}
	
	public void enterRoom(String msg) {
		gPanel.clearRoom();
		String uinfo [] = msg.split(" ");
		for (String uifs : uinfo) {
			if (uifs.isEmpty())
				continue;
			int index = Integer.parseInt(uifs.substring(0, 1));
			boolean ready = uifs.substring(1, 2).equals("T");
			String name = uifs.substring(2);
			gPanel.setUserNameSpace(index - 1, name);
			gPanel.changeReady(index - 1, ready);
			
		}
		setContentPane(gPanel);
	}
	
	public void changeRoom(int roomNum, int cur, int max) {
		if(cur == 0) {
			rlPanel.joinB[roomNum].setConnected(false);
			rlPanel.joinB[roomNum].setIcon(rlPanel.joinB[roomNum].bImg[0]);
			rlPanel.joinB[roomNum].setText("");
			return;
		}
		if(rlPanel.joinB[roomNum].connected) {
			rlPanel.joinB[roomNum].setText("인원 " + cur + "/" + max);	
		}else{
			rlPanel.joinB[roomNum].setConnected(true);
			rlPanel.joinB[roomNum].switchIcon();
			rlPanel.joinB[roomNum].setFont(new Font("KoreanCOMAR", Font.TRUETYPE_FONT, 22));
			rlPanel.joinB[roomNum].setForeground(Color.white);
			rlPanel.joinB[roomNum].setText( "인원 " + cur + "/" + max);
		}
	}
	
	
}


