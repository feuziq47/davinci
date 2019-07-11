package gui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.Font;

public class RoomListGUI extends JFrame {

	JLabel BGLabel; // Background label
	
	ImageIcon BGimg; // background img
	ImageIcon[] bImg = new ImageIcon[5];
	
	JButton makeRB; // 'make room' button
	JButton[] intoRB = new JButton[5]; // 'join room' button
	JButton backB; // back button
	JButton joinB;


	
	
	public RoomListGUI(){
		//this.setTitle("Davinci Code : RoomList");
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setSize(540,960);
		this.setVisible(true);
		getContentPane().setLayout(null);
		

		
		try {
			BGimg = new ImageIcon("D:\\ImageFiles\\roomlist540x960.jpg");
			bImg[0] = new ImageIcon("D:\\ImageFiles\\makeroomB.png");
			bImg[1] = new ImageIcon("D:\\ImageFiles\\m_joinB.png");
			bImg[2] = new ImageIcon("D:\\ImageFiles\\backpointer.png");
			bImg[3] = new ImageIcon("D:\\ImageFiles\\emptyRoomB_2.png");
			bImg[4] = new ImageIcon("D:\\ImageFiles\\joinRoomB_2.png");
		}catch(Exception e) {
		
		}
		
		BGLabel = new JLabel(BGimg);
		makeRB = new JButton(bImg[0]);
		backB = new JButton(bImg[2]);
		joinB = new JButton(bImg[4]);
		joinB.setFont(new Font("KoreanCOMAR", Font.TRUETYPE_FONT, 22));
		JPanel roomPanel = new JPanel();
		roomPanel.setBorder(null);
		
		for(int i=0; i<intoRB.length; i++) {
			intoRB[i] = new JButton(bImg[3]);
			}
		
		makeRB.setBounds(190, 810, bImg[0].getIconWidth(), bImg[0].getIconHeight());
		backB.setBounds(35, 27, bImg[2].getIconWidth(), bImg[2].getIconHeight());
		BGLabel.setBounds(0,0, BGimg.getIconWidth(), BGimg.getIconHeight());
		
		/*
		for(int i=0; i<intoRB.length; i++) {
		intoRB[i].setBounds(420, 210 + 200*i, bImg[1].getIconWidth(), bImg[1].getIconHeight());
		}
		*/
		
		


		roomPanel.setBounds(43, 127, 454, 658);
		roomPanel.setOpaque(false);
		roomPanel.setLayout(new BoxLayout(roomPanel, BoxLayout.Y_AXIS));
		
		
		for(int i=0; i<intoRB.length-1; i++) {
			intoRB[i].setOpaque(false);
			intoRB[i].setContentAreaFilled(false);
			roomPanel.add(intoRB[i]);
		}
		
		joinB.setOpaque(false);
		joinB.setContentAreaFilled(false);
		joinB.setText("게임하자  [인원 3/4] ");
		joinB.setHorizontalTextPosition(SwingConstants.HORIZONTAL);
		roomPanel.add(joinB);
		
		this.add(makeRB);
		this.add(backB);
		this.add(roomPanel);
		this.add(BGLabel);
		
		
		
		this.repaint();
		this.revalidate();
		}
	
	
	
	public static void main(String args[]) {
		new RoomListGUI();
	}
}
