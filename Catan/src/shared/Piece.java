package shared;

import client.gameManager.GameManager;
import shared.definitions.CatanColor;
import shared.definitions.PieceType;
import shared.model.GameModel;
import shared.model.map.Edge;
import shared.model.map.Vertex;


//Add player ID
public class Piece 
{
	/**
	 * enum type of piece
	 */
	private PieceType type;
	/**
	 * edge the piece is built on
	 */
	private Edge edge;
	/**
	 * vertice the piece is built on
	 */
	private Vertex vertice;

	private int playerID;
	/**
	 * set type, and build location of piece
	 * @param type
	 * @param edge
	 * @param vertice
	 */
	public Piece(PieceType type, Edge edge, Vertex vertice, int playerID)
	{
		this.type = type;
		this.edge = edge;
		this.vertice = vertice;
		this.playerID = playerID;
	}
	
	/**
	 * returns edge piece is built on
	 * @return
	 */
	public Edge getEdge()
	{
		return edge;
	}
	/**
	 * returns vertice piece is built on
	 * @return
	 */
	public Vertex getVertice()
	{
		return vertice;
	}
	/**
	 * returns enum type of piece
	 * @return
	 */
	public PieceType getPieceType()
	{
		return type;
	}

	public int getPlayerID() {
		return playerID;
	}

	public CatanColor getColor() {
		GameModel model = GameManager.getInstance().getModel();
		return model.getColor(playerID);
	}
}
