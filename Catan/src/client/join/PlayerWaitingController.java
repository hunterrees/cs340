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
	
	private int gameSize;

	public PlayerWaitingController(IPlayerWaitingView view) {

		super(view);
		GameManager.getInstance().addObserver(this);
	}

	@Override
	public IPlayerWaitingView getView() {

		return (IPlayerWaitingView)super.getView();
	}

	@Override
	public void start() {
		try{
			String[] ais = GameManager.getInstance().listAIs();
			getView().setAIChoices(ais);
			getView().setPlayers(getPlayerInfo());
			getView().showModal();
			if(GameManager.getInstance().getModel().getPlayers().size() == 4){
				GameManager.getInstance().setStartingUp(false);
				getView().closeModal();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	@Override
	public void addAI() {
		try{
			GameManager.getInstance().addAI(getView().getSelectedAI());
			GameManager.getInstance().getNewModel();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(gameSize != GameManager.getInstance().getModel().getPlayers().size()){
			start();
		}
	}
	
	private PlayerInfo[] getPlayerInfo(){
		ArrayList<Player> players = GameManager.getInstance().getModel().getPlayers();
		PlayerInfo[] playersInfo = new PlayerInfo[players.size()];
		gameSize = players.size();
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

