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
import shared.definitions.PortType;
import shared.locations.*;

public class Map {
	private HashMap<HexLocation, TerrainHex> hexes = new HashMap<HexLocation, TerrainHex>();
	private HashMap<EdgeLocation, Edge> edges = new HashMap<EdgeLocation, Edge>();
	private HashMap<VertexLocation, Vertex> verticies = new HashMap<VertexLocation, Vertex>();
	private HexLocation robberLocation;
	private HashMap<VertexLocation, Port> ports;
	HashMap<EdgeLocation,PortType> edgePorts = new HashMap<>();

	// change
	// changedf
	public Map() {
		
		buildHexes();
		createWaterHexes();
		buildEdges();
		buildVerticies();
		ports = createPorts();
		placePorts(ports);
		robberLocation = new HexLocation(-2, 0);
	}
	
	public Map(HashMap<HexLocation, TerrainHex> hexes, HashMap<VertexLocation, Port> ports, HashMap<EdgeLocation,PortType> edgePorts){
		this.ports = ports;
		this.hexes = hexes;
		this.edgePorts = edgePorts;
		robberLocation = null;
		createWaterHexes();
		buildEdges();
		buildVerticies();
		placePorts(ports);

	}

	public ArrayList<Port> listPorts(int pID){
		
		ArrayList<Port> portArray = new ArrayList<Port>();
		for(Entry<VertexLocation, Vertex> entry : verticies.entrySet()) {
		
			if (entry.getValue().getPiece() != null
					&& entry.getValue().getPort() != null
					&& entry.getValue().getPiece().getPlayerID() == pID){
				portArray.add(entry.getValue().getPort());
			}
		
		}
		return portArray;
	}

