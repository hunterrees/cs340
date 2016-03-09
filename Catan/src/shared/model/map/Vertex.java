package shared.model.map;

import shared.Piece;
import shared.locations.VertexLocation;

public class Vertex {
	
	private Piece piece;
	private VertexLocation location;
	private Port port;
	
	//constructor
	/**
	 * 
	 * @param location, the VertexLocation of this Vertex
	 */
	public Vertex(VertexLocation location) {
		piece = null;
		this.location = location;
		port = null;
	}
	
	//functions
	/**
	 * @return true if you can build a city here (settlement already here)
	 */
	public boolean canBuildCity() {
		return false;
	}
	
	/**
	 * @return true if you can build a settlement here (no adjacent buildings and a road connecting)
	 */
	public boolean canBuildSettlement() {
		return false;
	}

	
	
	//Getters and Setters
	
	/**
	 * @return the piece
	 */
	public Piece getPiece() {
		return piece;
	}

	/**
	 * @param piece the piece to set
	 */
	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	/**
	 * @return the VertexLocation
	 */
	public VertexLocation getLocation() {
		return location;
	}

	/**
	 * @param location the VertexLocation to set
	 */
	public void setLocation(VertexLocation location) {
		this.location = location;
	}

	/**
	 * @return the port
	 */
	public Port getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(Port port) {
		this.port = port;
	}
	
	
	
	
	
}
