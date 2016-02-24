package client.map;

import java.util.*;

import client.states.*;
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

	private State state;

	public MapController(IMapView view, IRobView robView) {


		super(view);
		//GameManager.getInstance().addObserver(this);

		setRobView(robView);
		//map = GameManager.getInstance().getModel().getMap();
		/*if (map == null){
			map = new Map();
		}
		initFromModel();
		state = new SetupSecond(map, this);
		System.out.println("\n\n\n\n\nin the constructorrrrrrrr\n\n\n\n\n");
		state = new SetupFirst(map, this);

		System.out.println("\n\n\n\n after first setup\n\n\n\n\n");*/

		//state = new Normal(map);


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
		if (GameManager.getInstance().isStartingUp()){
			return;
		}
		
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
			
			getView().addPort(new EdgeLocation(new HexLocation(0, 3), EdgeDire
			ction.North), entry.getValue().getType());

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

		return state.canBuildRoad(playerID,edgeLoc);
	}

	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		int playerID = 1;
		return state.canBuildSettlement(playerID, vertLoc);
	}

	public boolean canPlaceCity(VertexLocation vertLoc) {
		int playerID = 1;
		return state.canBuildCity(playerID,vertLoc);
	}

	public boolean canPlaceRobber(HexLocation hexLoc) {
		
		return true;
	}

	private int x = 0;
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
		getView().placeRoad(edgeLoc, GameManager.getInstance().getPlayerInfo().getColor());


		/*if(state instanceof SetupSecond) {
			state = new Normal(map, this);
		}
		if(state instanceof SetupFirst) {
			state = new SetupSecond(map, this);
		}

		if(state instanceof Normal && x == 0) {
			state.playRoadBuild(GameManager.getInstance().getPlayerInfo().getId());
			x = 1;
		}*/


	}

	public void placeSettlement(VertexLocation vertLoc) {
		Piece building = new Piece(PieceType.SETTLEMENT,null,null,1);

		//map.getVerticies().get(vertLoc).setPiece(building);
		/*try {
			GameManager.getInstance().buildSettlment(1, vertLoc, true);
		} catch (GameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		//CatanColor color = building.getColor();

		//getView().placeSettlement(vertLoc, color);
		getView().placeSettlement(vertLoc, GameManager.getInstance().getPlayerInfo().getColor());

		int id = GameManager.getInstance().getPlayerInfo().getId();
		state.buildSettlement(id, vertLoc);

	}

	public void placeCity(VertexLocation vertLoc) {
		map.getVerticies().get(vertLoc).setPiece(new Piece(PieceType.CITY,null,null,1));

		getView().placeCity(vertLoc, GameManager.getInstance().getPlayerInfo().getColor());
	}

	public void placeRobber(HexLocation hexLoc) {
		
		getView().placeRobber(hexLoc);
		
		getRobView().showModal();
	}
	
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {	
		
		getView().startDrop(pieceType, GameManager.getInstance().getPlayerInfo().getColor(), true);
	}
	
	public void cancelMove() {
		
	}
	
	public void playSoldierCard() {	
		
	}
	
	public void playRoadBuildingCard() {
		state.playRoadBuild(GameManager.getInstance().getPlayerInfo().getId());
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
		System.out.println("\n\n\n\nupdatinggggggggggggggggggggggggggggggggggggggggg\n\n\n\n\n");

		map = GameManager.getInstance().getModel().getMap();
		initFromModel();

		if(GameManager.getInstance().isMyTurn()) {
			switch (GameManager.getInstance().getModel().getGameState()) {
				case playing:
					state = new Normal(map, this);
					break;
				//case discarding: state = new Discard();
				//case rolling: state = new Rolling();
				case robbing:
					state = new Robbing(map);
				case firstRound:
					state = new SetupFirst(map, this);
				case secondRound:
					state = new SetupSecond(map, this);
				default:
					state = new NotYourTurn(map);
			}
		} else {
			state = new NotYourTurn(map);
		}

		map = GameManager.getInstance().getModel().getMap();
		initFromModel();
	}


	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}

