package map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import player.Player;
import shared.Piece;
import shared.definitions.CatanColor;
import shared.definitions.HexType;
import shared.definitions.PieceType;
import shared.locations.*;

public class Map {
	int test;
	private HashMap<HexLocation, TerrainHex> hexes = new HashMap<HexLocation, TerrainHex>();
	private HashMap<EdgeLocation, Edge> edges = new HashMap<EdgeLocation, Edge>();
	private HashMap<VertexLocation, Vertex> verticies = new HashMap<VertexLocation, Vertex>();
	
	// change
	// changedf
	public Map() {
		
		buildHexes();
		buildEdges();
		buildVerticies();
	}
	
	public Map(HashMap<HexLocation, TerrainHex> hexes, HashMap<EdgeLocation, Edge> edges,
			HashMap<VertexLocation, Vertex> verticies){
		
		this.hexes = hexes;
		this.edges = edges;
		this.verticies = verticies;
	}
	
	
	
	
	
	//functions i wrotedsf
		/**
		 * @return true if you can build a city here (settlement already here)j
		 */
		public boolean canBuildCity(int playerID, VertexLocation loc) {
			Piece tempPiece = verticies.get(loc).getPiece();

			// Not valid if there's no piece there already
			if(tempPiece == null) {
				return false;
			}

			// Not valid if the piece doesn't belong to the player
			if(tempPiece.getPlayerID() != playerID) {
				return false;
			}

			// Not valid if the piece isn't a settlement
			if(tempPiece.getPieceType() != PieceType.SETTLEMENT) {
				return false;
			}


			// If it passes all the tests then it's valid
			return true;
		}
		
		/**
		 * @return true if you can build a settlement here (no adjacent buildings and a road connecting)
		 */

		public boolean canBuildSettlement(int playerID, boolean setUp, VertexLocation loc) {
			if(verticies.get(loc).getPiece() != null) {
				return false;
			}



			switch(loc.getDir()) {
				case East: return vertexECase(playerID, setUp, loc);
				case West: return vertexWCase(playerID, setUp, loc);
				case NorthEast: return vertexNECase(playerID, setUp, loc);
				case SouthEast: return vertexSECase(playerID, setUp, loc);
				case NorthWest: return vertexNWCase(playerID, setUp, loc);
				case SouthWest: return vertexSWCase(playerID, setUp, loc);
				default: System.out.println("Error! VertexLocation doesn't exist!");
			}

			System.out.println("Error! It should never come here!");
			return false;
		}









		/**
		 *
		 * @param playerID
		 * @param setUp
		 * @param loc
		 * @return
		 */
		public boolean vertexECase(int playerID, boolean setUp, VertexLocation loc) {
			TerrainHex currentHex = hexes.get(loc.getHexLoc());
			TerrainHex upperRightHex = hexes.get(loc.getHexLoc().getNeighborLoc(EdgeDirection.NorthEast));

			// Check if the corners are valid
			Vertex rightCorner = verticies.get(new VertexLocation(upperRightHex.getLocation(), VertexDirection.SouthEast).getNormalizedLocation());
			Vertex upCorner = verticies.get(new VertexLocation(upperRightHex.getLocation(), VertexDirection.NorthEast).getNormalizedLocation());
			Vertex downCorner = verticies.get(new VertexLocation(currentHex.getLocation(), VertexDirection.SouthEast).getNormalizedLocation());


			if(rightCorner.getPiece() != null || upCorner.getPiece() != null || downCorner.getPiece() != null) {
				return false;
			}

			if(!setUp){
				// Check if the edges are valid
				Edge rightEdge = edges.get(new EdgeLocation(upperRightHex.getLocation(), EdgeDirection.South).getNormalizedLocation());
				Edge upEdge = edges.get(new EdgeLocation(currentHex.getLocation(), EdgeDirection.NorthEast).getNormalizedLocation());
				Edge downEdge = edges.get(new EdgeLocation(currentHex.getLocation(), EdgeDirection.SouthEast).getNormalizedLocation());



				if(rightEdge.getPiece() != null && rightEdge.getPiece().getPlayerID() == playerID){
					return true;
				}
				else if(upEdge.getPiece() != null && upEdge.getPiece().getPlayerID() == playerID){
					return true;
				}
				else if(downEdge.getPiece() != null && downEdge.getPiece().getPlayerID() == playerID){
					return true;
				}
				else{
					return false;
				}
			}
			return true;
		}

