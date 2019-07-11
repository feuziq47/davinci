package gui;

import javax.swing.*;

import davinchiSnC.Client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class RoomListGUI_p extends JPanel {

	JPanel roomPanel = new JPanel();
	JLabel BGLabel; // Background label
	ImageIcon BGimg; // background img
	public joinButton[] joinB = new joinButton[6];
	Client c;
	
	public RoomListGUI_p(Client c){
		this.setLayout(null);
		this.setSize(540,960);
		this.c = c;
		
		try {
			BGimg = new ImageIcon(ImageDir.bgImage3_dir); //배경이미지
		}catch(Exception e) {
			System.out.println("No Image File");
			e.printStackTrace();
		}
		

		BGLabel = new JLabel(BGimg);

		BGLabel.setBounds(0,0, BGimg.getIconWidth(), BGimg.getIconHeight());

		roomPanel.setBounds(43, 107, 454, 790);
		roomPanel.setOpaque(false);
		roomPanel.setLayout(new BoxLayout(roomPanel, BoxLayout.Y_AXIS));
		roomPanel.setBorder(null);
		
		for(int i=0; i<joinB.length; i++) {
			joinB[i] = new joinButton(i + 1);
			roomPanel.add(joinB[i]);
		}

		this.add(roomPanel);
		this.add(BGLabel);
		
		for(int i=0; i< joinB.length; i++) {
            joinB[i].addActionListener(new ActionListener() { // 입장하기 -> 게임방 패널
                public void actionPerformed(ActionEvent e) {
                    RoomListGUI_p.joinButton jB = ((RoomListGUI_p.joinButton)(e.getSource()));
                    try {
						c.roomEnter(jB.value);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

                }
            });
        }
	}

	@Override
	public Dimension getPreferredSize(){
		return new Dimension(540,960);
	}



	public static void main(String[] args) {
		JFrame exam = new JFrame("example");
		RoomListGUI_p rlp = new RoomListGUI_p(null);


		exam.setLayout(new BorderLayout());
		exam.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		exam.add(rlp, BorderLayout.CENTER);
		exam.pack();
		exam.setVisible(true);
		exam.setMinimumSize(exam.getPreferredSize());
		exam.setResizable(false);
		exam.setLocationRelativeTo(null);

	}
	
	

	class joinButton extends JButton{
		boolean connected = false;
		ImageIcon[] bImg = new ImageIcon[2];
		int value;

		String roomname = null;
		int pNum = 0;
		int maxpNum = 4;

		joinButton(int value){
			this.value = value;
			try{
				bImg[0] = new ImageIcon(ImageDir.emptyRoomImage_dir);
				bImg[1] = new ImageIcon(ImageDir.joinRoomImage_dir);
			}catch(Exception e){
				System.out.println("There is no image");
				e.printStackTrace();
			}

			if(connected == false) {
				setIcon(bImg[0]);
				setHorizontalTextPosition(SwingConstants.HORIZONTAL);
				setForeground(Color.white);
				setOpaque(false);
				setContentAreaFilled(false);
				setBorderPainted(false);
			}else{
				setIcon(bImg[1]);
				setFont(new Font("KoreanCOMAR", Font.TRUETYPE_FONT, 22));
				setForeground(Color.white);
				setText(roomname + "인원 " + pNum + "/" +maxpNum);
				setHorizontalTextPosition(SwingConstants.HORIZONTAL);
				setOpaque(false);
				setContentAreaFilled(false);
				setBorderPainted(false);
			}

		}


		public void setConnected(boolean connected){
			this.connected = connected;
		}

		public void switchIcon(){
			setIcon(bImg[1]);
		}


	}

}

