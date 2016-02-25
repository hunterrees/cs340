package client.states;

import client.map.MapController;
import gameManager.GameManager;
import map.Map;
import model.GameException;
import shared.Piece;
import shared.definitions.CatanColor;
import shared.definitions.PieceType;
import shared.locations.EdgeLocation;

/**
 * Created by Brian on 16/2/23.
 */
public class RoadBulid extends State {
    MapController mc;
    EdgeLocation firstEdge;
    EdgeLocation secondEdge;

    public RoadBulid(Map map, MapController mc ) {
        super(map);
        this.mc = mc;
        mc.getView().startDrop(PieceType.ROAD, GameManager.getInstance().getPlayerInfo().getColor(), true);
    }

    @Override
    public void buildRoad(int playerID, EdgeLocation loc) {
    	if(firstEdge != null){
    		try {
    			
    			GameManager.getInstance().buildRoad(playerID, firstEdge, true);
    			GameManager.getInstance().buildRoad(playerID, loc, true);
    			
    		} catch (GameException e) {
    			e.printStackTrace();
    		}
    	}
    	else{
    		firstEdge = loc;
    		map.getEdges().get(loc).setPiece(new Piece(PieceType.ROAD, null, null, playerID));
    		mc.getView().startDrop(PieceType.ROAD, GameManager.getInstance().getPlayerInfo().getColor(), true);
    		
    	}

        
    }
    

    
	@Override
	public boolean canBuildRoad(int playerID, EdgeLocation loc) {
		// TODO Auto-generated method stub
		return map.canBuildRoad(playerID, false, loc);
	}

	@Override
    public void playRoadBuild(int playerID) {
        
    }
	
	@Override
	public void cancel() {
		System.out.println("roadbuild canceling");
		if(firstEdge != null){
			System.out.println("had first edge");
			map.getEdges().get(firstEdge).setPiece(null);
			//mc.update(null, null);
			mc.initFromModel();
            mc.getView().placeRoad(firstEdge, CatanColor.CLEAR);


        }
	}
}