	/**
	 *
	 * @param playerID
	 * @param setup
	 * @param loc
     * @return
     */
		public boolean vertexWCase(int playerID, boolean setup, VertexLocation loc) {





			TerrainHex currentHex = hexes.get(loc.getHexLoc());
			TerrainHex upperLeftHex = hexes.get(loc.getHexLoc().getNeighborLoc(EdgeDirection.NorthWest));

			// Check if the corners are valid
			Vertex upCorner = verticies.get(new VertexLocation(currentHex.getLocation(), VertexDirection.NorthWest).getNormalizedLocation());
			Vertex leftCorner = verticies.get(new VertexLocation(upperLeftHex.getLocation(), VertexDirection.SouthWest).getNormalizedLocation());
			Vertex downCorner = verticies.get(new VertexLocation(currentHex.getLocation(), VertexDirection.SouthWest).getNormalizedLocation());


			if(leftCorner.getPiece() != null || upCorner.getPiece() != null || downCorner.getPiece() != null) {
				return false;
			}

			if(!setup){
				// Check if the edges are valid
				Edge leftEdge = edges.get(new EdgeLocation(upperLeftHex.getLocation(), EdgeDirection.South).getNormalizedLocation());
				Edge upEdge = edges.get(new EdgeLocation(currentHex.getLocation(), EdgeDirection.NorthWest).getNormalizedLocation());
				Edge downEdge = edges.get(new EdgeLocation(currentHex.getLocation(), EdgeDirection.SouthWest).getNormalizedLocation());



				if(leftEdge.getPiece() != null && leftEdge.getPiece().getPlayerID() == playerID){
					return true;
				}
				else if(upEdge.getPiece() != null && upEdge.getPiece().getPlayerID() == playerID){
					return true;
				}
				else if(downEdge.getPiece() != null && downEdge.getPiece().getPlayerID() == playerID){
					return true;
				}
				else{
					return false;
				}
			}
			return true;

		}

	/**
	 *
	 * @param playerID
	 * @param setup
	 * @param loc
     * @return
     */
		public boolean vertexNECase(int playerID, boolean setup, VertexLocation loc) {





			TerrainHex currentHex = hexes.get(loc.getHexLoc());
			TerrainHex upperHex = hexes.get(loc.getHexLoc().getNeighborLoc(EdgeDirection.North));

			// Check if the corners are valid
			Vertex upCorner = verticies.get(new VertexLocation(upperHex.getLocation(), VertexDirection.East).getNormalizedLocation());
			Vertex leftCorner = verticies.get(new VertexLocation(currentHex.getLocation(), VertexDirection.NorthWest).getNormalizedLocation());
			Vertex downCorner = verticies.get(new VertexLocation(currentHex.getLocation(), VertexDirection.East).getNormalizedLocation());


			if(leftCorner.getPiece() != null || upCorner.getPiece() != null || downCorner.getPiece() != null) {
				return false;
			}

			if(!setup){
				// Check if the edges are valid
				Edge leftEdge = edges.get(new EdgeLocation(currentHex.getLocation(), EdgeDirection.North).getNormalizedLocation());
				Edge upEdge = edges.get(new EdgeLocation(upperHex.getLocation(), EdgeDirection.SouthEast).getNormalizedLocation());
				Edge downEdge = edges.get(new EdgeLocation(currentHex.getLocation(), EdgeDirection.NorthEast).getNormalizedLocation());



				if(leftEdge.getPiece() != null && leftEdge.getPiece().getPlayerID() == playerID){
					return true;
				}
				else if(upEdge.getPiece() != null && upEdge.getPiece().getPlayerID() == playerID){
					return true;
				}
				else if(downEdge.getPiece() != null && downEdge.getPiece().getPlayerID() == playerID){
					return true;
				}
				else{
					return false;
				}
			}
			return true;


		}

	/**
	 *
	 * @param playerID
	 * @param setup
	 * @param loc
     * @return
     */
		public boolean vertexSECase(int playerID, boolean setup, VertexLocation loc) {




			TerrainHex currentHex = hexes.get(loc.getHexLoc());
			TerrainHex lowerHex = hexes.get(loc.getHexLoc().getNeighborLoc(EdgeDirection.South));

			// Check if the corners are valid
			Vertex upCorner = verticies.get(new VertexLocation(currentHex.getLocation(), VertexDirection.East).getNormalizedLocation());
			Vertex leftCorner = verticies.get(new VertexLocation(currentHex.getLocation(), VertexDirection.SouthWest).getNormalizedLocation());
			Vertex downCorner = verticies.get(new VertexLocation(lowerHex.getLocation(), VertexDirection.East).getNormalizedLocation());


			if(leftCorner.getPiece() != null || upCorner.getPiece() != null || downCorner.getPiece() != null) {
				return false;
			}

			if(!setup){
				// Check if the edges are valid
				Edge leftEdge = edges.get(new EdgeLocation(currentHex.getLocation(), EdgeDirection.South).getNormalizedLocation());
				Edge upEdge = edges.get(new EdgeLocation(currentHex.getLocation(), EdgeDirection.SouthEast).getNormalizedLocation());
				Edge downEdge = edges.get(new EdgeLocation(lowerHex.getLocation(), EdgeDirection.NorthEast).getNormalizedLocation());



				if(leftEdge.getPiece() != null && leftEdge.getPiece().getPlayerID() == playerID){
					return true;
				}
				else if(upEdge.getPiece() != null && upEdge.getPiece().getPlayerID() == playerID){
					return true;
				}
				else if(downEdge.getPiece() != null && downEdge.getPiece().getPlayerID() == playerID){
					return true;
				}
				else{
					return false;
				}
			}
			return true;


		}

