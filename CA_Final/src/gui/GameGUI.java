package gui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextArea;

public class GameGUI extends JFrame{

	JLabel BGLabel; //Background Image Labels
	JLabel[] uImgLabel = new JLabel[4]; // user icon labels
	JLabel[] BotbLabel = new JLabel[2]; // bottom blocks
	JLabel[] cbLabel = new JLabel[4]; //user block labels
	
	ImageIcon BGImage; //Background Image
	ImageIcon[] userImg = new ImageIcon[4]; //user ICON
	ImageIcon[] BotbImg = new ImageIcon[2]; //bottom block ICON
	ImageIcon cboard; // user block Image
	
	JTextField whiteBnum;
	JTextField blackBnum;
	JTextField xField1 = new JTextField();
	JTextField xField2 = new JTextField();
	JTextField chatField = new JTextField(); // chatting input field
	
	JTextArea textA = new JTextArea(); //"남은 블럭" 글자 표시
	JTextArea chatBoard = new JTextArea(); // chatting board


	public GameGUI(){
	
	this.setTitle("Davinci Code : Game");
	this.setSize(540, 960);
	this.setResizable(false);
	this.setVisible(true);
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	getContentPane().setLayout(null);
	
	try { // load Image
		BGImage = new ImageIcon(ImageDir.bgImage1_dir);
		userImg[0] = new ImageIcon(ImageDir.userImage1_dir);
		userImg[1] = new ImageIcon(ImageDir.userImage2_dir);
		userImg[2] = new ImageIcon(ImageDir.userImage3_dir);
		userImg[3] = new ImageIcon(ImageDir.userImage4_dir);
		BotbImg[0] = new ImageIcon(ImageDir.whiteImage_dir);
		BotbImg[1] = new ImageIcon(ImageDir.blackImage_dir);
		cboard = new ImageIcon(ImageDir.cardboardImage_dir);
	}catch(Exception e) {
		System.out.println("No Image File");
	}
	
	
	uImgLabel[0] = new JLabel(userImg[0]);
	uImgLabel[1] = new JLabel(userImg[1]);
	uImgLabel[2] = new JLabel(userImg[2]);
	uImgLabel[3] = new JLabel(userImg[3]);
	BotbLabel[0] = new JLabel(BotbImg[0]);
	BotbLabel[1] = new JLabel(BotbImg[1]);
	cbLabel[0] = new JLabel(cboard);
	cbLabel[1] = new JLabel(cboard);
	cbLabel[2] = new JLabel(cboard);
	cbLabel[3] = new JLabel(cboard);
	BGLabel = new JLabel(BGImage);
	whiteBnum = new JTextField("N");
	blackBnum = new JTextField("N");

	
	
	uImgLabel[0].setBounds(12, 108, userImg[0].getIconWidth(), userImg[0].getIconHeight()); // user icon label
	uImgLabel[1].setBounds(12, 262, userImg[0].getIconWidth(), userImg[0].getIconHeight());
	uImgLabel[2].setBounds(12, 416, userImg[0].getIconWidth(), userImg[0].getIconHeight());
	uImgLabel[3].setBounds(12, 570, userImg[0].getIconWidth(), userImg[0].getIconHeight());
	BotbLabel[0].setBounds(420, 770, BotbImg[0].getIconWidth(), BotbImg[0].getIconHeight()); // bottom block label
	BotbLabel[1].setBounds(420, 840, BotbImg[1].getIconWidth(), BotbImg[1].getIconHeight());
	cbLabel[0].setBounds(157, 115, cboard.getIconWidth(), cboard.getIconHeight()); // user block board
	cbLabel[1].setBounds(157, 269, cboard.getIconWidth(), cboard.getIconHeight());
	cbLabel[2].setBounds(157, 423, cboard.getIconWidth(), cboard.getIconHeight());
	cbLabel[3].setBounds(157, 577, cboard.getIconWidth(), cboard.getIconHeight());
	xField1.setBounds(475, 785, 32, 32);
	xField2.setBounds(475, 855, 32, 32);
	whiteBnum.setBounds(498, 782, 32, 32);
	blackBnum.setBounds(498, 852, 32, 32);
	textA.setBounds(384, 810, 37, 54);
	chatBoard.setBounds(10,750, 370, 140);
	chatField.setBounds(10,892, 370, 20);

	
	BGLabel.setBounds(0,0,BGImage.getIconWidth(), BGImage.getIconHeight()); // background

	
	for(int i=0; i<4; i++) {
		getContentPane().add(uImgLabel[i]);
		getContentPane().add(cbLabel[i]);
	}
	getContentPane().add(BotbLabel[0]);
	getContentPane().add(BotbLabel[1]);
	getContentPane().add(whiteBnum);
	getContentPane().add(blackBnum);
	getContentPane().add(xField1);
	getContentPane().add(xField2);
	getContentPane().add(textA);
	getContentPane().add(chatBoard);
	getContentPane().add(chatField);
	getContentPane().add(BGLabel);
	
	whiteBnum.setForeground(Color.WHITE);
	whiteBnum.setHorizontalAlignment(SwingConstants.CENTER);
	whiteBnum.setColumns(10);
	whiteBnum.setOpaque(false);
	
	blackBnum.setForeground(Color.WHITE);
	blackBnum.setHorizontalAlignment(SwingConstants.CENTER);
	blackBnum.setColumns(10);
	blackBnum.setOpaque(false);

	xField1.setFont(new Font("굴림", Font.PLAIN, 20));
	xField1.setForeground(Color.WHITE);
	xField1.setHorizontalAlignment(SwingConstants.CENTER);
	xField1.setText("X");
	xField1.setOpaque(false);
	xField1.setEditable(false);
	xField1.setBorder(new EmptyBorder(3,5,7,9));
	xField1.setColumns(10);
	
	xField2.setFont(new Font("굴림", Font.PLAIN, 20));
	xField2.setForeground(Color.WHITE);
	xField2.setText("X");
	xField2.setOpaque(false);
	xField2.setEditable(false);
	xField2.setHorizontalAlignment(SwingConstants.CENTER);
	xField2.setColumns(10);
	xField2.setBorder(new EmptyBorder(3,5,7,9));

	textA.setFont(new Font("Monospaced", Font.PLAIN, 16));
	textA.setForeground(Color.WHITE);
	textA.setEditable(false);
	textA.setText("\uB0A8\uC740\r\n\uBE14\uB7ED");
	textA.setOpaque(false);
	
	chatBoard.setOpaque(false);
	chatBoard.setBorder(new LineBorder(Color.white, 3));
	chatBoard.setForeground(Color.white);
	chatField.setOpaque(false);
	chatField.setBorder(new LineBorder(Color.white, 3));
	chatField.setForeground(Color.white);

	
	this.repaint();
	}
	
	
	
	
	public static void main(String[] args) {
		new GameGUI();
	}
}
