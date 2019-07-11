package DB;

public class DTO {
	private int code;
	private String ID;
	private String Password;
	private int win;
	private int lose;
	
	public DTO() {}
	public DTO(int code,String ID, String Password, int win, int lose) {
		this.code=code;
		this.ID=ID;
		this.Password=Password;
		this.win=win;
		this.lose=lose;
	}
	public int getCode() { return code;}
	public String getId() { return ID;}
	public String getPassword() { return Password;}
	public int getWin() { return win;}
	public int getLose() { return lose;}
	
	public void setCode(int code) {
		this.code=code;
	}
	public void setId(String ID) {
		this.ID=ID;
	}
	public void setPassword(String Password) {
		this.Password=Password;
	}
	public void setWin(int win) {
		this.win=win;
	}
	public void setLose(int lose) {
		this.lose=lose;
	}
}