	/**
	 *
	 * @param playerID
	 * @param setup
	 * @param loc
     * @return
     */
		public boolean vertexNWCase(int playerID, boolean setup, VertexLocation loc) {





			TerrainHex currentHex = hexes.get(loc.getHexLoc());
			TerrainHex upperHex = hexes.get(loc.getHexLoc().getNeighborLoc(EdgeDirection.North));

			System.out.println(upperHex.getLocation());
			// Check if the corners are valid
			Vertex upCorner = verticies.get(new VertexLocation(upperHex.getLocation(), VertexDirection.West).getNormalizedLocation());
			Vertex rightCorner = verticies.get(new VertexLocation(currentHex.getLocation(), VertexDirection.NorthEast).getNormalizedLocation());
			Vertex downCorner = verticies.get(new VertexLocation(currentHex.getLocation(), VertexDirection.West).getNormalizedLocation());


			if(rightCorner != null && rightCorner.getPiece() != null || upCorner != null && upCorner.getPiece() != null || rightCorner != null && downCorner.getPiece() != null) {
				return false;
			}

			if(!setup){
				// Check if the edges are valid
				Edge rightEdge = edges.get(new EdgeLocation(currentHex.getLocation(), EdgeDirection.North).getNormalizedLocation());
				Edge upEdge = edges.get(new EdgeLocation(upperHex.getLocation(), EdgeDirection.NorthEast).getNormalizedLocation());
				Edge downEdge = edges.get(new EdgeLocation(currentHex.getLocation(), EdgeDirection.SouthWest).getNormalizedLocation());



				if(rightEdge.getPiece() != null && rightEdge.getPiece().getPlayerID() == playerID){
					return true;
				}
				else if(upEdge.getPiece() != null && upEdge.getPiece().getPlayerID() == playerID){
					return true;
				}
				else if(downEdge.getPiece() != null && downEdge.getPiece().getPlayerID() == playerID){
					return true;
				}
				else{
					return false;
				}
			}
			return true;
		}

		public boolean vertexSWCase(int playerID, boolean setup, VertexLocation loc) { // Not done fix this




			TerrainHex currentHex = hexes.get(loc.getHexLoc());
			TerrainHex lowerHex = hexes.get(loc.getHexLoc().getNeighborLoc(EdgeDirection.South));

			// Check if the corners are valid
			Vertex upCorner = verticies.get(new VertexLocation(currentHex.getLocation(), VertexDirection.West).getNormalizedLocation());
			Vertex rightCorner = verticies.get(new VertexLocation(currentHex.getLocation(), VertexDirection.SouthEast).getNormalizedLocation());
			Vertex downCorner = verticies.get(new VertexLocation(lowerHex.getLocation(), VertexDirection.West).getNormalizedLocation());


			if(rightCorner.getPiece() != null || upCorner.getPiece() != null || downCorner.getPiece() != null) {
				return false;
			}

			if(!setup){
				// Check if the edges are valid
				Edge rightEdge = edges.get(new EdgeLocation(currentHex.getLocation(), EdgeDirection.South).getNormalizedLocation());
				Edge upEdge = edges.get(new EdgeLocation(currentHex.getLocation(), EdgeDirection.SouthWest).getNormalizedLocation());
				Edge downEdge = edges.get(new EdgeLocation(lowerHex.getLocation(), EdgeDirection.NorthWest).getNormalizedLocation());



				if(rightEdge.getPiece() != null && rightEdge.getPiece().getPlayerID() == playerID){
					return true;
				}
				else if(upEdge.getPiece() != null && upEdge.getPiece().getPlayerID() == playerID){
					return true;
				}
				else if(downEdge.getPiece() != null && downEdge.getPiece().getPlayerID() == playerID){
					return true;
				}
				else{
					return false;
				}
			}
			return true;
		}



	/**
		 * 
		 * @return true if this is a valid location for a road (no road currently at this location)
		 */
		public boolean canBuildRoad(int playerID, boolean free, EdgeLocation loc) {
			if(edges.get(loc).getPiece() != null) {
				return false;
			}



			TerrainHex hex = hexes.get(loc.getHexLoc());
			TerrainHex neighbor = hexes.get(hex.getLocation().getNeighborLoc(loc.getDir()));

			switch(loc.getDir()) {
				case NorthWest: return edgeNWCase(playerID, free, loc, hex, neighbor);
				case North: return edgeNCase(playerID, free, loc, hex, neighbor);
				case NorthEast: return edgeNECase(playerID, free, loc, hex, neighbor);
				case SouthEast: return edgeSECase(playerID, free, loc, hex, neighbor);
				case South: return edgeSCase(playerID, free, loc, hex, neighbor);
				case SouthWest: return edgeSWCase(playerID, free, loc, hex, neighbor);


				default: System.out.println("Error! The EdgeLocation doesn't exist!");
			}
			System.out.println("Error! Shouldn't ever reach here!");
			return false;
		}


