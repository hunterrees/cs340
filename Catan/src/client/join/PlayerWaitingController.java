package client.join;

import java.util.ArrayList;
import java.util.Observable;

import client.base.*;
import client.data.GameInfo;
import client.data.PlayerInfo;
import gameManager.GameManager;
import model.GameException;
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
			if(!getView().isModalShowing()){
				getView().showModal();
			}
			if(GameManager.getInstance().getModel().getPlayers().size() == 4){
				getView().closeModal();
				GameManager.getInstance().setStartingUp(false);
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
		if(GameManager.getInstance().isGameEnd()){
			return;
		}
		if(GameManager.getInstance().isStartingUp()){
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
			if(players.get(i).getPlayerID() == GameManager.getInstance().getPlayerInfo().getId()){
				GameManager.getInstance().getPlayerInfo().setPlayerIndex(i);
			}
			playersInfo[i].setPlayerIndex(i);
		}
		return playersInfo;
	}

}

