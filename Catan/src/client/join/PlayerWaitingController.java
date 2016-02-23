package client.join;

import java.util.ArrayList;
import java.util.Observable;

import client.base.*;
import client.data.PlayerInfo;
import gameManager.GameManager;
import player.Player;


/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends Controller implements IPlayerWaitingController {

	public PlayerWaitingController(IPlayerWaitingView view) {

		super(view);
	}

	@Override
	public IPlayerWaitingView getView() {

		return (IPlayerWaitingView)super.getView();
	}

	@Override
	public void start() {
		getView().showModal();
		try{
			String[] ais = GameManager.getInstance().listAIs();
			getView().setAIChoices(ais);
			getView().setPlayers(getPlayerInfo());
			getView().showModal();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	@Override
	public void addAI() {
		try{
			GameManager.getInstance().addAI(getView().getSelectedAI());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		start();
	}
	
	private PlayerInfo[] getPlayerInfo(){
		ArrayList<Player> players = GameManager.getInstance().getModel().getPlayers();
		PlayerInfo[] playersInfo = new PlayerInfo[players.size()];
		for(int i = 0; i < players.size(); i++){
			playersInfo[i] = new PlayerInfo();
			playersInfo[i].setName(players.get(i).getName());
			playersInfo[i].setColor(players.get(i).getPlayerColor());
			playersInfo[i].setId(players.get(i).getPlayerID());
			playersInfo[i].setPlayerIndex(i);
		}
		return playersInfo;
	}

}

