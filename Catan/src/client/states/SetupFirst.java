package client.states;

import client.map.MapController;
import gameManager.GameManager;
import map.Map;
import model.GameException;
import shared.definitions.PieceType;
import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;

import java.util.Scanner;

/**
 * Created by Brian on 16/2/17.
 */
public class SetupFirst extends State{

    private MapController mc;

    public SetupFirst(Map map, MapController mc) {
        super(map);
        this.mc = mc;

        Scanner s = new Scanner(System.in);

        System.out.println("before model");

       mc.getView().startDrop(PieceType.SETTLEMENT, GameManager.getInstance().getPlayerInfo().getColor(), false);
       // System.out.println("press any key to continue");
        //s.next();

        System.out.println("after model");

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

    public void  buildRoad(int playerID, EdgeLocation loc) {
        try {
            GameManager.getInstance().buildRoad(playerID, loc, true);
            GameManager.getInstance().finishTurn(playerID);
        } catch (GameException e) {
            e.printStackTrace();
        }

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
// ff



}