	private HashMap<VertexLocation, Port> createPorts() {
		HashMap<VertexLocation,Port> ports =  new HashMap<>();

		// Port 1
		VertexLocation loc1 = new VertexLocation(new HexLocation(0,-2), VertexDirection.NorthWest).getNormalizedLocation();
		ports.put(loc1, new Port(PortType.THREE));

		VertexLocation loc2 = new VertexLocation(new HexLocation(0,-2), VertexDirection.NorthEast).getNormalizedLocation();
		ports.put(loc2, new Port(PortType.THREE));

		edgePorts.put(new EdgeLocation(new HexLocation(0,-2),EdgeDirection.North).getNormalizedLocation(),PortType.THREE);

		// Port 2
		VertexLocation loc3 = new VertexLocation(new HexLocation(2,-1), VertexDirection.NorthEast).getNormalizedLocation();
		ports.put(loc3, new Port(PortType.THREE));

		VertexLocation loc4 = new VertexLocation(new HexLocation(2,-1), VertexDirection.East).getNormalizedLocation();
		ports.put(loc4, new Port(PortType.THREE));

		edgePorts.put(new EdgeLocation(new HexLocation(2,-1),EdgeDirection.NorthEast).getNormalizedLocation(),PortType.THREE);


		// Port 3
		VertexLocation loc5 = new VertexLocation(new HexLocation(2,0), VertexDirection.East).getNormalizedLocation();
		ports.put(loc5, new Port(PortType.THREE));

		VertexLocation loc6 = new VertexLocation(new HexLocation(2,0), VertexDirection.SouthEast).getNormalizedLocation();
		ports.put(loc6, new Port(PortType.THREE));

		edgePorts.put(new EdgeLocation(new HexLocation(2,0),EdgeDirection.SouthEast),PortType.THREE);


		// Port 4
		VertexLocation loc7 = new VertexLocation(new HexLocation(-2,2), VertexDirection.West).getNormalizedLocation();
		ports.put(loc7, new Port(PortType.THREE));

		VertexLocation loc8 = new VertexLocation(new HexLocation(-2,2), VertexDirection.SouthWest).getNormalizedLocation();
		ports.put(loc8, new Port(PortType.THREE));

		edgePorts.put(new EdgeLocation(new HexLocation(-2,2),EdgeDirection.SouthWest),PortType.THREE);


		// Port 5
		VertexLocation loc9 = new VertexLocation(new HexLocation(-1,2), VertexDirection.SouthWest).getNormalizedLocation();
		ports.put(loc9, new Port(PortType.WOOD));

		VertexLocation loc10 = new VertexLocation(new HexLocation(-1,2), VertexDirection.SouthEast).getNormalizedLocation();
		ports.put(loc10, new Port(PortType.WOOD));

		edgePorts.put(new EdgeLocation(new HexLocation(-1,2),EdgeDirection.South).getNormalizedLocation(),PortType.WOOD);


		// Port 6
		VertexLocation loc11 = new VertexLocation(new HexLocation(1,1), VertexDirection.SouthWest).getNormalizedLocation();
		ports.put(loc11, new Port(PortType.BRICK));

		VertexLocation loc12 = new VertexLocation(new HexLocation(1,1), VertexDirection.SouthEast).getNormalizedLocation();
		ports.put(loc12, new Port(PortType.BRICK));

		edgePorts.put(new EdgeLocation(new HexLocation(1,1),EdgeDirection.South),PortType.BRICK);


		// Port 7
		VertexLocation loc13 = new VertexLocation(new HexLocation(1,-2), VertexDirection.NorthEast).getNormalizedLocation();
		ports.put(loc13, new Port(PortType.SHEEP));

		VertexLocation loc14 = new VertexLocation(new HexLocation(1,-2), VertexDirection.NorthWest).getNormalizedLocation();
		ports.put(loc14, new Port(PortType.SHEEP));

		edgePorts.put(new EdgeLocation(new HexLocation(1,-2),EdgeDirection.NorthEast),PortType.SHEEP);


		// Port 8
		VertexLocation loc15 = new VertexLocation(new HexLocation(-2,1), VertexDirection.NorthWest).getNormalizedLocation();
		ports.put(loc15, new Port(PortType.WHEAT));

		VertexLocation loc16 = new VertexLocation(new HexLocation(-2,1), VertexDirection.West).getNormalizedLocation();
		ports.put(loc16, new Port(PortType.WHEAT));

		edgePorts.put(new EdgeLocation(new HexLocation(-2,1),EdgeDirection.NorthWest),PortType.WHEAT);


		// Port 9
		VertexLocation loc17 = new VertexLocation(new HexLocation(-1,-1), VertexDirection.NorthWest).getNormalizedLocation();
		ports.put(loc17, new Port(PortType.ORE));

		VertexLocation loc18 = new VertexLocation(new HexLocation(-1,-1), VertexDirection.West).getNormalizedLocation();
		ports.put(loc18, new Port(PortType.ORE));

		edgePorts.put(new EdgeLocation(new HexLocation(-1,-1),EdgeDirection.NorthWest),PortType.ORE);



		return ports;
	}

	/**
	 * Places the ports
	 * @param ports
     */
	private void placePorts(HashMap<VertexLocation, Port> ports) {
		for(Entry<VertexLocation, Port> entry : ports.entrySet()) {
			VertexLocation loc = entry.getKey().getNormalizedLocation();
			Vertex vert = verticies.get(loc);
			vert.setPort(entry.getValue());
		}
	}
	
