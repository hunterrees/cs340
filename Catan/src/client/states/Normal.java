package client.states;

import gameManager.GameManager;
import map.Map;
import model.GameException;
import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;

/**
 * Created by Brian on 16/2/17.
 */
public class Normal extends State {

    public Normal(Map map) {
        super(map);
    }

    @Override
    public boolean canBuildRoad(int playerID, EdgeLocation loc) {
        return map.canBuildRoad(playerID, false, loc);
    }

    @Override
    public boolean canBuildSettlement(int playerID, VertexLocation loc) {
        return map.canBuildSettlement(playerID, false, loc);
    }

    @Override
    public boolean canBuildCity(int playerID, VertexLocation loc) {
        return map.canBuildCity(playerID, loc);
    }

    @Override
    public void  buildRoad(int playerID, EdgeLocation loc) {
        try {
            GameManager.getInstance().buildRoad(playerID, loc, false);
        } catch (GameException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void buildSettlement(int playerId, VertexLocation loc) {
        try {
            GameManager.getInstance().buildSettlment(playerId, loc, false);
        } catch (GameException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void buildCity(int playerId, VertexLocation loc) {
        try {
            GameManager.getInstance().buildCity(playerId,loc);
        } catch (GameException e) {
            e.printStackTrace();
        }
    }


}