	/**
	 *
	 * @param playerID
	 * @param setup
	 * @param loc
	 * @param lowerRightHex
	 * @param upperLeftHex
     * @return
     */
	private boolean edgeNWCase(int playerID, boolean setup, EdgeLocation loc, TerrainHex lowerRightHex, TerrainHex upperLeftHex) {
		TerrainHex hex = hexes.get(loc.getHexLoc());



		// Check if the surrounding edges make this case valid
		Edge upperLeft = edges.get(new EdgeLocation(upperLeftHex.getLocation(),EdgeDirection.South).getNormalizedLocation());
		Edge upperRight = edges.get(new EdgeLocation(upperLeftHex.getLocation(),EdgeDirection.NorthEast).getNormalizedLocation());
		Edge lowerLeft = edges.get(new EdgeLocation(lowerRightHex.getLocation(),EdgeDirection.SouthWest).getNormalizedLocation());
		Edge lowerRight = edges.get(new EdgeLocation(lowerRightHex.getLocation(),EdgeDirection.North).getNormalizedLocation());

		boolean edgeValid = false;


		if(upperLeft.getPiece() != null) {
			if (upperLeft.getPiece().getPlayerID() == playerID) {
				edgeValid = true;
			}
		}
		if(upperRight.getPiece() != null) {
			if (upperRight.getPiece().getPlayerID() == playerID) {
				edgeValid = true;
			}
		}
		if(lowerLeft.getPiece() != null) {
			if (lowerLeft.getPiece().getPlayerID() == playerID) {
				edgeValid = true;
			}
		}
		if(lowerRight.getPiece() != null) {
			if (lowerRight.getPiece().getPlayerID() == playerID) {
				edgeValid = true;
			}
		}


		// Check if the surrounding corners make this case valid
		Vertex left = verticies.get(new VertexLocation(loc.getHexLoc(), VertexDirection.NorthWest).getNormalizedLocation());
		Vertex right = verticies.get(new VertexLocation(loc.getHexLoc(), VertexDirection.NorthEast).getNormalizedLocation());

		boolean cornerValid = false;

		if(left.getPiece() != null) {
			if (left.getPiece().getPlayerID() == playerID) {
				cornerValid = true;
			}
		}
		if(right.getPiece() != null) {
			if (right.getPiece().getPlayerID() == playerID) {
				cornerValid = true;
			}
		}
		if(left.getPiece() != null) {
			if (left.getPiece().getPlayerID() != playerID) {
				cornerValid = false;
			}
		}
		if(right.getPiece() != null) {
			if (right.getPiece().getPlayerID() != playerID) {
				cornerValid = false;
			}
		}

		// See if it's valid overall
		boolean canBuildRoad = edgeValid || cornerValid;



		return canBuildRoad;
	}

	/**
	 *
	 * @param playerID
	 * @param setup
	 * @param loc
	 * @param lowerHex
	 * @param upperHex
     * @return
     */
	private boolean edgeNCase(int playerID, boolean setup, EdgeLocation loc, TerrainHex lowerHex, TerrainHex upperHex) {
		TerrainHex hex = hexes.get(loc.getHexLoc());

		// Check if the surrounding edges make this case valid
		Edge upperLeft = edges.get(new EdgeLocation(upperHex.getLocation(),EdgeDirection.SouthWest).getNormalizedLocation());
		Edge upperRight = edges.get(new EdgeLocation(upperHex.getLocation(),EdgeDirection.SouthEast).getNormalizedLocation());
		Edge lowerLeft = edges.get(new EdgeLocation(lowerHex.getLocation(),EdgeDirection.NorthWest).getNormalizedLocation());
		Edge lowerRight = edges.get(new EdgeLocation(lowerHex.getLocation(),EdgeDirection.NorthEast).getNormalizedLocation());

		boolean edgeValid = false;


		if(upperLeft.getPiece() != null) {
			if (upperLeft.getPiece().getPlayerID() == playerID) {
				edgeValid = true;
			}
		}
		if(upperRight.getPiece() != null) {
			if (upperRight.getPiece().getPlayerID() == playerID) {
				edgeValid = true;
			}
		}
		if(lowerLeft.getPiece() != null) {
			if (lowerLeft.getPiece().getPlayerID() == playerID) {
				edgeValid = true;
			}
		}
		if(lowerRight.getPiece() != null) {
			if (lowerRight.getPiece().getPlayerID() == playerID) {
				edgeValid = true;
			}
		}


		// Check if the surrounding corners make this case valid
		Vertex left = verticies.get(new VertexLocation(loc.getHexLoc(), VertexDirection.West).getNormalizedLocation());
		Vertex right = verticies.get(new VertexLocation(loc.getHexLoc(), VertexDirection.East).getNormalizedLocation());

		boolean cornerValid = false;

		if(left.getPiece() != null) {
			if (left.getPiece().getPlayerID() == playerID) {
				cornerValid = true;
			}
		}
		if(right.getPiece() != null) {
			if (right.getPiece().getPlayerID() == playerID) {
				cornerValid = true;
			}
		}
		if(left.getPiece() != null) {
			if (left.getPiece().getPlayerID() != playerID) {
				cornerValid = false;
			}
		}
		if(right.getPiece() != null) {
			if (right.getPiece().getPlayerID() != playerID) {
				cornerValid = false;
			}
		}

		// See if it's valid overall
		boolean canBuildRoad = edgeValid || cornerValid;



		return canBuildRoad;
	}

