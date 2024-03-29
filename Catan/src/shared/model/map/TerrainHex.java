package shared.model.map;

import java.io.Serializable;
import java.util.HashMap;

import shared.Piece;
import shared.definitions.HexType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

public class TerrainHex implements Serializable{

	
	
	private HexLocation location;
	
	private HexType type;
	private Piece piece;
	private int number;
	
	HashMap<EdgeLocation, Edge> edges;
	HashMap<VertexLocation, Vertex> verticies;







	//constructor
	
	/**
	 * 
	 * @param x, x coordinate for the hex position
	 * @param y, y coordinate for the hex position
	 */
	public TerrainHex(HexLocation loc, HexType wheat, int num){
		location = loc;
		this.type = wheat;
		number = num;
		piece = null;
	}



	
	//getters + setters
	
	/**
	 * @return the edges
	 */
	public HashMap<EdgeLocation, Edge> getEdges() {
		return edges;
	}



	/**
	 * @param edges the edges to set
	 */
	public void setEdges(HashMap<EdgeLocation, Edge> edges) {
		this.edges = edges;
	}



	/**
	 * @return the verticies
	 */
	public HashMap<VertexLocation, Vertex> getVerticies() {
		return verticies;
	}



	/**
	 * @param verticies the verticies to set
	 */
	public void setVerticies(HashMap<VertexLocation, Vertex> verticies) {
		this.verticies = verticies;
	}





	/**
	 * @return the location
	 */
	public HexLocation getLocation() {
		return location;
	}




	/**
	 * @param location the location to set
	 */
	public void setLocation(HexLocation location) {
		this.location = location;
	}




	/**
	 * @return the type
	 */
	public HexType getType() {
		return type;
	}




	/**
	 * @param type the type to set
	 */
	public void setType(HexType type) {
		this.type = type;
	}




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
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}




	/**
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}





}
