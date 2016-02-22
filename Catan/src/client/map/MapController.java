package client.map;

import java.util.*;

import gameManager.GameManager;
import map.Edge;
import map.Map;
import map.TerrainHex;
import map.Vertex;
import model.GameException;
import model.GameModel;
import shared.Piece;
import shared.definitions.*;
import shared.locations.*;
import client.base.*;
import client.data.*;


/**
 * Implementation for the map controller
 */
public class MapController extends Controller implements IMapController {
	
	
	private Map map;

	private IRobView robView;
	
	public MapController(IMapView view, IRobView robView) {


		super(view);

		setRobView(robView);
		//map = GameManager.getInstance().getModel().getMap();
		if (map == null){
			map = new Map();
		}
		
		initFromModel();
	}
	
	public IMapView getView() {
		
		return (IMapView)super.getView();
	}
	
	private IRobView getRobView() {
		return robView;
	}
	private void setRobView(IRobView robView) {
		this.robView = robView;
	}
	

	
	protected void initFromModel() {
		
		// Place Hexes, numbers, robber
		for(java.util.Map.Entry<HexLocation, TerrainHex> entry: map.getHexes().entrySet()){
			
			getView().addHex(entry.getKey(), entry.getValue().getType());
			if(entry.getValue().getType() != HexType.WATER && entry.getValue().getType() != HexType.DESERT){
				getView().addNumber(entry.getKey(), entry.getValue().getNumber());
				
			}
			/*if(entry.getValue().getType() == HexType.DESERT) {
				getView().placeRobber(entry.getKey());
			}*/
		}

		getView().placeRobber(map.getRobberLocation());

		for(java.util.Map.Entry<EdgeLocation, Edge> entry: map.getEdges().entrySet()){
			if (entry.getValue().getPiece() != null && entry.getValue().getPiece().getPieceType() == PieceType.ROAD){
				getView().placeRoad(entry.getKey(),
						CatanColor.RED);
			}
		}

		for(java.util.Map.Entry<EdgeLocation, PortType> entry : map.getEdgePorts().entrySet()) {
			HexLocation hexLoc = entry.getKey().getHexLoc();
			TerrainHex hex = map.getHexes().get(hexLoc);
			EdgeLocation edgeLoc = entry.getKey();



			if(hex.getType() == HexType.WATER){
				getView().addPort(entry.getKey(),entry.getValue());
			}
			else{
				hexLoc = hexLoc.getNeighborLoc(edgeLoc.getDir());
				hex = map.getHexes().get(hexLoc);
				edgeLoc = new EdgeLocation(hexLoc, entry.getKey().getDir().getOppositeDirection());
				getView().addPort(edgeLoc, entry.getValue());
			}
		}

		for(java.util.Map.Entry<VertexLocation, Vertex> entry : map.getVerticies().entrySet()) {
			if(entry.getValue().getPiece() != null && entry.getValue().getPiece().getPieceType() == PieceType.CITY) {
				getView().placeCity(entry.getKey(), CatanColor.RED);
			} else if(entry.getValue().getPiece() != null && entry.getValue().getPiece().getPieceType() == PieceType.SETTLEMENT) {
				getView().placeSettlement(entry.getKey(), CatanColor.RED);
			}
		}

		
		// Place roads
/*		for()
		getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.NorthWest),
				CatanColor.RED);*/
		
		

/*		for(Entry<VertexLocation, Port> entry : map.getPorts().entrySet() {
			
			getView().addPort(new EdgeLocation(new HexLocation(0, 3), EdgeDirection.North), entry.getValue().getType());

		}
*/
		
		
		
		
		/*Random rand = new Random();

		for (int x = 0; x <= 3; ++x) {
			
			int maxY = 3 - x;			
			for (int y = -3; y <= maxY; ++y) {				
				int r = rand.nextInt(HexType.values().length);
				HexType hexType = HexType.values()[r];
				HexLocation hexLoc = new HexLocation(x, y);
				getView().addHex(hexLoc, hexType);
				getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.NorthWest),
						CatanColor.RED);
				getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.SouthWest),
						CatanColor.RED);
				getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.South),
						CatanColor.RED);
				getView().placeSettlement(new VertexLocation(hexLoc,  VertexDirection.NorthWest), CatanColor.RED);
				getView().placeCity(new VertexLocation(hexLoc,  VertexDirection.NorthEast), CatanColor.RED);
			}
			
			if (x != 0) {
				int minY = x - 3;
				for (int y = minY; y <= 3; ++y) {
					int r = rand.nextInt(HexType.values().length);
					HexType hexType = HexType.values()[r];
					HexLocation hexLoc = new HexLocation(-x, y);
					getView().addHex(hexLoc, hexType);
					getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.NorthWest),
							CatanColor.RED);
					getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.SouthWest),
							CatanColor.RED);
					getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.South),
							CatanColor.RED);
					getView().placeSettlement(new VertexLocation(hexLoc,  VertexDirection.NorthWest), CatanColor.RED);
					getView().placeCity(new VertexLocation(hexLoc,  VertexDirection.NorthEast), CatanColor.RED);
				}
			}
		}*/
		
		/*PortType portType = PortType.BRICK;
		getView().addPort(new EdgeLocation(new HexLocation(0, 3), EdgeDirection.North), portType);
		getView().addPort(new EdgeLocation(new HexLocation(0, -3), EdgeDirection.South), portType);
		getView().addPort(new EdgeLocation(new HexLocation(-3, 3), EdgeDirection.NorthEast), portType);
		getView().addPort(new EdgeLocation(new HexLocation(-3, 0), EdgeDirection.SouthEast), portType);
		getView().addPort(new EdgeLocation(new HexLocation(3, -3), EdgeDirection.SouthWest), portType);
		getView().addPort(new EdgeLocation(new HexLocation(3, 0), EdgeDirection.NorthWest), portType);*/

		
		
		
/*for(Entry<HexLocation, TerrainHex> entry: map.getHexes().entrySet()){
			
			
			if(entry.getValue().getType() != HexType.WATER && entry.getValue().getType() != HexType.DESERT){
				getView().addNumber(entry.getKey(), entry.getValue().getNumber());
				
			}
			
		}*/
		
		/*getView().addNumber(new HexLocation(-2, 0), 5);
		getView().addNumber(new HexLocation(-2, 1), 5);
		getView().addNumber(new HexLocation(-2, 2), 5);
		getView().addNumber(new HexLocation(-1, 0), 5);
		getView().addNumber(new HexLocation(-1, 1), 5);
		getView().addNumber(new HexLocation(1, -1), 5);
		getView().addNumber(new HexLocation(1, 0), 5);
		getView().addNumber(new HexLocation(2, -2), 5);
		getView().addNumber(new HexLocation(2, -1), 5);
		getView().addNumber(new HexLocation(2, 0), 5);*/
		
		//</temp>
	}

	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
		int playerID = 1;

