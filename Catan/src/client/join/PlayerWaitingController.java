package client.join;

import java.util.ArrayList;
import java.util.Observable;

import client.base.*;
import client.data.GameInfo;
import client.data.PlayerInfo;
import client.gameManager.GameManager;
import shared.model.GameException;
import shared.model.player.Player;


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
				GameManager.getInstance().getNewModel();
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
		PlayerInfo[] playersInfo = null;
		try{
			int id = GameManager.getInstance().getGameID();
			GameInfo[] games = GameManager.getInstance().listGames();
			GameInfo game = games[id];
			game.getPlayers();
			gameSize = 0;
			for(int i = 0; i < game.getPlayers().size(); i++){
				if(game.getPlayers().get(i).getId() != -1){
					gameSize++;
				}
			}
			playersInfo = new PlayerInfo[gameSize];
			for(int i = 0; i < gameSize; i++){
				playersInfo[i] = new PlayerInfo();
				playersInfo[i].setName(game.getPlayers().get(i).getName());
				playersInfo[i].setColor(game.getPlayers().get(i).getColor());
				playersInfo[i].setId(game.getPlayers().get(i).getId());
				if(game.getPlayers().get(i).getId() == GameManager.getInstance().getPlayerInfo().getId()){
					GameManager.getInstance().getPlayerInfo().setPlayerIndex(i);
				}
				playersInfo[i].setPlayerIndex(i);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return playersInfo;
	}

}

