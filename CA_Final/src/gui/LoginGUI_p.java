package gui;

import javax.swing.*;

import davinchiSnC.Client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI_p extends JPanel{

	RoomListGUI_p roomlistGUI;
	JPanel logPanel;
	JLabel BGLabel; //Background Image Label
	ImageIcon BGImage; //Background Image
	ImageIcon[] bImg = new ImageIcon[2]; // button images
	JTextField IDField = new JTextField(); // id field
	JPasswordField pwField = new JPasswordField(); // password field
	JButton accountB; // making account button
	JButton loginB; // login button
	Client c;
	
	
	public LoginGUI_p(Client c){
		
		setSize(540,960);
		setVisible(true);
		setLayout(null);
		this.c = c;
		try { // load Image
			BGImage = new ImageIcon(ImageDir.bgImage2_dir);
			bImg[0] = new ImageIcon(ImageDir.makeAcountImage_dir);
			bImg[1] = new ImageIcon(ImageDir.loginButtonImage_dir);
		}catch(Exception e) {
			System.out.println("There is no Image File");
		}

		BGLabel = new JLabel(BGImage);
		accountB = new JButton(bImg[0]);
		loginB = new JButton(bImg[1]);
		
		BGLabel.setBounds(0,0,BGImage.getIconWidth(), BGImage.getIconHeight());
		IDField.setBounds(64, 568, 405, 41);
		pwField.setBounds(64, 632, 405, 41);
		accountB.setBounds(64, 705, bImg[0].getIconWidth(), bImg[0].getIconHeight());
		loginB.setBounds(266, 705, bImg[1].getIconWidth(), bImg[1].getIconHeight());
		
		IDField.setForeground(new Color(255, 255, 255));
		IDField.setHorizontalAlignment(SwingConstants.CENTER);
		IDField.setBackground(new Color(50, 42, 29));
		IDField.setColumns(10);

		pwField.setForeground(new Color(255, 255, 255));
		pwField.setHorizontalAlignment(SwingConstants.CENTER);
		pwField.setBackground(new Color(50,42,29));

		accountB.addActionListener(new ActionListener() { // 계정 생성 창 띄우기
			public void actionPerformed(ActionEvent e) {
				new SignUpGUI(c).setVisible(true);
			}
		});
		
	
		this.add(IDField);
		this.add(pwField);
		this.add(accountB);
		this.add(loginB);
		this.add(BGLabel);
		this.repaint();
	}
	
	public boolean login() {
		String id, pw;
		id = IDField.getText();
		pw = pwField.getText();
		pwField.setText("");
		if (!id.isEmpty() && !pw.isEmpty()) {
			return c.login(id, pw);
		}
		else
			return false;
	}
	
	
}
