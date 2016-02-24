package client.states;

import client.map.MapController;
import gameManager.GameManager;
import map.Map;
import model.GameException;
import shared.definitions.PieceType;
import shared.locations.EdgeLocation;

/**
 * Created by Brian on 16/2/23.
 */
public class RoadBulid extends State {
    MapController mc;

    public RoadBulid(Map map, MapController mc ) {
        super(map);
        this.mc = mc;
    }

    @Override
    public void buildRoad(int playerID, EdgeLocation loc) {
        try {
            GameManager.getInstance().buildRoad(playerID, loc, true);
        } catch (GameException e) {
            e.printStackTrace();
        }

        mc.startMove(PieceType.ROAD, false, false);
    }

    @Override
    public void playRoadBuild(int playerID) {
        mc.startMove(PieceType.ROAD, false, false);
    }
}
