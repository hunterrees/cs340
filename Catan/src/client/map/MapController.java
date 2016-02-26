package client.map;

import java.util.*;

import player.Player;
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
	
	private boolean usingSoldier = false;

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
	

	
	public void initFromModel() {
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
				getView().placeRoad(entry.getKey(), entry.getValue().getPiece().getColor());
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
				getView().placeCity(entry.getKey(), entry.getValue().getPiece().getColor());
			} else if(entry.getValue().getPiece() != null && entry.getValue().getPiece().getPieceType() == PieceType.SETTLEMENT) {
				getView().placeSettlement(entry.getKey(), entry.getValue().getPiece().getColor());
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
		int playerID = GameManager.getInstance().getPlayerInfo().getPlayerIndex();;

		return state.canBuildRoad(playerID,edgeLoc);
	}

	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		int playerID = GameManager.getInstance().getPlayerInfo().getPlayerIndex();;
		return state.canBuildSettlement(playerID, vertLoc);
	}

	public boolean canPlaceCity(VertexLocation vertLoc) {
		int playerID = GameManager.getInstance().getPlayerInfo().getPlayerIndex();;
		return state.canBuildCity(playerID,vertLoc);
	}

	public boolean canPlaceRobber(HexLocation hexLoc) {
		
		return state.canPlaceRobber(hexLoc);
	}

	private int x = 0;
	public void placeRoad(EdgeLocation edgeLoc) {
		Piece road = new Piece(PieceType.ROAD,null,null,1);
		state.buildRoad(GameManager.getInstance().getPlayerInfo().getPlayerIndex(), edgeLoc);
		
		//map.getEdges().get(edgeLoc).setPiece(road);
	/*	try {
			GameManager.getInstance().buildRoad(1, edgeLoc, true);
		} catch (GameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		//CatanColor color = road.getColor();
		//getView().placeRoad(edgeLoc, color);
	//	if(!(state instanceof RoadBulid)){
			getView().placeRoad(edgeLoc, GameManager.getInstance().getPlayerInfo().getColor());
	//	}


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

		int id = GameManager.getInstance().getPlayerInfo().getPlayerIndex();
		System.out.println(" player index: " + id);
		System.out.println("plyer ID: " + GameManager.getInstance().getPlayerInfo().getId());
		System.out.println("build settlement");
		state.buildSettlement(id, vertLoc);
		initFromModel();
		

	}

	public void placeCity(VertexLocation vertLoc) {
		//map.getVerticies().get(vertLoc).setPiece(new Piece(PieceType.CITY,null,null,1));
		state.buildCity(GameManager.getInstance().getPlayerInfo().getPlayerIndex(), vertLoc);

		getView().placeCity(vertLoc, GameManager.getInstance().getPlayerInfo().getColor());
	}

	private RobPlayerInfo[] getRobbies(HexLocation hexLoc) {
		TreeSet<Integer> robbieIndexes = new TreeSet<>(); 
		int playerID = GameManager.getInstance().getPlayerInfo().getPlayerIndex();
		// 
		Vertex vert1 = map.getVerticies().get(new VertexLocation(hexLoc, VertexDirection.NorthWest).getNormalizedLocation());
		Vertex vert2 = map.getVerticies().get(new VertexLocation(hexLoc, VertexDirection.NorthEast).getNormalizedLocation());
		Vertex vert3 = map.getVerticies().get(new VertexLocation(hexLoc, VertexDirection.East).getNormalizedLocation());
		Vertex vert4 = map.getVerticies().get(new VertexLocation(hexLoc, VertexDirection.SouthEast).getNormalizedLocation());
		Vertex vert5 = map.getVerticies().get(new VertexLocation(hexLoc, VertexDirection.SouthWest).getNormalizedLocation());
		Vertex vert6 = map.getVerticies().get(new VertexLocation(hexLoc, VertexDirection.West).getNormalizedLocation());
		if(vert1.getPiece() != null && vert1.getPiece().getPlayerID() != playerID) {
			if(GameManager.getInstance().getModel().getPlayers().get(vert1.getPiece().getPlayerID()).getPlayerHand().getNumResources() > 0){
				robbieIndexes.add(vert1.getPiece().getPlayerID());
			}
			
		}
		if(vert2.getPiece() != null && vert2.getPiece().getPlayerID() != playerID) {
			if(GameManager.getInstance().getModel().getPlayers().get(vert2.getPiece().getPlayerID()).getPlayerHand().getNumResources() > 0){
				robbieIndexes.add(vert2.getPiece().getPlayerID());
			}
			
		}
		if(vert3.getPiece() != null && vert3.getPiece().getPlayerID() != playerID) {
			if(GameManager.getInstance().getModel().getPlayers().get(vert3.getPiece().getPlayerID()).getPlayerHand().getNumResources() > 0){
				robbieIndexes.add(vert3.getPiece().getPlayerID());
			}
			
		}
		if(vert4.getPiece() != null && vert4.getPiece().getPlayerID() != playerID) {
			if(GameManager.getInstance().getModel().getPlayers().get(vert4.getPiece().getPlayerID()).getPlayerHand().getNumResources() > 0){
				robbieIndexes.add(vert4.getPiece().getPlayerID());
			}
			
		}
		if(vert5.getPiece() != null && vert5.getPiece().getPlayerID() != playerID) {
			if(GameManager.getInstance().getModel().getPlayers().get(vert5.getPiece().getPlayerID()).getPlayerHand().getNumResources() > 0){
				robbieIndexes.add(vert5.getPiece().getPlayerID());
			}
			
		}
		if(vert6.getPiece() != null && vert6.getPiece().getPlayerID() != playerID) {
			if(GameManager.getInstance().getModel().getPlayers().get(vert6.getPiece().getPlayerID()).getPlayerHand().getNumResources() > 0){
				robbieIndexes.add(vert6.getPiece().getPlayerID());
			}
			
		}
		RobPlayerInfo[] robbies = new RobPlayerInfo[robbieIndexes.size()];
		int j = 0;
		for(Integer i: robbieIndexes){
			RobPlayerInfo temp = createRobbie(i);
			robbies[j] = temp;
			
			j++;
		}
		
		
		
		
		
		
		
		
		return robbies;
	}
	
	public RobPlayerInfo createRobbie(int playerIndex){
		
		Player player = GameManager.getInstance().getModel().getPlayers().get(playerIndex);
		
		int playerID = player.getPlayerID();
		String name = player.getName();
		CatanColor color = player.getPlayerColor();
		int numCards = player.getPlayerHand().getResourceCards().size();
		
		RobPlayerInfo temp = new RobPlayerInfo();
		temp.setColor(color);
		temp.setId(playerID);
		temp.setName(name);
		temp.setNumCards(numCards);
		temp.setPlayerIndex(playerIndex);
		
		
		
		
		
		
		return temp;
	}
	
	public void placeRobber(HexLocation hexLoc) {
		
		//get player to rob info
		RobPlayerInfo[] robbies = getRobbies(hexLoc);
		
		
		//call robberview.setplayers
		if(robbies != null){
			getRobView().setPlayers(robbies);
		}
		else{
			RobPlayerInfo[] robbies2 = new RobPlayerInfo[0];
			getRobView().setPlayers(robbies2);
			
		}
		
		//showmodal
		
		//move robber through view 
		
		getRobView().showModal();
		getView().placeRobber(hexLoc);
		map.setRobberLocation(hexLoc);
	}
	
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {	
		
		getView().startDrop(pieceType, GameManager.getInstance().getPlayerInfo().getColor(), true);
	}
	
	public void cancelMove() {
		state.cancel();
	}
	
	public void playSoldierCard() {	
		// called when they click on soldier
		System.out.println("robbing");
		state = new Robbing(map);
		usingSoldier = true;
		getView().startDrop(PieceType.ROBBER, GameManager.getInstance().getPlayerInfo().getColor(), false);
		
		
	}
	
	public void playRoadBuildingCard() {
		state = new RoadBulid(map, this);
	}
	
	public void robPlayer(RobPlayerInfo victim) {	
		
		if(usingSoldier){
			System.out.println("play soldier called to gm");
			try {
				GameManager.getInstance().knight(GameManager.getInstance().getPlayerInfo().getPlayerIndex(), victim.getPlayerIndex(), map.getRobberLocation());
				usingSoldier = false;
			} catch (GameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			System.out.println("rob player called to gm");
			try {
				GameManager.getInstance().robPlayer(GameManager.getInstance().getPlayerInfo().getPlayerIndex(), victim.getPlayerIndex(), map.getRobberLocation());
			} catch (GameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/* (non-Javadoc)
	 * @see client.base.Controller#update(java.util.Observable, java.lang.Object)
	 */
	int i = 0;
	boolean firstCalled = false;
	boolean secondCalled = false;
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		//super.update(o, arg);
		System.out.println("\n\n\n\nupdatinggggggggggggggggggggggggggggggggggggggggg\n\n\n\n\n");

		if(GameManager.getInstance().isGameEnd()){
			return;
		}
		map = GameManager.getInstance().getModel().getMap();
		
		initFromModel();

		if(GameManager.getInstance().isMyTurn()) {

			switch (GameManager.getInstance().getModel().getGameState()) {
				case playing:
					System.out.println("normal state");
					state = new Normal(map, this);
					/*if (!(state instanceof Robbing)){
						System.out.println("robbing");
						state = new Robbing(map);
						getView().startDrop(PieceType.ROBBER, GameManager.getInstance().getPlayerInfo().getColor(), false);
					}*/
					
					
					break;
				//case discarding: state = new Discard();
				//case rolling: state = new Rolling();
				case robbing:
					if (!(state instanceof Robbing)){
						System.out.println("robbing");
						state = new Robbing(map);
						getView().startDrop(PieceType.ROBBER, GameManager.getInstance().getPlayerInfo().getColor(), false);
					}
					break;
				case firstRound:

					System.out.println("first stateeeeeeee");
					

					if(!firstCalled) {
						firstCalled = true;
						System.out.println("inside first called");
						state = new SetupFirst(map, this);
//						Scanner s = new Scanner(System.in);
//
//						//System.out.println("wait one");
//						//s.next();
//						this.getView().startDrop(PieceType.SETTLEMENT, GameManager.getInstance().getPlayerInfo().getColor(), false);
//						//System.out.println("wait one");
//						//s.next();
//						this.getView().startDrop(PieceType.ROAD, GameManager.getInstance().getPlayerInfo().getColor(), false);


					}
					state.setMap(map);
					break;
				case secondRound:
					System.out.println("second state");
					//if(!(state instanceof SetupSecond)){
					//	System.out.println("seconds state");
					if(!secondCalled){
						secondCalled = true;
						state = new SetupSecond(map, this);
					}
					state.setMap(map);

					//}
					//else{
					//	System.out.println("no state change");
					//}
					break;
				default:
					System.out.println("not your turn 1");
					state = new NotYourTurn(map);
					break;
			}
		} else {
/*			System.out.println("not your turn");
			if(!(state instanceof NotYourTurn) || i < 3){
				state = new SetupFirst(map,this);
				state = new NotYourTurn(map);
				//update(null, null);
			}
			else{
				state = new SetupFirst(map, this);
				state = new NotYourTurn(map);
			}
			i++;*/
			System.out.println("not your turn 2");
			state = new NotYourTurn(map);
		}

		System.out.println("End of update state = " + state);



	}


	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}

