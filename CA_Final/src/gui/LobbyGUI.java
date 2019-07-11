package gui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;

public class LobbyGUI extends JFrame{

	JLabel BGLabel; // Background label
	ImageIcon BGimg; // background img
	JButton startB; // go to room list
	JButton recordB; // go to State
	JButton backB; // back button
	ImageIcon[] bImg = new ImageIcon[3];
	
	
	public LobbyGUI(){
		this.setTitle("Davinci Code : Lobby");
		this.setSize(540, 960);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		getContentPane().setLayout(null);
		
		try {
			BGimg = new ImageIcon(ImageDir.lobbybackImage_dir);
			bImg[0] = new ImageIcon(ImageDir.startButtonImage_dir);
			bImg[1] = new ImageIcon(ImageDir.recordImage_dir);
			bImg[2] = new ImageIcon(ImageDir.backPointerImage_dir);
		}catch(Exception e) {
			
		}
		
		BGLabel = new JLabel(BGimg);
		startB = new JButton(bImg[0]);
		recordB = new JButton(bImg[1]);
		backB = new JButton(bImg[2]);
		
		startB.setBounds(133, 309, bImg[0].getIconWidth(), bImg[0].getIconHeight());
		recordB.setBounds(133, 524, bImg[1].getIconWidth(), bImg[1].getIconHeight());
		backB.setBounds(35, 35, bImg[2].getIconWidth(), bImg[2].getIconHeight());
		BGLabel.setBounds(0,0, BGimg.getIconWidth(), BGimg.getIconHeight());

		
		
		getContentPane().add(startB);
		getContentPane().add(recordB);
		getContentPane().add(backB);
		getContentPane().add(BGLabel);
		
		
		this.repaint();
	}
	
	
	
	
	
	

	public static void main(String[] args) {
		new LobbyGUI();
	}
}