		return map.canBuildRoad(playerID,true,edgeLoc);
	}

	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		int playerID = 1;
		return map.canBuildSettlement(playerID, true, vertLoc);
	}

	public boolean canPlaceCity(VertexLocation vertLoc) {
		int playerID = 1;
		return map.canBuildCity(playerID,vertLoc);
	}

	public boolean canPlaceRobber(HexLocation hexLoc) {
		
		return true;
	}

	public void placeRoad(EdgeLocation edgeLoc) {
		Piece road = new Piece(PieceType.ROAD,null,null,1);
		map.getEdges().get(edgeLoc).setPiece(road);
	/*	try {
			GameManager.getInstance().buildRoad(1, edgeLoc, true);
		} catch (GameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		//CatanColor color = road.getColor();
		//getView().placeRoad(edgeLoc, color);
		getView().placeRoad(edgeLoc, CatanColor.BLUE);

	}

	public void placeSettlement(VertexLocation vertLoc) {
		Piece building = new Piece(PieceType.SETTLEMENT,null,null,1);

		map.getVerticies().get(vertLoc).setPiece(building);
		/*try {
			GameManager.getInstance().buildSettlment(1, vertLoc, true);
		} catch (GameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		//CatanColor color = building.getColor();

		//getView().placeSettlement(vertLoc, color);
		getView().placeSettlement(vertLoc, CatanColor.BLUE);

	}

	public void placeCity(VertexLocation vertLoc) {
		map.getVerticies().get(vertLoc).setPiece(new Piece(PieceType.CITY,null,null,1));

		getView().placeCity(vertLoc, CatanColor.ORANGE);
	}

	public void placeRobber(HexLocation hexLoc) {
		
		getView().placeRobber(hexLoc);
		
		getRobView().showModal();
	}
	
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {	
		
		getView().startDrop(pieceType, CatanColor.BLUE, true);
	}
	
	public void cancelMove() {
		
	}
	
	public void playSoldierCard() {	
		
	}
	
	public void playRoadBuildingCard() {	
		
	}
	
	public void robPlayer(RobPlayerInfo victim) {	
		
	}

	/* (non-Javadoc)
	 * @see client.base.Controller#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		//super.update(o, arg);
		
		map = GameManager.getInstance().getModel().getMap();
		initFromModel();
	}
	


}

