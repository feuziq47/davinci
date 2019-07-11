package gui;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainGUI_test extends JFrame{


	JPanel logPanel = new JPanel();
	JPanel listPanel = new JPanel();
	JLabel BGLabel[] = new JLabel[2]; //Background Image Label
	ImageIcon[] BGImage = new ImageIcon[2]; //Background Image
	ImageIcon[] bImg = new ImageIcon[6]; // button images
	JTextField IDField = new JTextField(); // id field
	JPasswordField pwField = new JPasswordField(); // password field
	JButton accountB; // making account button
	JButton loginB; // login button
	JButton makeRB; // 'make room' button
	JButton[] intoRB = new JButton[5]; // 'join room' button
//	JButton backB; // back button
	JButton joinB;

	String path = GameGUI.class.getResource("").getPath();
	
	public mainGUI_test(){
		
		this.setTitle("Davinci Code");
		this.setSize(540,960);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		
		try { // load Image
			BGImage[0] = new ImageIcon(ImageDir.bgImage2_dir); // 로그인 이미지
			BGImage[1] = new ImageIcon(ImageDir.bgImage3_dir); // 방 목록 이미지
			bImg[0] = new ImageIcon(ImageDir.makeAcountImage_dir); // 계정 생성 이미지
			bImg[1] = new ImageIcon(ImageDir.loginButtonImage_dir); // 로그인버튼 이미지
			bImg[2] = new ImageIcon(ImageDir.makeRoomImage_dir); // 방 만들기 이미지
			bImg[3] = new ImageIcon(ImageDir.backPointerImage_dir); //뒤로가기 버튼 이미지
			bImg[4] = new ImageIcon(ImageDir.emptyRoomImage_dir); // 빈방 이미지
			bImg[5] = new ImageIcon(ImageDir.joinRoomImage_dir); // 입장 이미지
		}catch(Exception e) {
			System.out.println("There is no Image File");
		}

		
		BGLabel[0] = new JLabel(BGImage[0]);
		BGLabel[1] = new JLabel(BGImage[1]);
		accountB = new JButton(bImg[0]);
		loginB = new JButton(bImg[1]);
		makeRB = new JButton(bImg[2]);
//		backB = new JButton(bImg[3]);
		
		for(int i=0; i<intoRB.length; i++) {
			intoRB[i] = new JButton(bImg[4]);
			}
		
		joinB = new JButton(bImg[5]);
		joinB.setFont(new Font("KoreanCOMAR", Font.TRUETYPE_FONT, 22));
		JPanel roomPanel = new JPanel();
		
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
		
		logPanel.setLayout(null);
		listPanel.setLayout(null);
		
		logPanel.setBounds(0,0,540,960);
		listPanel.setBounds(0,0,540,960);
		
		BGLabel[0].setBounds(0,0,BGImage[0].getIconWidth(), BGImage[0].getIconHeight());
		IDField.setBounds(64, 568, 405, 41);
		pwField.setBounds(64, 632, 405, 41);
		accountB.setBounds(64, 705, bImg[0].getIconWidth(), bImg[0].getIconHeight());
		loginB.setBounds(266, 705, bImg[1].getIconWidth(), bImg[1].getIconHeight());
		
		
		BGLabel[1].setBounds(0,0,BGImage[1].getIconWidth(), BGImage[1].getIconHeight());
		makeRB.setBounds(190, 810, bImg[2].getIconWidth(), bImg[2].getIconHeight());
//		backB.setBounds(35, 27, bImg[3].getIconWidth(), bImg[3].getIconHeight());
		roomPanel.setBounds(43, 127, 454, 658);
		
		IDField.setForeground(new Color(255, 255, 255));
		IDField.setHorizontalAlignment(SwingConstants.CENTER);
		IDField.setBackground(new Color(50, 42, 29));
		IDField.setColumns(10);

		pwField.setForeground(new Color(255, 255, 255));
		pwField.setHorizontalAlignment(SwingConstants.CENTER);
		pwField.setBackground(new Color(50,42,29));
		
		roomPanel.setBorder(null);
		roomPanel.setOpaque(false);
		roomPanel.setLayout(new BoxLayout(roomPanel, BoxLayout.Y_AXIS));

		accountB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SignUpGUI(null).setVisible(true);
			}
		});
		
		loginB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setContentPane(listPanel);
				getContentPane().revalidate();
				}
		});
		
//		backB.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				setContentPane(logPanel);
//				getContentPane().revalidate();
//			}
//			
//		});
		
		
		logPanel.add(IDField);
		logPanel.add(pwField);
		logPanel.add(accountB);
		logPanel.add(loginB);
		logPanel.add(BGLabel[0]);
		
		listPanel.add(makeRB);
//		listPanel.add(backB);
		listPanel.add(roomPanel);
		listPanel.add(BGLabel[1]);
		
		this.add(logPanel);
		this.add(listPanel);
		setContentPane(logPanel);
		this.repaint();
	}
	
	public static void main(String[] args) {
		new mainGUI_test();
	}
}