	/**
	 *
	 * @param playerID
	 * @param setup
	 * @param loc
	 * @param lowerLeftHex
	 * @param upperRightHex
     * @return
     */
	private boolean edgeNECase(int playerID, boolean setup, EdgeLocation loc, TerrainHex lowerLeftHex, TerrainHex upperRightHex) {
		TerrainHex hex = hexes.get(loc.getHexLoc());


		// Check if the surrounding edges make this case valid
		Edge upperLeft = edges.get(new EdgeLocation(upperRightHex.getLocation(),EdgeDirection.NorthWest).getNormalizedLocation());
		Edge upperRight = edges.get(new EdgeLocation(upperRightHex.getLocation(),EdgeDirection.South).getNormalizedLocation());
		Edge lowerLeft = edges.get(new EdgeLocation(lowerLeftHex.getLocation(),EdgeDirection.North).getNormalizedLocation());
		Edge lowerRight = edges.get(new EdgeLocation(lowerLeftHex.getLocation(),EdgeDirection.SouthEast).getNormalizedLocation());

		boolean edgeValid = false;


		if(upperLeft.getPiece() != null) {
			if (upperLeft.getPiece().getPlayerID() == playerID) {
				edgeValid = true;
			}
		}
		if(upperRight.getPiece() != null) {
			if (upperRight.getPiece().getPlayerID() == playerID) {
				edgeValid = true;
			}
		}
		if(lowerLeft.getPiece() != null) {
			if (lowerLeft.getPiece().getPlayerID() == playerID) {
				edgeValid = true;
			}
		}
		if(lowerRight.getPiece() != null) {
			if (lowerRight.getPiece().getPlayerID() == playerID) {
				edgeValid = true;
			}
		}


		// Check if the surrounding corners make this case valid
		Vertex left = verticies.get(new VertexLocation(loc.getHexLoc(), VertexDirection.NorthEast).getNormalizedLocation());
		Vertex right = verticies.get(new VertexLocation(loc.getHexLoc(), VertexDirection.East).getNormalizedLocation());

		boolean cornerValid = false;

		if(left.getPiece() != null) {
			if (left.getPiece().getPlayerID() == playerID) {
				cornerValid = true;
			}
		}
		if(right.getPiece() != null) {
			if (right.getPiece().getPlayerID() == playerID) {
				cornerValid = true;
			}
		}
		if(left.getPiece() != null) {
			if (left.getPiece().getPlayerID() != playerID) {
				cornerValid = false;
			}
		}
		if(right.getPiece() != null) {
			if (right.getPiece().getPlayerID() != playerID) {
				cornerValid = false;
			}
		}

		// See if it's valid overall
		boolean canBuildRoad = edgeValid || cornerValid;



		return canBuildRoad;
	}

	/**
	 *
	 * @param playerID
	 * @param free
	 * @param loc
	 * @param upperLeftHex
	 * @param lowerRightHex
     * @return
     */
	private boolean edgeSECase(int playerID, boolean free, EdgeLocation loc, TerrainHex upperLeftHex, TerrainHex lowerRightHex) {
		TerrainHex hex = hexes.get(loc.getHexLoc());


		// Check if the surrounding edges make this case valid
		Edge upperLeft = edges.get(new EdgeLocation(upperLeftHex.getLocation(),EdgeDirection.South).getNormalizedLocation());
		Edge upperRight = edges.get(new EdgeLocation(upperLeftHex.getLocation(),EdgeDirection.NorthEast).getNormalizedLocation());
		Edge lowerLeft = edges.get(new EdgeLocation(lowerRightHex.getLocation(),EdgeDirection.SouthWest).getNormalizedLocation());
		Edge lowerRight = edges.get(new EdgeLocation(lowerRightHex.getLocation(),EdgeDirection.North).getNormalizedLocation());

		boolean edgeValid = false;


		if(upperLeft.getPiece() != null) {
			if (upperLeft.getPiece().getPlayerID() == playerID) {
				edgeValid = true;
			}
		}
		if(upperRight.getPiece() != null) {
			if (upperRight.getPiece().getPlayerID() == playerID) {
				edgeValid = true;
			}
		}
		if(lowerLeft.getPiece() != null) {
			if (lowerLeft.getPiece().getPlayerID() == playerID) {
				edgeValid = true;
			}
		}
		if(lowerRight.getPiece() != null) {
			if (lowerRight.getPiece().getPlayerID() == playerID) {
				edgeValid = true;
			}
		}


		// Check if the surrounding corners make this case valid
		Vertex left = verticies.get(new VertexLocation(loc.getHexLoc(), VertexDirection.West).getNormalizedLocation());
		Vertex right = verticies.get(new VertexLocation(loc.getHexLoc(), VertexDirection.NorthWest).getNormalizedLocation());

		boolean cornerValid = false;

		if(left.getPiece() != null) {
			if (left.getPiece().getPlayerID() == playerID) {
				cornerValid = true;
			}
		}
		if(right.getPiece() != null) {
			if (right.getPiece().getPlayerID() == playerID) {
				cornerValid = true;
			}
		}
		if(left.getPiece() != null) {
			if (left.getPiece().getPlayerID() != playerID) {
				cornerValid = false;
			}
		}
		if(right.getPiece() != null) {
			if (right.getPiece().getPlayerID() != playerID) {
				cornerValid = false;
			}
		}

		// See if it's valid overall
		boolean canBuildRoad = edgeValid || cornerValid;



		return canBuildRoad;
	}

