package client.map;

import java.util.*;

import map.Edge;
import map.Map;
import map.TerrainHex;
import shared.definitions.*;
import shared.locations.*;
import client.base.*;
import client.data.*;


/**
 * Implementation for the map controller
 */
public class MapController extends Controller implements IMapController {
	
	private Map map = new Map();
	private IRobView robView;
	
	public MapController(IMapView view, IRobView robView) {
		
		super(view);
		
		setRobView(robView);
		
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
			if(entry.getValue().getType() == HexType.DESERT) {
				getView().placeRobber(entry.getKey());
			}
		}
		for(java.util.Map.Entry<EdgeLocation, Edge> entry: map.getEdges().entrySet()){
			if (entry.getValue().getPiece() != null && entry.getValue().getPiece().getPieceType() == PieceType.ROAD){
				getView().placeRoad(entry.getKey(),
						CatanColor.RED);
			}
		}

		for(java.util.Map.Entry<EdgeLocation, PortType> entry : map.getEdgePorts().entrySet()) {
			getView().addPort(entry.getKey(),entry.getValue());
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
		
		PortType portType = PortType.BRICK;
		getView().addPort(new EdgeLocation(new HexLocation(0, 3), EdgeDirection.North), portType);
		getView().addPort(new EdgeLocation(new HexLocation(0, -3), EdgeDirection.South), portType);
		getView().addPort(new EdgeLocation(new HexLocation(-3, 3), EdgeDirection.NorthEast), portType);
		getView().addPort(new EdgeLocation(new HexLocation(-3, 0), EdgeDirection.SouthEast), portType);
		getView().addPort(new EdgeLocation(new HexLocation(3, -3), EdgeDirection.SouthWest), portType);
		getView().addPort(new EdgeLocation(new HexLocation(3, 0), EdgeDirection.NorthWest), portType);

		
		
		
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
		
		return true;
	}

	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		
		return true;
	}

	public boolean canPlaceCity(VertexLocation vertLoc) {
		
		return true;
	}

	public boolean canPlaceRobber(HexLocation hexLoc) {
		
		return true;
	}

	public void placeRoad(EdgeLocation edgeLoc) {
		
		getView().placeRoad(edgeLoc, CatanColor.ORANGE);
	}

	public void placeSettlement(VertexLocation vertLoc) {
		
		getView().placeSettlement(vertLoc, CatanColor.ORANGE);
	}

	public void placeCity(VertexLocation vertLoc) {
		
		getView().placeCity(vertLoc, CatanColor.ORANGE);
	}

	public void placeRobber(HexLocation hexLoc) {
		
		getView().placeRobber(hexLoc);
		
		getRobView().showModal();
	}
	
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {	
		
		getView().startDrop(pieceType, CatanColor.ORANGE, true);
	}
	
	public void cancelMove() {
		
	}
	
	public void playSoldierCard() {	
		
	}
	
	public void playRoadBuildingCard() {	
		
	}
	
	public void robPlayer(RobPlayerInfo victim) {	
		
	}
	
}

