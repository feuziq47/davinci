package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import davinchiSnC.Client;

public class SignUpGUI extends JFrame{

	JLabel BGLabel; // Background label
	ImageIcon BGimg; // background img
	ImageIcon[] bImg = new ImageIcon[3]; //button images
	JTextField IDField; // id field
	JPasswordField PWField; // password field
	JPasswordField PWField_2; // password recheck field
	
	JButton overlapB; // ID overlap checking 
	JButton regiB; // register button
	JButton cancelB; // cancel button
	Client c;

	String path = GameGUI.class.getResource("").getPath();
	
	public SignUpGUI(Client c){
		this.setTitle("Davinci Code : Register");
		this.setSize(540, 960);
		this.setResizable(false);
		this.setVisible(true);
		this.c = c;
		getContentPane().setLayout(null);
		
		
		try {
			BGimg = new ImageIcon(ImageDir.bgImage4_dir);
			bImg[0] = new ImageIcon(ImageDir.overLapImage_dir);
			bImg[1] = new ImageIcon(ImageDir.makeACImage_dir);
			bImg[2] = new ImageIcon(ImageDir.cancelImage_dir);
		}catch(Exception e) {
			
		}
		
		
		IDField = new JTextField();
		PWField = new JPasswordField();
		PWField_2 = new JPasswordField();
		overlapB = new JButton(bImg[0]);
		regiB = new JButton(bImg[1]);
		cancelB = new JButton(bImg[2]);
		BGLabel = new JLabel(BGimg);

		IDField.setHorizontalAlignment(SwingConstants.CENTER);
		IDField.setColumns(10);
		IDField.setOpaque(false);
		IDField.setBorder(new LineBorder(Color.white, 3));
		IDField.setForeground(Color.white);
		
		PWField.setHorizontalAlignment(SwingConstants.CENTER);
		PWField.setColumns(10);
		PWField.setOpaque(false);
		PWField.setBorder(new LineBorder(Color.white, 3));
		PWField.setForeground(Color.white);

		PWField_2.setHorizontalAlignment(SwingConstants.CENTER);
		PWField_2.setColumns(10);
		PWField_2.setOpaque(false);
		PWField_2.setBorder(new LineBorder(Color.white, 3));
		PWField_2.setForeground(Color.white);
		
		
		IDField.setBounds(77, 207, 388, 30);
		PWField.setBounds(77, 416, 388, 30);
		PWField_2.setBounds(77, 538, 388, 30);
		overlapB.setBounds(193, 255, bImg[0].getIconWidth(), bImg[0].getIconHeight());
		regiB.setBounds(90, 630, bImg[1].getIconWidth(), bImg[1].getIconHeight());
		cancelB.setBounds(292, 630, bImg[2].getIconWidth(), bImg[2].getIconHeight());
		BGLabel.setBounds(0,0, BGimg.getIconWidth(), BGimg.getIconHeight());
		
		overlapB.addActionListener(new ActionListener() { // 중복 체크 버튼
			public void actionPerformed(ActionEvent e) {
				if(checkDuplicate())
					IDField.setText("");
			}
		});
		
		regiB.addActionListener(new ActionListener() { // 가입 버튼
			public void actionPerformed(ActionEvent e) {
				if(signup()) {
					CloseFrame();
				}
				else {
					IDField.setText("");
				}
			}
		});
		
		cancelB.addActionListener(new ActionListener() { // 취소버튼으로 창 닫기
			public void actionPerformed(ActionEvent e) {
				CloseFrame();
			}
		});
		
		getContentPane().add(IDField);
		getContentPane().add(PWField);
		getContentPane().add(PWField_2);
		getContentPane().add(overlapB);
		getContentPane().add(regiB);
		getContentPane().add(cancelB);
		getContentPane().add(BGLabel);
		
		
		this.repaint();
	}
	
	
	public void CloseFrame() {
		this.dispose();
	}
	
	public boolean checkDuplicate() {
		String id;
		id = IDField.getText();
		if (!id.isEmpty()) {
			return c.checkDuplicate(id);
		}
		else
			return true;
	}

	public boolean signup() {
		String id, pw, pw2;
		id = IDField.getText();
		pw = PWField.getText();
		pw2 = PWField_2.getText();
		if (!id.isEmpty() && !pw.isEmpty() && pw.equals(pw2)) {
			return c.signup(id, pw);
		}
		else
			return false;
	}

	
	public static void main(String args[]) {
		new SignUpGUI(null);
	}
	
	
	
	
}
