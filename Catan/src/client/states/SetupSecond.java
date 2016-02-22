package client.states;

import client.map.MapController;
import gameManager.GameManager;
import map.Map;
import map.Vertex;
import model.GameException;
import shared.definitions.PieceType;
import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;

/**
 * Created by Brian on 16/2/17.
 */
public class SetupSecond extends State {


    private MapController mc;

    public SetupSecond(Map map, MapController mc) {
        super(map);
        this.mc = mc;

        mc.getView().startDrop(PieceType.ROAD, GameManager.getInstance().getPlayerInfo().getColor(), false);

        mc.getView().startDrop(PieceType.SETTLEMENT, GameManager.getInstance().getPlayerInfo().getColor(), false);
    }

    @Override
    public boolean canBuildCity(int playerID, VertexLocation loc) {
        return false;
    }

    @Override
    public boolean canBuildRoad(int playerID, EdgeLocation loc) {
        return map.canBuildRoad(playerID, true, loc);
    }

    @Override
    public boolean canBuildSettlement(int playerID, VertexLocation loc) {
        return map.canBuildSettlement(playerID, true, loc);
    }

    @Override
    public void  buildRoad(int playerID, EdgeLocation loc) {
        try {
            GameManager.getInstance().buildRoad(playerID, loc, true);
        } catch (GameException e) {
            e.printStackTrace();
        }
       // mc.setState(new Normal(map));
    }

    @Override
    public void buildSettlement(int playerId, VertexLocation loc) {
        try {
            GameManager.getInstance().buildSettlment(playerId, loc, true);
        } catch (GameException e) {
            e.printStackTrace();
        }
    }

}
