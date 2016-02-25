package client.states;

import gameManager.GameManager;
import map.Map;
import map.Vertex;
import model.GameException;
import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;

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
    


}