	/**
	 *
	 * @param playerID
	 * @param free
	 * @param loc
	 * @param upperRightHex
	 * @param lowerLeftHex
     * @return
     */
	private boolean edgeSWCase(int playerID, boolean free, EdgeLocation loc, TerrainHex upperRightHex, TerrainHex lowerLeftHex) {
		TerrainHex hex = hexes.get(loc.getHexLoc());


		// Check if the surrounding edges make this case valid
		Edge upperLeft = edges.get(new EdgeLocation(upperRightHex.getLocation(),EdgeDirection.NorthWest).getNormalizedLocation());
		Edge upperRight = edges.get(new EdgeLocation(upperRightHex.getLocation(),EdgeDirection.South).getNormalizedLocation());
		Edge lowerLeft = edges.get(new EdgeLocation(lowerLeftHex.getLocation(),EdgeDirection.North).getNormalizedLocation());
		Edge lowerRight = edges.get(new EdgeLocation(lowerLeftHex.getLocation(),EdgeDirection.SouthEast).getNormalizedLocation());

		boolean edgeValid = false;


		if(upperLeft.getPiece() != null) {
			if (upperLeft.getPiece().getPlayerID() == playerID) {
				edgeValid = true;
			}
		}
		if(upperRight.getPiece() != null) {
			if (upperRight.getPiece().getPlayerID() == playerID) {
				edgeValid = true;
			}
		}
		if(lowerLeft.getPiece() != null) {
			if (lowerLeft.getPiece().getPlayerID() == playerID) {
				edgeValid = true;
			}
		}
		if(lowerRight.getPiece() != null) {
			if (lowerRight.getPiece().getPlayerID() == playerID) {
				edgeValid = true;
			}
		}


		// Check if the surrounding corners make this case valid
		Vertex left = verticies.get(new VertexLocation(loc.getHexLoc(), VertexDirection.NorthEast).getNormalizedLocation());
		Vertex right = verticies.get(new VertexLocation(loc.getHexLoc(), VertexDirection.East).getNormalizedLocation());

		boolean cornerValid = false;

		if(left.getPiece() != null) {
			if (left.getPiece().getPlayerID() == playerID) {
				cornerValid = true;
			}
		}
		if(right.getPiece() != null) {
			if (right.getPiece().getPlayerID() == playerID) {
				cornerValid = true;
			}
		}
		if(left.getPiece() != null) {
			if (left.getPiece().getPlayerID() != playerID) {
				cornerValid = false;
			}
		}
		if(right.getPiece() != null) {
			if (right.getPiece().getPlayerID() != playerID) {
				cornerValid = false;
			}
		}

		// See if it's valid overall
		boolean canBuildRoad = edgeValid || cornerValid;



		return canBuildRoad;
	}

