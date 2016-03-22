package client.states;

import client.gameManager.GameManager;
import client.map.MapController;
import shared.definitions.PieceType;
import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;
import shared.model.GameException;
import shared.model.map.Map;

/**
 * Created by Brian on 16/2/17.
 */
public class SetupSecond extends State {


    private MapController mc;

    public SetupSecond(Map map, MapController mc) {
        super(map);
        this.mc = mc;


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
            GameManager.getInstance().finishTurn(playerID);
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
        mc.getView().startDrop(PieceType.ROAD, GameManager.getInstance().getPlayerInfo().getColor(), false);

    }


}
