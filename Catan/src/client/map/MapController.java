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

		setRobView(robView);



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

		getView().placeRoad(edgeLoc, GameManager.getInstance().getPlayerInfo().getColor());



	}

	public void placeSettlement(VertexLocation vertLoc) {
		Piece building = new Piece(PieceType.SETTLEMENT,null,null,1);


		getView().placeSettlement(vertLoc, GameManager.getInstance().getPlayerInfo().getColor());

		int id = GameManager.getInstance().getPlayerInfo().getPlayerIndex();
		state.buildSettlement(id, vertLoc);
		initFromModel();
		

	}

	public void placeCity(VertexLocation vertLoc) {
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
		state = new Robbing(map);
		usingSoldier = true;
		getView().startDrop(PieceType.ROBBER, GameManager.getInstance().getPlayerInfo().getColor(), true);
		
		
	}
	
	public void playRoadBuildingCard() {
		state = new RoadBulid(map, this);
	}
	
	public void robPlayer(RobPlayerInfo victim) {	
		
		if(usingSoldier){
			try {
				GameManager.getInstance().knight(GameManager.getInstance().getPlayerInfo().getPlayerIndex(), victim.getPlayerIndex(), map.getRobberLocation());
				usingSoldier = false;
			} catch (GameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
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
	public synchronized void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		//super.update(o, arg);

		if(GameManager.getInstance().isGameEnd() || GameManager.getInstance().isStartingUp()){
			return;
		}
		map = GameManager.getInstance().getModel().getMap();
		
		initFromModel();

		if(GameManager.getInstance().isMyTurn()) {

			switch (GameManager.getInstance().getModel().getGameState()) {
				case playing:
					state = new Normal(map, this);

					
					
					break;

				case robbing:
					if (!(state instanceof Robbing)){
						state = new Robbing(map);
						getView().startDrop(PieceType.ROBBER, GameManager.getInstance().getPlayerInfo().getColor(), false);
					}
					break;
				case firstRound:

					

					if(!firstCalled) {
						firstCalled = true;
						state = new SetupFirst(map, this);



					}
					state.setMap(map);
					break;
				case secondRound:

					if(!secondCalled){
						secondCalled = true;
						state = new SetupSecond(map, this);
					}
					state.setMap(map);

					break;
				default:
					state = new NotYourTurn(map);
					break;
			}
		} else {

			state = new NotYourTurn(map);
		}




	}


	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}

