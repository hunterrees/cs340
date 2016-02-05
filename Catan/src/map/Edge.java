package map;

import shared.Piece;
import shared.locations.EdgeLocation;

public class Edge {

	private Piece piece;
	private EdgeLocation location;
	
	
	// comment to zach
	/**
	 * 
	 * @param location, the EdgeLocation for this Edge
	 
	 */
	public Edge(EdgeLocation location){
		
	}
	
	/**
	 * 
	 * @return true if this is a valid location for a road (no road currently at this location)
	 */
	public boolean canBuildRoad(){
		return false;
	}

	/**
	 * @return the piece
	 */
	public Piece getPiece() {
		return piece;
	}

	/**
	 * @param piece: the piece to set
	 */
	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	/**
	 * @return the location
	 */
	public EdgeLocation getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(EdgeLocation location) {
		this.location = location;
	}

	
	
	
}
