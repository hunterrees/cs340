package translators.moves;
/*****This edge location is for the translator, not to use on the map*****/
public class EdgeLocation {
	
	private int x;
	private int y;
	private String direction;
	
	public EdgeLocation(int x, int y, String direction) {
		super();
		this.x = x;
		this.y = y;
		this.direction = direction;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
}
