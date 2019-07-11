package gaming;
public class Num_Block extends Block{
	
	public int num;
	
	public Num_Block(int num, boolean color) {
		super(color);
		this.num = num;
	}
	
	public int getNum() {
		return num;
	}
	
	public String toString() {
		String bc;
		if (color)
			bc = "W";
		else
			bc = "B";
		return "{color: "+bc+", stand: "+stand+", num: "+num+"}";
	}
}