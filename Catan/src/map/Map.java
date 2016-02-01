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

	private HashMap<HexLocation, TerrainHex> hexes;
	private HashMap<EdgeLocation, Edge> edges;
	private HashMap<VertexLocation, Vertex> verticies;
	private ArrayList<Player> players;
	
	public Map(ArrayList<Player> players) {
		this.players = players;
		hexes = new HashMap<HexLocation, TerrainHex>();
		
		buildHexes();
	}
	
	
	
	
	//functions i wrotedsf
		/**
		 * @return true if you can build a city here (settlement already here)j
		 */
		public boolean canBuildCity(int playerID, VertexLocation loc) {
			Piece tempPiece = verticies.get(loc).getPiece();

			// Not valid if there's no piece there already
			if(tempPiece != null) {
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
		public boolean canBuildSettlement(int playerID, boolean free, VertexLocation loc) {
			if(verticies.get(loc).getPiece() != null) {
				return false;
			}

			Player p = players.get(playerID);

			switch(loc.getDir()) {
				case East: vertexECase(p, free, loc); break;
				case West: vertexWCase(p, free, loc); break;
				case NorthEast: vertexNECase(p, free, loc); break;
				case SouthEast: vertexSECase(p, free, loc); break;
				case NorthWest: vertexNWCase(p, free, loc); break;
				case SouthWest: vertexSWCase(p, free, loc); break;
				default: System.out.println("Error! VertexLocation doesn't exist!");
			}

			System.out.println("Error! It should never come here!");
			return false;
		}


		/**
		 *
		 * @param p
		 * @param free
		 * @param loc
		 * @return
		 */
		public boolean vertexECase(Player p, boolean free, VertexLocation loc) {
			TerrainHex currentHex = hexes.get(loc.getHexLoc());
			TerrainHex upperRightHex = hexes.get(loc.getHexLoc().getNeighborLoc(EdgeDirection.NorthEast));

			// Check if the edges are valid
			Edge rightEdge = upperRightHex.getEdges().get(new EdgeLocation(upperRightHex.getLocation(), EdgeDirection.South));
			Edge upEdge = currentHex.getEdges().get(new EdgeLocation(currentHex.getLocation(), EdgeDirection.NorthEast));
			Edge downEdge = currentHex.getEdges().get(new EdgeLocation(currentHex.getLocation(), EdgeDirection.SouthEast));

			boolean edgeValid = true;
			if(rightEdge.getPiece() != null || upEdge.getPiece() != null || downEdge.getPiece() != null) {
				return false;
			}

			// Check if the corners are valid
			Vertex rightCorner = upperRightHex.getVerticies().get(new VertexLocation(upperRightHex.getLocation(), VertexDirection.SouthEast));
			Vertex upCorner = currentHex.getVerticies().get(new VertexLocation(upperRightHex.getLocation(), VertexDirection.NorthEast));
			Vertex downCorner = currentHex.getVerticies().get(new VertexLocation(currentHex.getLocation(), VertexDirection.SouthEast));

			boolean cornerValid = false;
			if(rightCorner.getPiece().getPlayerID() == p.getPlayerID() ||
					upCorner.getPiece().getPlayerID() == p.getPlayerID() ||
					downCorner.getPiece().getPlayerID() == p.getPlayerID()) {
				cornerValid = true;
			}



			// Check if it's valid
			boolean canBuild = edgeValid && cornerValid;

			return canBuild;
		}

	/**
	 *
	 * @param p
	 * @param free
	 * @param loc
     * @return
     */
		public boolean vertexWCase(Player p, boolean free, VertexLocation loc) {
			TerrainHex currentHex = hexes.get(loc.getHexLoc());
			TerrainHex upperLeftHex = hexes.get(loc.getHexLoc().getNeighborLoc(EdgeDirection.NorthWest));

			// Check if the edges are valid
			Edge leftEdge = upperLeftHex.getEdges().get(new EdgeLocation(upperLeftHex.getLocation(), EdgeDirection.South));
			Edge upEdge = currentHex.getEdges().get(new EdgeLocation(currentHex.getLocation(), EdgeDirection.NorthWest));
			Edge downEdge = currentHex.getEdges().get(new EdgeLocation(currentHex.getLocation(), EdgeDirection.SouthWest));

			boolean edgeValid = true;
			if(leftEdge.getPiece() != null || upEdge.getPiece() != null || downEdge.getPiece() != null) {
				return false;
			}

			// Check if the corners are valid
			Vertex upCorner = currentHex.getVerticies().get(new VertexLocation(currentHex.getLocation(), VertexDirection.NorthWest));
			Vertex leftCorner = upperLeftHex.getVerticies().get(new VertexLocation(upperLeftHex.getLocation(), VertexDirection.SouthWest));
			Vertex downCorner = currentHex.getVerticies().get(new VertexLocation(currentHex.getLocation(), VertexDirection.SouthWest));

			boolean cornerValid = false;
			if(leftCorner.getPiece().getPlayerID() == p.getPlayerID() ||
					upCorner.getPiece().getPlayerID() == p.getPlayerID() ||
					downCorner.getPiece().getPlayerID() == p.getPlayerID()) {
				cornerValid = true;
			}



			// Check if it's valid
			boolean canBuild = edgeValid && cornerValid;

			return canBuild;
		}

	/**
	 *
	 * @param p
	 * @param free
	 * @param loc
     * @return
     */
		public boolean vertexNECase(Player p, boolean free, VertexLocation loc) {
			TerrainHex currentHex = hexes.get(loc.getHexLoc());
			TerrainHex upperHex = hexes.get(loc.getHexLoc().getNeighborLoc(EdgeDirection.North));

			// Check if the edges are valid
			Edge leftEdge = currentHex.getEdges().get(new EdgeLocation(currentHex.getLocation(), EdgeDirection.North));
			Edge upEdge = upperHex.getEdges().get(new EdgeLocation(upperHex.getLocation(), EdgeDirection.SouthEast));
			Edge downEdge = currentHex.getEdges().get(new EdgeLocation(currentHex.getLocation(), EdgeDirection.NorthEast));

			boolean edgeValid = true;
			if(leftEdge.getPiece() != null || upEdge.getPiece() != null || downEdge.getPiece() != null) {
				return false;
			}

			// Check if the corners are valid
			Vertex upCorner = upperHex.getVerticies().get(new VertexLocation(upperHex.getLocation(), VertexDirection.East));
			Vertex leftCorner = currentHex.getVerticies().get(new VertexLocation(currentHex.getLocation(), VertexDirection.NorthWest));
			Vertex downCorner = currentHex.getVerticies().get(new VertexLocation(currentHex.getLocation(), VertexDirection.East));

			boolean cornerValid = false;
			if(leftCorner.getPiece().getPlayerID() == p.getPlayerID() ||
					upCorner.getPiece().getPlayerID() == p.getPlayerID() ||
					downCorner.getPiece().getPlayerID() == p.getPlayerID()) {
				cornerValid = true;
			}



			// Check if it's valid
			boolean canBuild = edgeValid && cornerValid;

			return canBuild;
		}

	/**
	 *
	 * @param p
	 * @param free
	 * @param loc
     * @return
     */
		public boolean vertexSECase(Player p, boolean free, VertexLocation loc) {
			TerrainHex currentHex = hexes.get(loc.getHexLoc());
			TerrainHex lowerHex = hexes.get(loc.getHexLoc().getNeighborLoc(EdgeDirection.South));

			// Check if the edges are valid
			Edge leftEdge = currentHex.getEdges().get(new EdgeLocation(currentHex.getLocation(), EdgeDirection.South));
			Edge upEdge = currentHex.getEdges().get(new EdgeLocation(currentHex.getLocation(), EdgeDirection.SouthEast));
			Edge downEdge = lowerHex.getEdges().get(new EdgeLocation(lowerHex.getLocation(), EdgeDirection.NorthEast));

			boolean edgeValid = true;
			if(leftEdge.getPiece() != null || upEdge.getPiece() != null || downEdge.getPiece() != null) {
				return false;
			}

			// Check if the corners are valid
			Vertex upCorner = currentHex.getVerticies().get(new VertexLocation(currentHex.getLocation(), VertexDirection.East));
			Vertex leftCorner = currentHex.getVerticies().get(new VertexLocation(currentHex.getLocation(), VertexDirection.SouthWest));
			Vertex downCorner = currentHex.getVerticies().get(new VertexLocation(currentHex.getLocation(), VertexDirection.East));

			boolean cornerValid = false;
			if(leftCorner.getPiece().getPlayerID() == p.getPlayerID() ||
					upCorner.getPiece().getPlayerID() == p.getPlayerID() ||
					downCorner.getPiece().getPlayerID() == p.getPlayerID()) {
				cornerValid = true;
			}



			// Check if it's valid
			boolean canBuild = edgeValid && cornerValid;

			return canBuild;

		}

	/**
	 *
	 * @param p
	 * @param free
	 * @param loc
     * @return
     */
		public boolean vertexNWCase(Player p, boolean free, VertexLocation loc) {
			TerrainHex currentHex = hexes.get(loc.getHexLoc());
			TerrainHex upperHex = hexes.get(loc.getHexLoc().getNeighborLoc(EdgeDirection.North));

			// Check if the edges are valid
			Edge rightEdge = currentHex.getEdges().get(new EdgeLocation(currentHex.getLocation(), EdgeDirection.North));
			Edge upEdge = upperHex.getEdges().get(new EdgeLocation(upperHex.getLocation(), EdgeDirection.NorthEast));
			Edge downEdge = currentHex.getEdges().get(new EdgeLocation(currentHex.getLocation(), EdgeDirection.NorthWest));

			boolean edgeValid = true;
			if(rightEdge.getPiece() != null || upEdge.getPiece() != null || downEdge.getPiece() != null) {
				return false;
			}

			// Check if the corners are valid
			Vertex upCorner = upperHex.getVerticies().get(new VertexLocation(upperHex.getLocation(), VertexDirection.NorthEast));
			Vertex rightCorner = currentHex.getVerticies().get(new VertexLocation(currentHex.getLocation(), VertexDirection.NorthEast));
			Vertex downCorner = currentHex.getVerticies().get(new VertexLocation(currentHex.getLocation(), VertexDirection.West));

			boolean cornerValid = false;
			if(rightCorner.getPiece().getPlayerID() == p.getPlayerID() ||
					upCorner.getPiece().getPlayerID() == p.getPlayerID() ||
					downCorner.getPiece().getPlayerID() == p.getPlayerID()) {
				cornerValid = true;
			}



			// Check if it's valid
			boolean canBuild = edgeValid && cornerValid;

			return canBuild;
		}

		public boolean vertexSWCase(Player p, boolean free, VertexLocation loc) { // Not done fix this
			TerrainHex currentHex = hexes.get(loc.getHexLoc());
			TerrainHex lowerHex = hexes.get(loc.getHexLoc().getNeighborLoc(EdgeDirection.South));

			// Check if the edges are valid
			Edge rightEdge = currentHex.getEdges().get(new EdgeLocation(currentHex.getLocation(), EdgeDirection.South));
			Edge upEdge = currentHex.getEdges().get(new EdgeLocation(currentHex.getLocation(), EdgeDirection.SouthWest));
			Edge downEdge = lowerHex.getEdges().get(new EdgeLocation(lowerHex.getLocation(), EdgeDirection.NorthWest));

			boolean edgeValid = true;
			if(rightEdge.getPiece() != null || upEdge.getPiece() != null || downEdge.getPiece() != null) {
				return false;
			}

			// Check if the corners are valid
			Vertex upCorner = currentHex.getVerticies().get(new VertexLocation(currentHex.getLocation(), VertexDirection.West));
			Vertex rightCorner = currentHex.getVerticies().get(new VertexLocation(currentHex.getLocation(), VertexDirection.SouthEast));
			Vertex downCorner = lowerHex.getVerticies().get(new VertexLocation(lowerHex.getLocation(), VertexDirection.West));

			boolean cornerValid = false;
			if(rightCorner.getPiece().getPlayerID() == p.getPlayerID() ||
					upCorner.getPiece().getPlayerID() == p.getPlayerID() ||
					downCorner.getPiece().getPlayerID() == p.getPlayerID()) {
				cornerValid = true;
			}



			// Check if it's valid
			boolean canBuild = edgeValid && cornerValid;

			return canBuild;
		}



	/**
		 * 
		 * @return true if this is a valid location for a road (no road currently at this location)
		 */
		public boolean canBuildRoad(int playerID, boolean free, EdgeLocation loc) {
			if(edges.get(loc).getPiece() != null) {
				return false;
			}

			Player p = players.get(playerID);

			TerrainHex hex = hexes.get(loc.getHexLoc());
			TerrainHex neighbor = hexes.get(hex.getLocation().getNeighborLoc(loc.getDir()));

			switch(loc.getDir()) {
				case NorthWest: edgeNWCase(p, free, loc, hex, neighbor); break;
				case North: edgeNCase(p, free, loc, hex, neighbor); break;
				case NorthEast: edgeNECase(p, free, loc, hex, neighbor); break;
				case SouthEast: edgeSECase(p, free, loc, hex, neighbor); break;
				case South: edgeSCase(p, free, loc, hex, neighbor); break;
				case SouthWest: edgeSWCase(p, free, loc, hex, neighbor); break;


				default: System.out.println("Error! The EdgeLocation doesn't exist!");
			}
			System.out.println("Error! Shouldn't ever reach here!");
			return false;
		}


	/**
	 *
	 * @param p
	 * @param free
	 * @param loc
	 * @param lowerRightHex
	 * @param upperLeftHex
     * @return
     */
	private boolean edgeNWCase(Player p, boolean free, EdgeLocation loc, TerrainHex lowerRightHex, TerrainHex upperLeftHex) {
		TerrainHex hex = hexes.get(loc.getHexLoc());
		HashMap<EdgeLocation, Edge> edges = hex.getEdges();
		HashMap<VertexLocation, Vertex> corners = hex.getVerticies();


		// Check if the surrounding edges make this case valid
		Edge upperLeft = upperLeftHex.getEdges().get(new EdgeLocation(upperLeftHex.getLocation(),EdgeDirection.South));
		Edge upperRight = upperLeftHex.getEdges().get(new EdgeLocation(upperLeftHex.getLocation(),EdgeDirection.NorthEast));
		Edge lowerLeft = lowerRightHex.getEdges().get(new EdgeLocation(lowerRightHex.getLocation(),EdgeDirection.SouthWest));
		Edge lowerRight = lowerRightHex.getEdges().get(new EdgeLocation(lowerRightHex.getLocation(),EdgeDirection.North));

		boolean edgeValid = false;


		if(upperLeft.getPiece() != null) {
			if (upperLeft.getPiece().getPlayerID() == p.getPlayerID()) {
				edgeValid = true;
			}
		}
		if(upperRight.getPiece() != null) {
			if (upperRight.getPiece().getPlayerID() == p.getPlayerID()) {
				edgeValid = true;
			}
		}
		if(lowerLeft.getPiece() != null) {
			if (lowerLeft.getPiece().getPlayerID() == p.getPlayerID()) {
				edgeValid = true;
			}
		}
		if(lowerRight.getPiece() != null) {
			if (lowerRight.getPiece().getPlayerID() == p.getPlayerID()) {
				edgeValid = true;
			}
		}


		// Check if the surrounding corners make this case valid
		Vertex left = corners.get(new VertexLocation(loc.getHexLoc(), VertexDirection.NorthWest));
		Vertex right = corners.get(new VertexLocation(loc.getHexLoc(), VertexDirection.NorthEast));

		boolean cornerValid = false;

		if(left.getPiece() != null) {
			if (left.getPiece().getPlayerID() == p.getPlayerID()) {
				cornerValid = true;
			}
		}
		if(right.getPiece() != null) {
			if (right.getPiece().getPlayerID() == p.getPlayerID()) {
				cornerValid = true;
			}
		}
		if(left.getPiece() != null) {
			if (left.getPiece().getPlayerID() != p.getPlayerID()) {
				cornerValid = false;
			}
		}
		if(right.getPiece() != null) {
			if (right.getPiece().getPlayerID() != p.getPlayerID()) {
				cornerValid = false;
			}
		}

		// See if it's valid overall
		boolean canBuildRoad = edgeValid && cornerValid;



		return canBuildRoad;
	}

	/**
	 *
	 * @param p
	 * @param free
	 * @param loc
	 * @param lowerHex
	 * @param upperHex
     * @return
     */
	private boolean edgeNCase(Player p, boolean free, EdgeLocation loc, TerrainHex lowerHex, TerrainHex upperHex) {
		TerrainHex hex = hexes.get(loc.getHexLoc());
		HashMap<EdgeLocation, Edge> edges = hex.getEdges();
		HashMap<VertexLocation, Vertex> corners = hex.getVerticies();


		// Check if the surrounding edges make this case valid
		Edge upperLeft = upperHex.getEdges().get(new EdgeLocation(upperHex.getLocation(),EdgeDirection.SouthWest));
		Edge upperRight = upperHex.getEdges().get(new EdgeLocation(upperHex.getLocation(),EdgeDirection.SouthEast));
		Edge lowerLeft = lowerHex.getEdges().get(new EdgeLocation(lowerHex.getLocation(),EdgeDirection.NorthWest));
		Edge lowerRight = lowerHex.getEdges().get(new EdgeLocation(lowerHex.getLocation(),EdgeDirection.NorthEast));

		boolean edgeValid = false;


		if(upperLeft.getPiece() != null) {
			if (upperLeft.getPiece().getPlayerID() == p.getPlayerID()) {
				edgeValid = true;
			}
		}
		if(upperRight.getPiece() != null) {
			if (upperRight.getPiece().getPlayerID() == p.getPlayerID()) {
				edgeValid = true;
			}
		}
		if(lowerLeft.getPiece() != null) {
			if (lowerLeft.getPiece().getPlayerID() == p.getPlayerID()) {
				edgeValid = true;
			}
		}
		if(lowerRight.getPiece() != null) {
			if (lowerRight.getPiece().getPlayerID() == p.getPlayerID()) {
				edgeValid = true;
			}
		}


		// Check if the surrounding corners make this case valid
		Vertex left = corners.get(new VertexLocation(loc.getHexLoc(), VertexDirection.West));
		Vertex right = corners.get(new VertexLocation(loc.getHexLoc(), VertexDirection.East));

		boolean cornerValid = false;

		if(left.getPiece() != null) {
			if (left.getPiece().getPlayerID() == p.getPlayerID()) {
				cornerValid = true;
			}
		}
		if(right.getPiece() != null) {
			if (right.getPiece().getPlayerID() == p.getPlayerID()) {
				cornerValid = true;
			}
		}
		if(left.getPiece() != null) {
			if (left.getPiece().getPlayerID() != p.getPlayerID()) {
				cornerValid = false;
			}
		}
		if(right.getPiece() != null) {
			if (right.getPiece().getPlayerID() != p.getPlayerID()) {
				cornerValid = false;
			}
		}

		// See if it's valid overall
		boolean canBuildRoad = edgeValid && cornerValid;



		return canBuildRoad;
	}

	/**
	 *
	 * @param p
	 * @param free
	 * @param loc
	 * @param lowerLeftHex
	 * @param upperRightHex
     * @return
     */
	private boolean edgeNECase(Player p, boolean free, EdgeLocation loc, TerrainHex lowerLeftHex, TerrainHex upperRightHex) {
		TerrainHex hex = hexes.get(loc.getHexLoc());
		HashMap<EdgeLocation, Edge> edges = hex.getEdges();
		HashMap<VertexLocation, Vertex> corners = hex.getVerticies();


		// Check if the surrounding edges make this case valid
		Edge upperLeft = upperRightHex.getEdges().get(new EdgeLocation(upperRightHex.getLocation(),EdgeDirection.NorthWest));
		Edge upperRight = upperRightHex.getEdges().get(new EdgeLocation(upperRightHex.getLocation(),EdgeDirection.South));
		Edge lowerLeft = lowerLeftHex.getEdges().get(new EdgeLocation(lowerLeftHex.getLocation(),EdgeDirection.North));
		Edge lowerRight = lowerLeftHex.getEdges().get(new EdgeLocation(lowerLeftHex.getLocation(),EdgeDirection.SouthEast));

		boolean edgeValid = false;


		if(upperLeft.getPiece() != null) {
			if (upperLeft.getPiece().getPlayerID() == p.getPlayerID()) {
				edgeValid = true;
			}
		}
		if(upperRight.getPiece() != null) {
			if (upperRight.getPiece().getPlayerID() == p.getPlayerID()) {
				edgeValid = true;
			}
		}
		if(lowerLeft.getPiece() != null) {
			if (lowerLeft.getPiece().getPlayerID() == p.getPlayerID()) {
				edgeValid = true;
			}
		}
		if(lowerRight.getPiece() != null) {
			if (lowerRight.getPiece().getPlayerID() == p.getPlayerID()) {
				edgeValid = true;
			}
		}


		// Check if the surrounding corners make this case valid
		Vertex left = corners.get(new VertexLocation(loc.getHexLoc(), VertexDirection.NorthEast));
		Vertex right = corners.get(new VertexLocation(loc.getHexLoc(), VertexDirection.East));

		boolean cornerValid = false;

		if(left.getPiece() != null) {
			if (left.getPiece().getPlayerID() == p.getPlayerID()) {
				cornerValid = true;
			}
		}
		if(right.getPiece() != null) {
			if (right.getPiece().getPlayerID() == p.getPlayerID()) {
				cornerValid = true;
			}
		}
		if(left.getPiece() != null) {
			if (left.getPiece().getPlayerID() != p.getPlayerID()) {
				cornerValid = false;
			}
		}
		if(right.getPiece() != null) {
			if (right.getPiece().getPlayerID() != p.getPlayerID()) {
				cornerValid = false;
			}
		}

		// See if it's valid overall
		boolean canBuildRoad = edgeValid && cornerValid;



		return canBuildRoad;
	}

	/**
	 *
	 * @param p
	 * @param free
	 * @param loc
	 * @param upperLeftHex
	 * @param lowerRightHex
     * @return
     */
	private boolean edgeSECase(Player p, boolean free, EdgeLocation loc, TerrainHex upperLeftHex, TerrainHex lowerRightHex) {
		TerrainHex hex = hexes.get(loc.getHexLoc());
		HashMap<EdgeLocation, Edge> edges = hex.getEdges();
		HashMap<VertexLocation, Vertex> corners = hex.getVerticies();


		// Check if the surrounding edges make this case valid
		Edge upperLeft = upperLeftHex.getEdges().get(new EdgeLocation(upperLeftHex.getLocation(),EdgeDirection.South));
		Edge upperRight = upperLeftHex.getEdges().get(new EdgeLocation(upperLeftHex.getLocation(),EdgeDirection.NorthEast));
		Edge lowerLeft = lowerRightHex.getEdges().get(new EdgeLocation(lowerRightHex.getLocation(),EdgeDirection.SouthWest));
		Edge lowerRight = lowerRightHex.getEdges().get(new EdgeLocation(lowerRightHex.getLocation(),EdgeDirection.North));

		boolean edgeValid = false;


		if(upperLeft.getPiece() != null) {
			if (upperLeft.getPiece().getPlayerID() == p.getPlayerID()) {
				edgeValid = true;
			}
		}
		if(upperRight.getPiece() != null) {
			if (upperRight.getPiece().getPlayerID() == p.getPlayerID()) {
				edgeValid = true;
			}
		}
		if(lowerLeft.getPiece() != null) {
			if (lowerLeft.getPiece().getPlayerID() == p.getPlayerID()) {
				edgeValid = true;
			}
		}
		if(lowerRight.getPiece() != null) {
			if (lowerRight.getPiece().getPlayerID() == p.getPlayerID()) {
				edgeValid = true;
			}
		}


		// Check if the surrounding corners make this case valid
		Vertex left = corners.get(new VertexLocation(loc.getHexLoc(), VertexDirection.West));
		Vertex right = corners.get(new VertexLocation(loc.getHexLoc(), VertexDirection.NorthWest));

		boolean cornerValid = false;

		if(left.getPiece() != null) {
			if (left.getPiece().getPlayerID() == p.getPlayerID()) {
				cornerValid = true;
			}
		}
		if(right.getPiece() != null) {
			if (right.getPiece().getPlayerID() == p.getPlayerID()) {
				cornerValid = true;
			}
		}
		if(left.getPiece() != null) {
			if (left.getPiece().getPlayerID() != p.getPlayerID()) {
				cornerValid = false;
			}
		}
		if(right.getPiece() != null) {
			if (right.getPiece().getPlayerID() != p.getPlayerID()) {
				cornerValid = false;
			}
		}

		// See if it's valid overall
		boolean canBuildRoad = edgeValid && cornerValid;



		return canBuildRoad;
	}

	/**
	 *
	 * @param p
	 * @param free
	 * @param loc
	 * @param upperRightHex
	 * @param lowerLeftHex
     * @return
     */
	private boolean edgeSWCase(Player p, boolean free, EdgeLocation loc, TerrainHex upperRightHex, TerrainHex lowerLeftHex) {
		TerrainHex hex = hexes.get(loc.getHexLoc());
		HashMap<EdgeLocation, Edge> edges = hex.getEdges();
		HashMap<VertexLocation, Vertex> corners = hex.getVerticies();


		// Check if the surrounding edges make this case valid
		Edge upperLeft = upperRightHex.getEdges().get(new EdgeLocation(upperRightHex.getLocation(),EdgeDirection.NorthWest));
		Edge upperRight = upperRightHex.getEdges().get(new EdgeLocation(upperRightHex.getLocation(),EdgeDirection.South));
		Edge lowerLeft = lowerLeftHex.getEdges().get(new EdgeLocation(lowerLeftHex.getLocation(),EdgeDirection.North));
		Edge lowerRight = lowerLeftHex.getEdges().get(new EdgeLocation(lowerLeftHex.getLocation(),EdgeDirection.SouthEast));

		boolean edgeValid = false;


		if(upperLeft.getPiece() != null) {
			if (upperLeft.getPiece().getPlayerID() == p.getPlayerID()) {
				edgeValid = true;
			}
		}
		if(upperRight.getPiece() != null) {
			if (upperRight.getPiece().getPlayerID() == p.getPlayerID()) {
				edgeValid = true;
			}
		}
		if(lowerLeft.getPiece() != null) {
			if (lowerLeft.getPiece().getPlayerID() == p.getPlayerID()) {
				edgeValid = true;
			}
		}
		if(lowerRight.getPiece() != null) {
			if (lowerRight.getPiece().getPlayerID() == p.getPlayerID()) {
				edgeValid = true;
			}
		}


		// Check if the surrounding corners make this case valid
		Vertex left = corners.get(new VertexLocation(loc.getHexLoc(), VertexDirection.NorthEast));
		Vertex right = corners.get(new VertexLocation(loc.getHexLoc(), VertexDirection.East));

		boolean cornerValid = false;

		if(left.getPiece() != null) {
			if (left.getPiece().getPlayerID() == p.getPlayerID()) {
				cornerValid = true;
			}
		}
		if(right.getPiece() != null) {
			if (right.getPiece().getPlayerID() == p.getPlayerID()) {
				cornerValid = true;
			}
		}
		if(left.getPiece() != null) {
			if (left.getPiece().getPlayerID() != p.getPlayerID()) {
				cornerValid = false;
			}
		}
		if(right.getPiece() != null) {
			if (right.getPiece().getPlayerID() != p.getPlayerID()) {
				cornerValid = false;
			}
		}

		// See if it's valid overall
		boolean canBuildRoad = edgeValid && cornerValid;



		return canBuildRoad;
	}

	/**
	 *
	 * @param p
	 * @param free
	 * @param loc
	 * @param upperHex
	 * @param lowerHex
     * @return
     */
	private boolean edgeSCase(Player p, Boolean free, EdgeLocation loc, TerrainHex upperHex, TerrainHex lowerHex) {

		TerrainHex hex = hexes.get(loc.getHexLoc());
		HashMap<EdgeLocation, Edge> edges = hex.getEdges();
		HashMap<VertexLocation, Vertex> corners = hex.getVerticies();


		// Check if the surrounding edges make this case valid
		Edge upperLeft = upperHex.getEdges().get(new EdgeLocation(upperHex.getLocation(),EdgeDirection.SouthWest));
		Edge upperRight = upperHex.getEdges().get(new EdgeLocation(upperHex.getLocation(),EdgeDirection.SouthEast));
		Edge lowerLeft = lowerHex.getEdges().get(new EdgeLocation(lowerHex.getLocation(),EdgeDirection.NorthWest));
		Edge lowerRight = lowerHex.getEdges().get(new EdgeLocation(lowerHex.getLocation(),EdgeDirection.NorthEast));

		boolean edgeValid = false;


		if(upperLeft.getPiece() != null) {
			if (upperLeft.getPiece().getPlayerID() == p.getPlayerID()) {
				edgeValid = true;
			}
		}
		if(upperRight.getPiece() != null) {
			if (upperRight.getPiece().getPlayerID() == p.getPlayerID()) {
				edgeValid = true;
			}
		}
		if(lowerLeft.getPiece() != null) {
			if (lowerLeft.getPiece().getPlayerID() == p.getPlayerID()) {
				edgeValid = true;
			}
		}
		if(lowerRight.getPiece() != null) {
			if (lowerRight.getPiece().getPlayerID() == p.getPlayerID()) {
				edgeValid = true;
			}
		}


		// Check if the surrounding corners make this case valid
		Vertex left = corners.get(new VertexLocation(loc.getHexLoc(), VertexDirection.NorthWest));
		Vertex right = corners.get(new VertexLocation(loc.getHexLoc(), VertexDirection.NorthEast));

		boolean cornerValid = false;

		if(left.getPiece() != null) {
			if (left.getPiece().getPlayerID() == p.getPlayerID()) {
				cornerValid = true;
			}
		}
		if(right.getPiece() != null) {
			if (right.getPiece().getPlayerID() == p.getPlayerID()) {
				cornerValid = true;
			}
		}
		if(left.getPiece() != null) {
			if (left.getPiece().getPlayerID() != p.getPlayerID()) {
				cornerValid = false;
			}
		}
		if(right.getPiece() != null) {
			if (right.getPiece().getPlayerID() != p.getPlayerID()) {
				cornerValid = false;
			}
		}

		// See if it's valid overall
		boolean canBuildRoad = edgeValid && cornerValid;



		return canBuildRoad;
	}





	/**
	 * 
	 * Creates and assembles TerrainHex objects to be stored in a HashMap to be used throughout the game!
	 * 
	 **/
	public void buildHexes(){
		
		TerrainHex hex1 = new TerrainHex(HexType.WOOD, 11);
		HexLocation loc1 = new HexLocation(0, -2);
		hexes.put(loc1, hex1);
		
		TerrainHex hex2 = new TerrainHex(HexType.SHEEP, 12);
		HexLocation loc2 = new HexLocation(1, -2);
		hexes.put(loc2, hex2);
		
		TerrainHex hex3 = new TerrainHex(HexType.WHEAT, 9);
		HexLocation loc3 = new HexLocation(2, -2);
		hexes.put(loc3, hex3);
		
		TerrainHex hex4 = new TerrainHex(HexType.BRICK, 4);
		HexLocation loc4 = new HexLocation(-1, -1);
		hexes.put(loc4, hex4);
		
		TerrainHex hex5 = new TerrainHex(HexType.ORE, 6);
		HexLocation loc5 = new HexLocation(0, -1);
		hexes.put(loc5, hex5);
		
		TerrainHex hex6 = new TerrainHex(HexType.BRICK, 5);
		HexLocation loc6 = new HexLocation(1, -1);
		hexes.put(loc6, hex6);
		
		TerrainHex hex7 = new TerrainHex(HexType.SHEEP, 10);
		HexLocation loc7 = new HexLocation(2, -1);
		hexes.put(loc7, hex7);
		
		TerrainHex hex8 = new TerrainHex(HexType.DESERT, 0);
		HexLocation loc8 = new HexLocation(-2, 0);
		hexes.put(loc8, hex8);
		
		TerrainHex hex9 = new TerrainHex(HexType.WOOD, 3);
		HexLocation loc9 = new HexLocation(-1, 0);
		hexes.put(loc9, hex9);
		
		TerrainHex hex10 = new TerrainHex(HexType.WHEAT, 11);
		HexLocation loc10 = new HexLocation(0, 0);
		hexes.put(loc10, hex10);
		
		TerrainHex hex11 = new TerrainHex(HexType.WOOD, 4);
		HexLocation loc11 = new HexLocation(1, 0);
		hexes.put(loc11, hex11);
		
		TerrainHex hex12 = new TerrainHex(HexType.WHEAT, 8);
		HexLocation loc12 = new HexLocation(2, 0);
		hexes.put(loc12, hex12);
		
		TerrainHex hex13 = new TerrainHex(HexType.BRICK, 8);
		HexLocation loc13 = new HexLocation(-2, 1);
		hexes.put(loc13, hex13);
		
		TerrainHex hex14 = new TerrainHex(HexType.SHEEP, 10);
		HexLocation loc14 = new HexLocation(-1, 1);
		hexes.put(loc14, hex14);
		
		TerrainHex hex15 = new TerrainHex(HexType.SHEEP, 9);
		HexLocation loc15 = new HexLocation(0, 1);
		hexes.put(loc15, hex15);
		
		TerrainHex hex16 = new TerrainHex(HexType.ORE, 3);
		HexLocation loc16 = new HexLocation(1, 1);
		hexes.put(loc16, hex16);
		
		TerrainHex hex17 = new TerrainHex(HexType.ORE, 5);
		HexLocation loc17 = new HexLocation(-2, 2);
		hexes.put(loc17, hex17);
		
		TerrainHex hex18 = new TerrainHex(HexType.WHEAT, 2);
		HexLocation loc18 = new HexLocation(-1, 2);
		hexes.put(loc18, hex18);
		
		TerrainHex hex19 = new TerrainHex(HexType.WOOD, 6);
		HexLocation loc19 = new HexLocation(0, 2);
		hexes.put(loc19, hex19);
		
		
	}
	
	/**
	 * adds Vertex objects to the correct TerrainHex (multiple Hexes can and will share a Vertex)
	 */
	public void addCorners(){
		
	}
	
	/**
	 * adds Edge objects to the correct TerrainHex (multiple hexes can and will share an edge)
	 */
	public void addEdges(){
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


	public boolean canRoadBuild(EdgeLocation spot1, EdgeLocation spot2) {
		return false;
	}
}
