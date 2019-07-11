package tts;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import javazoom.jl.player.Player;

public class Readtext {
	private	InputStream in;
	private Player player;
	
	public Readtext(InputStream in) {
		this.in=in;
	}
	
	public void play() {
		try { 
            BufferedInputStream buffer = new BufferedInputStream(this.in);
            player = new Player(buffer);
            player.play();
		}catch (Exception e) {
			System.out.println(e);
		}
	}
}