	/**
	 *
	 * @param playerID
	 * @param free
	 * @param loc
	 * @param upperHex
	 * @param lowerHex
     * @return
     */
	private boolean edgeSCase(int playerID, Boolean free, EdgeLocation loc, TerrainHex upperHex, TerrainHex lowerHex) {

		TerrainHex hex = hexes.get(loc.getHexLoc());


		// Check if the surrounding edges make this case valid
		Edge upperLeft = edges.get(new EdgeLocation(upperHex.getLocation(),EdgeDirection.SouthWest).getNormalizedLocation());
		Edge upperRight = edges.get(new EdgeLocation(upperHex.getLocation(),EdgeDirection.SouthEast).getNormalizedLocation());
		Edge lowerLeft = edges.get(new EdgeLocation(lowerHex.getLocation(),EdgeDirection.NorthWest).getNormalizedLocation());
		Edge lowerRight = edges.get(new EdgeLocation(lowerHex.getLocation(),EdgeDirection.NorthEast).getNormalizedLocation());

		boolean edgeValid = false;


		if(upperLeft.getPiece() != null) {
			if (upperLeft.getPiece().getPlayerID() == playerID) {
				edgeValid = true;
			}
		}
		if(upperRight.getPiece() != null) {
			if (upperRight.getPiece().getPlayerID() ==playerID) {
				edgeValid = true;
			}
		}
		if(lowerLeft.getPiece() != null) {
			if (lowerLeft.getPiece().getPlayerID() == playerID) {
				edgeValid = true;
			}
		}
		if(lowerRight.getPiece() != null) {
			if (lowerRight.getPiece().getPlayerID() == playerID) {
				edgeValid = true;
			}
		}


		// Check if the surrounding corners make this case valid
		Vertex left = verticies.get(new VertexLocation(loc.getHexLoc(), VertexDirection.NorthWest).getNormalizedLocation());
		Vertex right = verticies.get(new VertexLocation(loc.getHexLoc(), VertexDirection.NorthEast).getNormalizedLocation());

		boolean cornerValid = false;

		if(left.getPiece() != null) {
			if (left.getPiece().getPlayerID() == playerID) {
				cornerValid = true;
			}
		}
		if(right.getPiece() != null) {
			if (right.getPiece().getPlayerID() == playerID) {
				cornerValid = true;
			}
		}
		if(left.getPiece() != null) {
			if (left.getPiece().getPlayerID() != playerID) {
				cornerValid = false;
			}
		}
		if(right.getPiece() != null) {
			if (right.getPiece().getPlayerID() != playerID) {
				cornerValid = false;
			}
		}

		// See if it's valid overall
		boolean canBuildRoad = edgeValid || cornerValid;



		return canBuildRoad;
	}





	/**
	 * 
	 * Creates and assembles TerrainHex objects to be stored in a HashMap to be used throughout the game!
	 * 
	 **/
	public void buildHexes(){
		HexLocation loc1 = new HexLocation(0, -2);
		TerrainHex hex1 = new TerrainHex(loc1, HexType.WOOD, 11);
		hexes.put(loc1, hex1);

		HexLocation loc2 = new HexLocation(1, -2);
		TerrainHex hex2 = new TerrainHex(loc2, HexType.SHEEP, 12);
		hexes.put(loc2, hex2);
		
		HexLocation loc3 = new HexLocation(2, -2);
		TerrainHex hex3 = new TerrainHex(loc3, HexType.WHEAT, 9);
		hexes.put(loc3, hex3);
		
		HexLocation loc4 = new HexLocation(-1, -1);
		TerrainHex hex4 = new TerrainHex(loc4, HexType.BRICK, 4);
		hexes.put(loc4, hex4);
		
		HexLocation loc5 = new HexLocation(0, -1);
		TerrainHex hex5 = new TerrainHex(loc5, HexType.ORE, 6);
		hexes.put(loc5, hex5);
		
		HexLocation loc6 = new HexLocation(1, -1);
		TerrainHex hex6 = new TerrainHex(loc6, HexType.BRICK, 5);
		hexes.put(loc6, hex6);
		
		HexLocation loc7 = new HexLocation(2, -1);
		TerrainHex hex7 = new TerrainHex(loc7,HexType.SHEEP, 10);
		hexes.put(loc7, hex7);
		
		HexLocation loc8 = new HexLocation(-2, 0);
		TerrainHex hex8 = new TerrainHex(loc8, HexType.DESERT, 0);
		hexes.put(loc8, hex8);
		
		HexLocation loc9 = new HexLocation(-1, 0);
		TerrainHex hex9 = new TerrainHex(loc9, HexType.WOOD, 3);
		hexes.put(loc9, hex9);
		
		HexLocation loc10 = new HexLocation(0, 0);
		TerrainHex hex10 = new TerrainHex(loc10, HexType.WHEAT, 11);
		hexes.put(loc10, hex10);
		
		HexLocation loc11 = new HexLocation(1, 0);
		TerrainHex hex11 = new TerrainHex(loc11, HexType.WOOD, 4);
		hexes.put(loc11, hex11);
		
		HexLocation loc12 = new HexLocation(2, 0);
		TerrainHex hex12 = new TerrainHex(loc12, HexType.WHEAT, 8);
		hexes.put(loc12, hex12);
		
		HexLocation loc13 = new HexLocation(-2, 1);
		TerrainHex hex13 = new TerrainHex(loc13, HexType.BRICK, 8);
		hexes.put(loc13, hex13);
		
		HexLocation loc14 = new HexLocation(-1, 1);
		TerrainHex hex14 = new TerrainHex(loc14, HexType.SHEEP, 10);
		hexes.put(loc14, hex14);
		
		HexLocation loc15 = new HexLocation(0, 1);
		TerrainHex hex15 = new TerrainHex(loc15, HexType.SHEEP, 9);
		hexes.put(loc15, hex15);
		
		HexLocation loc16 = new HexLocation(1, 1);
		TerrainHex hex16 = new TerrainHex(loc16, HexType.ORE, 3);
		hexes.put(loc16, hex16);
		
		HexLocation loc17 = new HexLocation(-2, 2);
		TerrainHex hex17 = new TerrainHex(loc17, HexType.ORE, 5);
		hexes.put(loc17, hex17);
		
		HexLocation loc18 = new HexLocation(-1, 2);
		TerrainHex hex18 = new TerrainHex(loc18, HexType.WHEAT, 2);
		hexes.put(loc18, hex18);
		
		HexLocation loc19 = new HexLocation(0, 2);
		TerrainHex hex19 = new TerrainHex(loc19, HexType.WOOD, 6);
		hexes.put(loc19, hex19);
		
		
	}
	
