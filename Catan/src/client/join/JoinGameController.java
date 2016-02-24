package client.join;

import shared.definitions.CatanColor;

import java.awt.List;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.JButton;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import client.base.*;
import client.data.*;
import client.misc.*;
import gameManager.GameManager;
import server.ServerPoller;


/**
 * Implementation for the join game controller
 */
public class JoinGameController extends Controller implements IJoinGameController {

	private INewGameView newGameView;
	private ISelectColorView selectColorView;
	private IMessageView messageView;
	private IAction joinAction;
	private GameInfo gameToJoin;
	
	/**
	 * JoinGameController constructor
	 * 
	 * @param view Join game view
	 * @param newGameView New game view
	 * @param selectColorView Select color view
	 * @param messageView Message view (used to display error messages that occur while the user is joining a game)
	 */
	public JoinGameController(IJoinGameView view, INewGameView newGameView, 
								ISelectColorView selectColorView, IMessageView messageView) {

		super(view);

		setNewGameView(newGameView);
		setSelectColorView(selectColorView);
		setMessageView(messageView);
	}
	
	public IJoinGameView getJoinGameView() {
		
		return (IJoinGameView)super.getView();
	}
	
	/**
	 * Returns the action to be executed when the user joins a game
	 * 
	 * @return The action to be executed when the user joins a game
	 */
	public IAction getJoinAction() {
		
		return joinAction;
	}

	/**
	 * Sets the action to be executed when the user joins a game
	 * 
	 * @param value The action to be executed when the user joins a game
	 */
	public void setJoinAction(IAction value) {	
		
		joinAction = value;
	}
	
	public INewGameView getNewGameView() {
		
		return newGameView;
	}

	public void setNewGameView(INewGameView newGameView) {
		
		this.newGameView = newGameView;
	}
	
	public ISelectColorView getSelectColorView() {
		
		return selectColorView;
	}
	public void setSelectColorView(ISelectColorView selectColorView) {
		
		this.selectColorView = selectColorView;
	}
	
	public IMessageView getMessageView() {
		
		return messageView;
	}
	public void setMessageView(IMessageView messageView) {
		
		this.messageView = messageView;
	}

	public GameInfo getGameToJoin() {
		return gameToJoin;
	}

	public void setGameToJoin(GameInfo gameToJoin) {
		this.gameToJoin = gameToJoin;
	}

	@Override
	public void start() {
		try{
			GameInfo[] games = GameManager.getInstance().listGames();
			getJoinGameView().setGames(games, GameManager.getInstance().getPlayerInfo());
			getJoinGameView().showModal();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void startCreateNewGame() {
		getNewGameView().showModal();
	}

	@Override
	public void cancelCreateNewGame() {
		getNewGameView().closeModal();
	}

	@Override
	public void createNewGame() {
		String title = getNewGameView().getTitle();
		boolean randomNumbers = getNewGameView().getRandomlyPlaceNumbers();
		boolean randomTiles = getNewGameView().getRandomlyPlaceHexes();
		boolean randomPorts = getNewGameView().getUseRandomPorts();
		try{
			String json = GameManager.getInstance().createGame(title, randomTiles, randomNumbers, randomPorts);
			Gson gson = new Gson();
			JsonObject gameInfo = gson.fromJson(json, JsonObject.class);
			JsonPrimitive jsonGameId = gameInfo.getAsJsonPrimitive("id");
			int gameID = jsonGameId.getAsInt();
			GameManager.getInstance().joinGame(gameID, "red");
			getNewGameView().closeModal();
			start();
		}catch(Exception e){
			getMessageView().setTitle("Create Game Error");
			getMessageView().setMessage("Error in creating the game");
			getMessageView().showModal();
			e.printStackTrace();
		}
	}

	@Override
	public void startJoinGame(GameInfo game) {
		gameToJoin = game;
		for(int i = 0; i < game.getPlayers().size(); i++){
			if(game.getPlayers().get(i).getId() != GameManager.getInstance().getPlayerInfo().getId()){
				getSelectColorView().setColorEnabled(game.getPlayers().get(i).getColor(), false);
			}else{
				GameManager.getInstance().getPlayerInfo().setPlayerIndex(i);
			}
		}
		if(getJoinGameView().isModalShowing()){
			getJoinGameView().closeModal();
		}
		getSelectColorView().showModal();
	}

	@Override
	public void cancelJoinGame() {
		getJoinGameView().closeModal();
	}

	@Override
	public void joinGame(CatanColor color) {
		try{
			GameManager.getInstance().joinGame(gameToJoin.getId(), getColorToString(color));
			GameManager.getInstance().getPlayerInfo().setColor(color);
			getSelectColorView().closeModal();
			getJoinGameView().closeModal();
		}catch(Exception e){
			e.printStackTrace();
		}
		GameManager manager = GameManager.getInstance();
		ServerPoller poller = new ServerPoller(manager.getServer(), manager);
		joinAction.execute();
	}
	
	private String getColorToString(CatanColor color) {
		
		switch (color) {
		case BLUE:return "blue";
		case BROWN:return "brown";
		case GREEN:return "green";
		case ORANGE:return "orange";
		case PUCE:return "puce";
		case PURPLE:return "purple";
		case RED:return "red";
		case WHITE:return "white";
		case YELLOW:return "yellow";
		}
		return null;
	}
	
	@Override
	public void update(Observable o, Object arg) {
	}

}

