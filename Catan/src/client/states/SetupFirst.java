package client.states;

/**
 * Created by Brian on 16/2/17.
 */
public class SetupFirst extends State{

    public SetupFirst() {}

    @Override
    public boolean canBuildCity(int playerID) {
        return super.canBuildCity(playerID);
    }

    @Override
    public boolean canBuildRoad(int playerID) {
        return super.canBuildRoad(playerID);
    }

    @Override
    public boolean canBuildSettlement(int playerID) {
        return super.canBuildSettlement(playerID);
    }

    @Override
    public boolean enoughForCity(int playerID) {
        return super.enoughForCity(playerID);
    }

    @Override
    public boolean enoughForSettlement(int playerID) {
        return super.enoughForSettlement(playerID);
    }

    @Override
    public boolean enoughForRoad(int playerID) {
        return super.enoughForRoad(playerID);
    }


}