	//functions i wrotedsf
		/**
		 * @return true if you can build a city here (settlement already here)j
		 */
		public boolean canBuildCity(int playerID, VertexLocation loc) {
			Piece tempPiece = verticies.get(loc.getNormalizedLocation()).getPiece();

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
			if(hexes.get(loc.getNormalizedLocation().getHexLoc()) == null){
				return false;
			}
			if(verticies.get(loc.getNormalizedLocation()).getPiece() != null) {
				return false;
			}



			switch(loc.getNormalizedLocation().getDir()) {
				case East: return vertexECase(playerID, setUp, loc.getNormalizedLocation());
				case West: return vertexWCase(playerID, setUp, loc.getNormalizedLocation());
				case NorthEast: return vertexNECase(playerID, setUp, loc.getNormalizedLocation());
				case SouthEast: return vertexSECase(playerID, setUp, loc.getNormalizedLocation());
				case NorthWest: return vertexNWCase(playerID, setUp, loc.getNormalizedLocation());
				case SouthWest: return vertexSWCase(playerID, setUp, loc.getNormalizedLocation());
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
			if (upperRightHex == null){
				return false;
			}

			// Check if the corners are valid
			Vertex rightCorner = verticies.get(new VertexLocation(upperRightHex.getLocation(), VertexDirection.SouthEast).getNormalizedLocation());
			Vertex upCorner = verticies.get(new VertexLocation(upperRightHex.getLocation(), VertexDirection.NorthEast).getNormalizedLocation());
			Vertex downCorner = verticies.get(new VertexLocation(currentHex.getLocation(), VertexDirection.SouthEast).getNormalizedLocation());


			if(rightCorner != null && rightCorner.getPiece() != null 
					|| upCorner != null && upCorner.getPiece() != null 
					|| downCorner != null && downCorner.getPiece() != null) {
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
			if(upperLeftHex == null) {
				return false;
			}

			// Check if the corners are valid
			Vertex upCorner = verticies.get(new VertexLocation(currentHex.getLocation(), VertexDirection.NorthWest).getNormalizedLocation());
			Vertex leftCorner = verticies.get(new VertexLocation(upperLeftHex.getLocation(), VertexDirection.SouthWest).getNormalizedLocation());
			Vertex downCorner = verticies.get(new VertexLocation(currentHex.getLocation(), VertexDirection.SouthWest).getNormalizedLocation());


			if(leftCorner != null && leftCorner.getPiece() != null ||
					upCorner != null && upCorner.getPiece() != null ||
					downCorner != null && downCorner.getPiece() != null) {
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
			if(upperHex == null) {
				return false;
			}
			TerrainHex upperRightHex = hexes.get(loc.getHexLoc().getNeighborLoc(EdgeDirection.NorthEast));
			if (upperRightHex == null){
				return false;
			}

			// Check if the corners are valid
			Vertex upCorner = verticies.get(new VertexLocation(upperHex.getLocation(), VertexDirection.East).getNormalizedLocation());
			Vertex leftCorner = verticies.get(new VertexLocation(currentHex.getLocation(), VertexDirection.NorthWest).getNormalizedLocation());
			Vertex downCorner = verticies.get(new VertexLocation(currentHex.getLocation(), VertexDirection.East).getNormalizedLocation());


			if(leftCorner != null && leftCorner.getPiece() != null ||
					upCorner != null && upCorner.getPiece() != null ||
					downCorner != null && downCorner.getPiece() != null) {
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

			if(lowerHex == null) {
				return false;
			}

			// Check if the corners are valid
			Vertex upCorner = verticies.get(new VertexLocation(currentHex.getLocation(), VertexDirection.East).getNormalizedLocation());
			Vertex leftCorner = verticies.get(new VertexLocation(currentHex.getLocation(), VertexDirection.SouthWest).getNormalizedLocation());
			Vertex downCorner = verticies.get(new VertexLocation(lowerHex.getLocation(), VertexDirection.East).getNormalizedLocation());


			if(leftCorner != null && leftCorner.getPiece() != null ||
					upCorner != null && upCorner.getPiece() != null ||
					downCorner != null && downCorner.getPiece() != null) {
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

			if(upperHex == null) {
				return false;
			}

			TerrainHex upperLeftHex = hexes.get(loc.getHexLoc().getNeighborLoc(EdgeDirection.NorthWest));
			if(upperLeftHex == null) {
				return false;
			}

			// Check if the corners are valid
			Vertex upCorner = verticies.get(new VertexLocation(upperHex.getLocation(), VertexDirection.West).getNormalizedLocation());
			Vertex rightCorner = verticies.get(new VertexLocation(currentHex.getLocation(), VertexDirection.NorthEast).getNormalizedLocation());
			Vertex downCorner = verticies.get(new VertexLocation(currentHex.getLocation(), VertexDirection.West).getNormalizedLocation());


			if(rightCorner != null && rightCorner.getPiece() != null || upCorner != null && upCorner.getPiece() != null || downCorner != null && downCorner.getPiece() != null) {
				return false;
			}

			if(!setup){
				// Check if the edges are valid
				Edge rightEdge = edges.get(new EdgeLocation(currentHex.getLocation(), EdgeDirection.North).getNormalizedLocation());
				Edge upEdge = edges.get(new EdgeLocation(upperHex.getLocation(), EdgeDirection.NorthEast).getNormalizedLocation());
				Edge downEdge = edges.get(new EdgeLocation(currentHex.getLocation(), EdgeDirection.NorthWest).getNormalizedLocation());



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

			if(lowerHex == null) {
				return false;
			}

			// Check if the corners are valid
			Vertex upCorner = verticies.get(new VertexLocation(currentHex.getLocation(), VertexDirection.West).getNormalizedLocation());
			Vertex rightCorner = verticies.get(new VertexLocation(currentHex.getLocation(), VertexDirection.SouthEast).getNormalizedLocation());
			Vertex downCorner = verticies.get(new VertexLocation(lowerHex.getLocation(), VertexDirection.West).getNormalizedLocation());


			if(rightCorner != null && rightCorner.getPiece() != null ||
					upCorner != null && upCorner.getPiece() != null ||
					downCorner != null && downCorner.getPiece() != null) {
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
			if(hexes.get(loc.getNormalizedLocation().getHexLoc()) == null) {
				return false;
			}

			if(edges.get(loc.getNormalizedLocation()).getPiece() != null) {
				//System.out.println(edges.get(loc.getNormalizedLocation()).getPiece());
				return false;
			}



			TerrainHex hex = hexes.get(loc.getNormalizedLocation().getHexLoc());
			TerrainHex neighbor = hexes.get(hex.getLocation().getNeighborLoc(loc.getNormalizedLocation().getDir()));
			if(neighbor == null) {
				return false;
			}

			if (hex.getType().equals(HexType.WATER) && neighbor.getType().equals(HexType.WATER)){
				return false;
			}

			switch(loc.getNormalizedLocation().getDir()) {
				case NorthWest: return edgeNWCase(playerID, free, loc.getNormalizedLocation(), hex, neighbor);
				case North: return edgeNCase(playerID, free, loc.getNormalizedLocation(), hex, neighbor);
				case NorthEast: return edgeNECase(playerID, free, loc.getNormalizedLocation(), hex, neighbor);
				case SouthEast: return edgeSECase(playerID, free, loc.getNormalizedLocation(), hex, neighbor);
				case South: return edgeSCase(playerID, free, loc.getNormalizedLocation(), hex, neighbor);
				case SouthWest: return edgeSWCase(playerID, free, loc.getNormalizedLocation(), hex, neighbor);


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


		if(!setup) {
			if (upperLeft.getPiece() != null) {
				if (upperLeft.getPiece().getPlayerID() == playerID) {
					edgeValid = true;
				}
			}
			if (upperRight.getPiece() != null) {
				if (upperRight.getPiece().getPlayerID() == playerID) {
					edgeValid = true;
				}
			}
			if (lowerLeft.getPiece() != null) {
				if (lowerLeft.getPiece().getPlayerID() == playerID) {
					edgeValid = true;
				}
			}
			if (lowerRight.getPiece() != null) {
				if (lowerRight.getPiece().getPlayerID() == playerID) {
					edgeValid = true;
				}
			}
		}


		// Check if the surrounding corners make this case valid
		Vertex left = verticies.get(new VertexLocation(loc.getHexLoc(), VertexDirection.NorthWest).getNormalizedLocation());
		Vertex right = verticies.get(new VertexLocation(loc.getHexLoc(), VertexDirection.West).getNormalizedLocation());

		boolean cornerValid = false;

		if(left.getPiece() != null) {
			if (left.getPiece().getPlayerID() == playerID) {
				if(!setup) {
					cornerValid = true;
				}
				else{

				}
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


		if(!setup) {
			if (upperLeft.getPiece() != null) {
				if (upperLeft.getPiece().getPlayerID() == playerID) {
					edgeValid = true;
				}
			}
			if (upperRight.getPiece() != null) {
				if (upperRight.getPiece().getPlayerID() == playerID) {
					edgeValid = true;
				}
			}
			if (lowerLeft.getPiece() != null) {
				if (lowerLeft.getPiece().getPlayerID() == playerID) {
					edgeValid = true;
				}
			}
			if (lowerRight.getPiece() != null) {
				if (lowerRight.getPiece().getPlayerID() == playerID) {
					edgeValid = true;
				}
			}
		}


		// Check if the surrounding corners make this case valid
		Vertex left = verticies.get(new VertexLocation(loc.getHexLoc(), VertexDirection.NorthEast).getNormalizedLocation());
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

		if(!setup) {

			if (upperLeft.getPiece() != null) {
				if (upperLeft.getPiece().getPlayerID() == playerID) {
					edgeValid = true;
				}
			}
			if (upperRight.getPiece() != null) {
				if (upperRight.getPiece().getPlayerID() == playerID) {
					edgeValid = true;
				}
			}
			if (lowerLeft.getPiece() != null) {
				if (lowerLeft.getPiece().getPlayerID() == playerID) {
					edgeValid = true;
				}
			}
			if (lowerRight.getPiece() != null) {
				if (lowerRight.getPiece().getPlayerID() == playerID) {
					edgeValid = true;
				}
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

		if(!free) {

			if (upperLeft.getPiece() != null) {
				if (upperLeft.getPiece().getPlayerID() == playerID) {
					edgeValid = true;
				}
			}
			if (upperRight.getPiece() != null) {
				if (upperRight.getPiece().getPlayerID() == playerID) {
					edgeValid = true;
				}
			}
			if (lowerLeft.getPiece() != null) {
				if (lowerLeft.getPiece().getPlayerID() == playerID) {
					edgeValid = true;
				}
			}
			if (lowerRight.getPiece() != null) {
				if (lowerRight.getPiece().getPlayerID() == playerID) {
					edgeValid = true;
				}
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

		if(!free) {

			if (upperLeft.getPiece() != null) {
				if (upperLeft.getPiece().getPlayerID() == playerID) {
					edgeValid = true;
				}
			}
			if (upperRight.getPiece() != null) {
				if (upperRight.getPiece().getPlayerID() == playerID) {
					edgeValid = true;
				}
			}
			if (lowerLeft.getPiece() != null) {
				if (lowerLeft.getPiece().getPlayerID() == playerID) {
					edgeValid = true;
				}
			}
			if (lowerRight.getPiece() != null) {
				if (lowerRight.getPiece().getPlayerID() == playerID) {
					edgeValid = true;
				}
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

		if(!free) {


			if (upperLeft.getPiece() != null) {
				if (upperLeft.getPiece().getPlayerID() == playerID) {
					edgeValid = true;
				}
			}
			if (upperRight.getPiece() != null) {
				if (upperRight.getPiece().getPlayerID() == playerID) {
					edgeValid = true;
				}
			}
			if (lowerLeft.getPiece() != null) {
				if (lowerLeft.getPiece().getPlayerID() == playerID) {
					edgeValid = true;
				}
			}
			if (lowerRight.getPiece() != null) {
				if (lowerRight.getPiece().getPlayerID() == playerID) {
					edgeValid = true;
				}
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
		// Normal hexes
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

	public void createWaterHexes() {
		// Water hexes
		HexLocation loc20 = new HexLocation(0, -3);
		TerrainHex hex20 = new TerrainHex(loc20, HexType.WATER, -1);
		hexes.put(loc20, hex20);

		/*HexLocation loc22 = new HexLocation(2, -2);
		TerrainHex hex22 = new TerrainHex(loc22, HexType.WATER, -1);
		hexes.put(loc22, hex22);*/

		HexLocation loc21 = new HexLocation(1, -3);
		TerrainHex hex21 = new TerrainHex(loc21, HexType.WATER, -1);
		hexes.put(loc21, hex21);

		HexLocation loc22 = new HexLocation(2,-3);
		TerrainHex hex22 = new TerrainHex(loc22, HexType.WATER, -1);
		hexes.put(loc22,hex22);



		HexLocation loc23 = new HexLocation(3, -3);
		TerrainHex hex23 = new TerrainHex(loc23, HexType.WATER, -1);
		hexes.put(loc23, hex23);

		HexLocation loc24 = new HexLocation(3, -2);
		TerrainHex hex24 = new TerrainHex(loc24, HexType.WATER, -1);
		hexes.put(loc24, hex24);

	/*	HexLocation loc25 = new HexLocation(3, -1);
		TerrainHex hex25 = new TerrainHex(loc25, HexType.WATER, -1);
		hexes.put(loc25, hex25);*/

		HexLocation loc26 = new HexLocation(3, -1);
		TerrainHex hex26 = new TerrainHex(loc26, HexType.WATER, -1);
		hexes.put(loc26, hex26);

		HexLocation loc27 = new HexLocation(3, 0);
		TerrainHex hex27 = new TerrainHex(loc27, HexType.WATER, -1);
		hexes.put(loc27, hex27);

		HexLocation loc28 = new HexLocation(2, 1);
		TerrainHex hex28 = new TerrainHex(loc28, HexType.WATER, -1);
		hexes.put(loc28, hex28);

		HexLocation loc29 = new HexLocation(1, 2);
		TerrainHex hex29 = new TerrainHex(loc29, HexType.WATER, -1);
		hexes.put(loc29, hex29);

		HexLocation loc30 = new HexLocation(0, 3);
		TerrainHex hex30 = new TerrainHex(loc30, HexType.WATER, -1);
		hexes.put(loc30, hex30);

		HexLocation loc31 = new HexLocation(-1, 3);
		TerrainHex hex31 = new TerrainHex(loc31, HexType.WATER, -1);
		hexes.put(loc31, hex31);

		HexLocation loc32 = new HexLocation(-2, 3);
		TerrainHex hex32 = new TerrainHex(loc32, HexType.WATER, -1);
		hexes.put(loc32, hex32);

		HexLocation loc33 = new HexLocation(-3, 3);
		TerrainHex hex33 = new TerrainHex(loc33, HexType.WATER, -1);
		hexes.put(loc33, hex33);

		HexLocation loc34 = new HexLocation(-3, 2);
		TerrainHex hex34 = new TerrainHex(loc34, HexType.WATER, -1);
		hexes.put(loc34, hex34);

		HexLocation loc35 = new HexLocation(-3, 1);
		TerrainHex hex35 = new TerrainHex(loc35, HexType.WATER, -1);
		hexes.put(loc35, hex35);

		HexLocation loc36 = new HexLocation(-3, 0);
		TerrainHex hex36 = new TerrainHex(loc36, HexType.WATER, -1);
		hexes.put(loc36, hex36);

		HexLocation loc37 = new HexLocation(-2, -1);
		TerrainHex hex37 = new TerrainHex(loc37, HexType.WATER, -1);
		hexes.put(loc37, hex37);

		HexLocation loc38 = new HexLocation(-1, -2);
		TerrainHex hex38 = new TerrainHex(loc38, HexType.WATER, -1);
		hexes.put(loc38, hex38);
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
		if(canBuildRoad(playerID, false, spot1.getNormalizedLocation())) {
			Edge tempEdge = edges.get(spot1);
			Piece temp = new Piece(PieceType.ROAD, tempEdge, null, playerID);
			edges.get(spot1.getNormalizedLocation()).setPiece(temp);
			if(canBuildRoad(playerID, false, spot2.getNormalizedLocation())) {
				firstPossible = true;
			}
			edges.get(spot1.getNormalizedLocation()).setPiece(null);
		}


		// Second case
		if(canBuildRoad(playerID, false, spot2.getNormalizedLocation())) {
			Edge tempEdge = edges.get(spot2.getNormalizedLocation());
			Piece temp = new Piece(PieceType.ROAD, tempEdge, null, playerID);
			edges.get(spot2.getNormalizedLocation()).setPiece(temp);
			if(canBuildRoad(playerID, false, spot1.getNormalizedLocation())) {
				secondPossible = true;
			}
			edges.get(spot2.getNormalizedLocation()).setPiece(null);
		}



		return firstPossible || secondPossible;
	}

	public HashMap<HexLocation, TerrainHex> getHexes() {
		return hexes;
	}

	public void setHexes(HashMap<HexLocation, TerrainHex> hexes) {
		this.hexes = hexes;
	}

	public HexLocation getRobberLocation() {
		return robberLocation;
	}

	public HashMap<EdgeLocation, PortType> getEdgePorts() {
		return edgePorts;
	}

	public void setRobberLocation(HexLocation robberLocation) {
		this.robberLocation = robberLocation;
	}

	public HashMap<VertexLocation, Port> getPorts() {
		return ports;
	}
}
