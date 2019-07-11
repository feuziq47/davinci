package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.DefaultCaret;

import davinchiSnC.Client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class GameGUI_p extends JPanel{

	public JLabel BGLabel; //Background Image Labels
	public JLabel[] uImgLabel = new JLabel[4]; // user icon labels
	public JLabel[] BotbLabel = new JLabel[2]; // bottom blocks
	public JLabel[] cbLabel = new JLabel[4]; //user block labels
	public JLabel[] idLabel = new JLabel[4];	//ID labels
	
	
	public ImageIcon BGImage; //Background Image
	public ImageIcon[] userImg = new ImageIcon[4]; //user ICON
	public ImageIcon[] BotbImg = new ImageIcon[2]; //bottom block ICON
	public ImageIcon cboard; // user block Image
	public ImageIcon backBImg;
//	public ImageIcon[] btnImg;
	
	public JButton backB; // back button
	public pBtn[] pBtns=new pBtn[26];

	
	
	public JTextField whiteBnum;
	public JTextField blackBnum;
	public JTextField xField1 = new JTextField();
	public JTextField xField2 = new JTextField();
	public JTextField chatField = new JTextField(); // chatting input field
	public JTextArea textA = new JTextArea(); //"남은 블럭" 글자 표시
	public JTextArea chatBoard = new JTextArea(); // chatting board
	public JScrollPane chatPane = new JScrollPane();
	
	public JRadioButton selectB = new JRadioButton("검은색");	//라디오버튼 및 콤보박스 생성
	public JRadioButton selectW = new JRadioButton("흰색");
	public ButtonGroup selectColor=new ButtonGroup();
	public String[] numset=new String[13];
	public JComboBox<String> numBox;
	
	int curr_card_index = 0;
	
	Client c;
	
	public GameGUI_p(Client c){

		setLayout(null);
		setSize(540, 960);
		this.c = c;

		try { // load Image
			BGImage = new ImageIcon(ImageDir.bgImage1_dir);
			userImg[0] = new ImageIcon(ImageDir.uwhiteImage_dir);
			userImg[1] = new ImageIcon(ImageDir.uredImage_dir);
			userImg[2] = new ImageIcon(ImageDir.uNoneImage_dir);
			userImg[3] = new ImageIcon(ImageDir.uDeadImage_dir);
			BotbImg[0] = new ImageIcon(ImageDir.whiteImage_dir);
			BotbImg[1] = new ImageIcon(ImageDir.blackImage_dir);
			cboard = new ImageIcon(ImageDir.cardboardImage_dir);
			backBImg = new ImageIcon(ImageDir.backPointerImage_dir);
		}catch(Exception e) {
			System.out.println("No Image File");
		}
		
//		uImgLabel[0] = new JLabel(userImg[0]);
//		uImgLabel[1] = new JLabel(userImg[1]);
//		uImgLabel[2] = new JLabel(userImg[2]);
//		uImgLabel[3] = new JLabel(userImg[3]);
		
		for(int i=0; i<uImgLabel.length; i++) {
			uImgLabel[i] = new JLabel(userImg[0]);
//			uImgLabel[i].setForeground(Color.white);
//			uImgLabel[i].setText(Integer.toString(i + 1));
		}
		
		BotbLabel[0] = new JLabel(BotbImg[0]);
		BotbLabel[1] = new JLabel(BotbImg[1]);
		cbLabel[0] = new JLabel(cboard);
		cbLabel[1] = new JLabel(cboard);
		cbLabel[2] = new JLabel(cboard);
		cbLabel[3] = new JLabel(cboard);
		
		BGLabel = new JLabel(BGImage);
		whiteBnum = new JTextField("13");
		blackBnum = new JTextField("13");
		backB = new JButton(backBImg);
		

		
		for(int i=0;i<12;i++) {							//라디오버튼 그룹추가 및 콤보박스 숫자설정
			numset[i]=Integer.toString(i);
		}
		numset[12]="-";
		numBox = new JComboBox<String>(numset);
		selectColor.add(selectB);
		selectColor.add(selectW);
		
		idLabel[0] = new JLabel("");
		idLabel[1] = new JLabel("");
		idLabel[2] = new JLabel("");
		idLabel[3] = new JLabel("");
		for(int i=0;i<4;i++) {
			idLabel[i].setForeground(Color.WHITE);
			idLabel[i].setHorizontalAlignment(SwingConstants.CENTER);
			idLabel[i].setOpaque(false);
		}
		idLabel[0].setBounds(12, 235, userImg[0].getIconWidth(),30);
		idLabel[1].setBounds(12, 390, userImg[1].getIconWidth(),30);
		idLabel[2].setBounds(12, 545, userImg[2].getIconWidth(),30);
		idLabel[3].setBounds(12, 700, userImg[3].getIconWidth(),30);
		
		for(int i=0;i<26;i++) {
			pBtns[i]=new pBtn();
//			if(i<13) {
//				pBtns[i].setColor(true);
//				pBtns[i].setNum(Integer.toString(i));
//				if(i==12) {
//					pBtns[i].setNum("-");
//				}
//			}else {
//				pBtns[i].setColor(false);
//				pBtns[i].setNum(Integer.toString(i-13));
//				if(i==25) {
//					pBtns[i].setNum("-");
//				}
//			}
		}
	
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
		chatPane.setBounds(10,750,370,140);
		chatField.setBounds(10,892, 370, 20);
		backB.setBounds(35, 27, backBImg.getIconWidth(), backBImg.getIconHeight());
		
		BGLabel.setBounds(0,0,BGImage.getIconWidth(), BGImage.getIconHeight()); // background
		
		
		selectB.setBounds(100,720,80,30);					//라디오버튼,콤보박스 위치설정및 폰트설정
		selectB.setFont(new Font("굴림", Font.PLAIN,15));
		selectW.setBounds(200,720,80,30);
		selectW.setFont(new Font("굴림", Font.PLAIN,15));
		numBox.setBounds(350,720,70,30);
	
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
		chatBoard.setForeground(Color.white);
		chatBoard.setEditable(false);
		
		chatBoard.setLineWrap(true); // 자동 줄 바꿈
        DefaultCaret caret = (DefaultCaret)chatBoard.getCaret();
        caret.setUpdatePolicy((DefaultCaret.ALWAYS_UPDATE)); //자동 스크롤 기능
		
		chatField.setOpaque(false);
		chatField.setBorder(new LineBorder(Color.white, 3));
		chatField.setForeground(Color.white);
		
		chatPane.setViewportView(chatBoard);
		chatPane.setOpaque(false);
		chatPane.getViewport().setOpaque(false);
		chatPane.setBorder(new LineBorder(Color.white, 3));
		
		for(int i=0; i<4; i++) {
			cbLabel[i].setLayout(new GridLayout(2,0));
			//value 가질수있어야함 ,text 있고,색깔
		}
		
		// Test!
//		cbLabel[0].add(pBtns[0],0);
//		cbLabel[0].add(pBtns[16],1);
	//	cbLabel[0].add(pBtns[2],2);
	//	cbLabel[0].add(pBtns[3],3);
	//	pBtns[2].openBlock();
		
		for(int i=0; i<4; i++) {
			add(uImgLabel[i]);
			add(cbLabel[i]);
			add(idLabel[i]);
		}
		add(selectB);	//검은색 라디오버튼 추가
		add(selectW);	//흰색 라디오버튼 추가
		add(numBox);	//숫자 콤보박스 추가
		add(BotbLabel[0]);
		add(BotbLabel[1]);
		add(whiteBnum);
		add(blackBnum);
		add(xField1);
		add(xField2);
		add(textA);
		add(chatPane);
		add(chatField);
		add(backB);
		add(BGLabel);
		

        for(int i=0;i<26;i++) {
        	int bInit=i;
        	pBtns[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
//					System.out.println(pBtns[bInit].getNum());
//					pBtns[bInit].openBlock();
					// send signal
					int player_idx = pBtns[bInit].getOwnerIndex();
					int btn_idx = pBtns[bInit].getIndex();
					try {
						c.sendButton(player_idx, btn_idx);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
        	});
        }
        
        for(int i=0; i<4; i++){ // 유저 이미지 리스너 (클릭 시 색상 바뀜/레디상태)
            int Init = i;
            uImgLabel[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                	try {
						c.sendReady(Init + 1);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
            });
        }
		
		chatField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if(chatField.getText().equals("")) {
                        return;
                    }
                    String msg;
                    msg = chatField.getText();
                    sendChat(msg);
                    chatField.setText("");
                }
            }
        });
	}
	
	public void freshCardIndex() {
		curr_card_index = 0;
	}
	
	public void addBlock(int player_idx, int card_idx) {
		cbLabel[player_idx].add(pBtns[curr_card_index] , card_idx);
		pBtns[curr_card_index].closeBlock();
		sortCardIndex(player_idx);
		curr_card_index ++;
	}
	
	public void sortCardIndex(int index) {
		int i = 0;
		for( Component component : cbLabel[index].getComponents()) {
			pBtn p = (pBtn) component;
			p.setIndex(i);
			p.setOwnerIndex(index);
			i++;
		}
	}
	
	public void setUserNameSpace(int index, String name) {
		idLabel[index].setText("[" + Integer.toString(index + 1) + "] " + name);
	}
	public void setUserImgSpace(int index, int type) {
		uImgLabel[index].setIcon(userImg[type]);
	}
	public void clearUserNameSpace(int index) {
		idLabel[index].setText("");
	}
	public void clearAllUser() {
		for (JLabel idl : idLabel)
			idl.setText("");
		for (JLabel idl : uImgLabel)
			idl.setIcon(userImg[2]);
	}
	public void clearAllCard() {
		whiteBnum.setText("13");
		blackBnum.setText("13");
		for(pBtn card : pBtns) {
			card.closeBlock();
		}
		for(JLabel board : cbLabel) {
			board.removeAll();
		}
	}
	public void clearChat() {
		chatBoard.setText("");
		chatField.setText("");
	}
	public void clearRoom() {
		freshCardIndex();
		clearChat();
		clearAllUser();
		clearAllCard();
	}
	public void clearAll4Game() {
		freshCardIndex();
		clearChat();
		clearAllCard();
	}
	
	
	public void sendChat(String msg) {
		try {
			c.chatSend(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Dimension getPreferredSize(){
		return new Dimension(540,960);
	}
	
	public void changeReady(int index, boolean ready) {
		if(ready){
			uImgLabel[index].setIcon(userImg[1]);
		}else{
			uImgLabel[index].setIcon(userImg[0]);
		}
	}

	public static void main(String[] args) {

		JFrame exam = new JFrame("example");
		GameGUI_p gp = new GameGUI_p(null);

		exam.setLayout(new BorderLayout());
		exam.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		exam.add(gp, BorderLayout.CENTER);
		exam.pack();
		exam.setVisible(true);
		exam.setMinimumSize(exam.getPreferredSize());
		exam.setResizable(false);
		exam.setLocationRelativeTo(null);
		exam.repaint();

	}
	
	class pBtn extends JButton {
		boolean isshow;	//show가 true이면 숫자가 보이는 상태, false이면 뒤집어진상태.
		boolean color;		//color가 true이면 흰색, false이면 검은색
		String num;
		int index;
		int owner_idx;
		ImageIcon[] btnImg =new ImageIcon[3];
		
		public pBtn() {
			this.isshow=false;
			try {
				btnImg[0] = new ImageIcon(ImageDir.whiteImage_dir); 
				btnImg[1] = new ImageIcon(ImageDir.blackImage_dir);
				btnImg[2] = new ImageIcon(ImageDir.reverseImage_dir);
			}catch(Exception e) {e.printStackTrace();}
			setIcon(btnImg[2]);
		}
		

		public int getOwnerIndex() {
			return this.owner_idx;
		}
		public void setOwnerIndex(int owner_idx) {
			this.owner_idx=owner_idx;
		}
		public int getIndex() {
			return this.index;
		}
		public void setIndex(int index) {
			this.index=index;
		}
		
		public boolean getShow() {
			return this.isshow;
		}
		public String getNum() {
			return this.num;
		}
		public void setShow(boolean isshow) {
			this.isshow=isshow;
		}
		public void setNum(String num) {
			this.num=num;
		}
		public void openBlock(boolean color, String num) {
			this.color=color;
			isshow= true;
			this.num = num;
			if(color) {
				setIcon(btnImg[0]);		//흰색 버튼이미지
				setHorizontalTextPosition(SwingConstants.HORIZONTAL);	//가운데 정렬
				setForeground(Color.black);
				setOpaque(false);
				setContentAreaFilled(false);
				setBorderPainted(false);
			}else {
				setIcon(btnImg[1]);		//검은색 버튼이미지
				setHorizontalTextPosition(SwingConstants.HORIZONTAL);	//가운데 정렬
				setForeground(Color.white);			
				setOpaque(false);
				setContentAreaFilled(false);
				setBorderPainted(false);
			}
			this.setText(num);
		}
		public void closeBlock( ) {
			this.setIcon(btnImg[2]);
			setHorizontalTextPosition(SwingConstants.HORIZONTAL);	//가운데 정렬
			setForeground(Color.black);
			setOpaque(false);
			setContentAreaFilled(false);
			setBorderPainted(false);
			revalidate();
			this.setText("");
		}
		
		
	}

	public void openBlock(int pidx, int cidx, boolean isBlack, String num) {
		if (num.equals("12"))
			num = "-";
		pBtn pbtn = (pBtn) cbLabel[pidx].getComponent(cidx);
		cardNumDecay(isBlack);
		pbtn.openBlock(!isBlack, num);
	}
	
	public void cardNumDecay(boolean isBlack) {
		JTextField tf; 
		if (isBlack)
			tf = blackBnum;
		else
			tf = whiteBnum;
		int cur_cnt = Integer.parseInt(tf.getText());
		tf.setText(Integer.toString(cur_cnt - 1));
	}

	public void playerDead(int pidx) {
		uImgLabel[pidx].setIcon(userImg[3]);		
	}

}	