	/**
	 * adds Vertex objects to the correct TerrainHex (multiple Hexes can and will share a Vertex)
	 */
	public void buildVerticies(){
		for(Entry<HexLocation, TerrainHex> entry : hexes.entrySet()){
			
			VertexLocation vertLoc1 = new VertexLocation(entry.getKey(), VertexDirection.NorthWest);
			Vertex v1 = new Vertex(vertLoc1);
			verticies.put(vertLoc1, v1);
			
			VertexLocation vertLoc2 = new VertexLocation(entry.getKey(), VertexDirection.NorthEast);
			Vertex v2 = new Vertex(vertLoc2);
			verticies.put(vertLoc2, v2);
			
			VertexLocation vertLoc3 = new VertexLocation(entry.getKey(), VertexDirection.East);
			Vertex v3 = new Vertex(vertLoc3);
			verticies.put(vertLoc3, v3);
			
			VertexLocation vertLoc4 = new VertexLocation(entry.getKey(), VertexDirection.SouthEast);
			Vertex v4 = new Vertex(vertLoc4);
			verticies.put(vertLoc4, v4);
			
			VertexLocation vertLoc5 = new VertexLocation(entry.getKey(), VertexDirection.SouthWest);
			Vertex v5 = new Vertex(vertLoc5);
			verticies.put(vertLoc5, v5);
			
			VertexLocation vertLoc6 = new VertexLocation(entry.getKey(), VertexDirection.West);
			Vertex v6 = new Vertex(vertLoc6);
			verticies.put(vertLoc6, v6);
			
			
		}
	}
	
	/**
	 * adds Edge objects to the correct TerrainHex (multiple hexes can and will share an edge)
	 */
	public void buildEdges(){
		for(Entry<HexLocation, TerrainHex> entry : hexes.entrySet()){
			
			EdgeLocation edgeLoc1 = new EdgeLocation(entry.getKey(), EdgeDirection.NorthWest);
			Edge edge1 = new Edge(edgeLoc1);
			edges.put(edgeLoc1, edge1);
			
			EdgeLocation edgeLoc2 = new EdgeLocation(entry.getKey(), EdgeDirection.North);
			Edge edge2 = new Edge(edgeLoc2);
			edges.put(edgeLoc2, edge2);
			
			EdgeLocation edgeLoc3 = new EdgeLocation(entry.getKey(), EdgeDirection.NorthEast);
			Edge edge3 = new Edge(edgeLoc3);
			edges.put(edgeLoc3, edge3);
			
			EdgeLocation edgeLoc4 = new EdgeLocation(entry.getKey(), EdgeDirection.SouthEast);
			Edge edge4 = new Edge(edgeLoc4);
			edges.put(edgeLoc4, edge4);
			
			EdgeLocation edgeLoc5 = new EdgeLocation(entry.getKey(), EdgeDirection.South);
			Edge edge5 = new Edge(edgeLoc5);
			edges.put(edgeLoc5, edge5);
			
			EdgeLocation edgeLoc6 = new EdgeLocation(entry.getKey(), EdgeDirection.SouthWest);
			Edge edge6 = new Edge(edgeLoc6);
			edges.put(edgeLoc6, edge6);
		}
	}
	
	/**
	 * adds Port objects to the correct Vertex objects
	 */
	public void addPorts(){
		
	}

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


	public boolean canRoadBuild(int playerID, EdgeLocation spot1, EdgeLocation spot2) {
		boolean firstPossible = false;
		boolean secondPossible = false;

		// First case
		if(canBuildRoad(playerID, false, spot1)) {
			Edge tempEdge = edges.get(spot1);
			Piece temp = new Piece(PieceType.ROAD, tempEdge, null, playerID);
			edges.get(spot1).setPiece(temp);
			if(canBuildRoad(playerID, false, spot2)) {
				firstPossible = true;
			}
			edges.get(spot1).setPiece(null);
		}


		// Second case
		if(canBuildRoad(playerID, false, spot2)) {
			Edge tempEdge = edges.get(spot2);
			Piece temp = new Piece(PieceType.ROAD, tempEdge, null, playerID);
			edges.get(spot1).setPiece(temp);
			if(canBuildRoad(playerID, false, spot1)) {
				secondPossible = true;
			}
			edges.get(spot2).setPiece(null);
		}



		return firstPossible || secondPossible;
	}
}
