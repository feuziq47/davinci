package gaming;
public class Joker_Block extends Block{
	
	public String hyp;
	
	public Joker_Block(boolean color) {
		super(color);
		hyp = "-";
	}
	
	public String getHyphen() {
		return hyp;
	}
	
	public String toString() {
		String bc;
		if (color)
			bc = "W";
		else
			bc = "B";
		return "{color: "+bc+", stand: "+stand+", hyp: "+hyp+"}";
	}
}
