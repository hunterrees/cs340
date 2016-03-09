package client.translators.moves;

import shared.locations.HexLocation;
import shared.locations.VertexDirection;

/*****This vertex location is for the translator, not to use on the map*****/
public class VertexLocation {

	private int x;
	private int y;
	private String direction;
	
	public VertexLocation(HexLocation location, VertexDirection direction) {
		super();
		this.x = location.getX();
		this.y = location.getY();
		this.direction = directionTranslate(direction);
	}
	
	private String directionTranslate(VertexDirection direction){
		switch(direction){
			case NorthWest: return "NW";
			case NorthEast: return "NE";
			case West: return "W";
			case East: return "E";
			case SouthWest: return "SW";
			case SouthEast: return "SE";
		}
		return "";
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
