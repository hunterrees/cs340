package client.states;

import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.map.Map;
/**
 * Created by Brian on 16/2/17.
 */
public class State {
    protected Map map;

    public State(Map map) {
        this.map = map;
    }


    public boolean enoughForRoad(int playerID) {
        return false;
    }

    public Map getMap() {
		return map;
	}


	public void setMap(Map map) {
		this.map = map;
	}


	public boolean enoughForSettlement(int playerID) {
        return false;
    }

    public boolean enoughForCity(int playerID) {
        return false;
    }

    public boolean canBuildRoad(int playerID, EdgeLocation loc) {
        return false;
    }

    public boolean canBuildSettlement(int playerID, VertexLocation loc) {
        return false;
    }

    public boolean canBuildCity(int playerID, VertexLocation loc) {
        return false;
    }

    public void  buildRoad(int playerID, EdgeLocation loc) {}

    public void buildSettlement(int playerId, VertexLocation loc) {

    }

    public void buildCity(int playerId, VertexLocation loc) {}

    public void playRoadBuild(int playerID) {

    }
    
    public void cancel() {
    	
    }
    public boolean canPlaceRobber(HexLocation hexLoc){
    	return false;
    }
    public void placeRobber(HexLocation hexLoc, int playerID){
    	
    }
    


}
