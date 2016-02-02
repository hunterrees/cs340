package map;

import java.util.HashMap;

import shared.Piece;
import shared.definitions.HexType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

public class TerrainHex {

	
	
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
	public TerrainHex(HexType wheat, int num){
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

	

}
