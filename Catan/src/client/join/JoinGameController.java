package client.join;

import shared.definitions.CatanColor;

import java.util.Observable;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import client.base.*;
import client.data.*;
import client.gameManager.GameManager;
import client.misc.*;


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
		getJoinGameView().closeModal();
		getNewGameView().showModal();
	}

	@Override
	public void cancelCreateNewGame() {
		getNewGameView().closeModal();
		start();
	}

	@Override
	public void createNewGame() {
		String title = getNewGameView().getTitle();
		boolean randomNumbers = getNewGameView().getRandomlyPlaceNumbers();
		boolean randomTiles = getNewGameView().getRandomlyPlaceHexes();
		boolean randomPorts = getNewGameView().getUseRandomPorts();
		try{
			GameInfo[] games = GameManager.getInstance().listGames();
			for(int i = 0; i < games.length; i++){
				if(games[i].getTitle().equals(title)){
					throw new Exception();
				}
			}
			getNewGameView().closeModal();
			String json = GameManager.getInstance().createGame(title, randomTiles, randomNumbers, randomPorts);
			Gson gson = new Gson();
			JsonObject gameInfo = gson.fromJson(json, JsonObject.class);
			JsonPrimitive jsonGameId = gameInfo.getAsJsonPrimitive("id");
			int gameID = jsonGameId.getAsInt();
			GameManager.getInstance().joinGame(gameID, "red");
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
		try{
			GameInfo[] games = GameManager.getInstance().listGames();
			gameToJoin = games[game.getId()];
			int numPlayers = 0;
			boolean inGame = false;
			for(int i = 0; i < gameToJoin.getPlayers().size(); i++){
				if(!gameToJoin.getPlayers().get(i).getName().equals("")){
					numPlayers++;
				}
				if(gameToJoin.getPlayers().get(i).getId() == GameManager.getInstance().getPlayerInfo().getId()){
					inGame = true;
				}
			}
			if(numPlayers == 4 && !inGame){
				throw new Exception();
			}
			for(int i = 0; i < gameToJoin.getPlayers().size(); i++){
				if(gameToJoin.getPlayers().get(i).getId() != GameManager.getInstance().getPlayerInfo().getId()){
					getSelectColorView().setColorEnabled(gameToJoin.getPlayers().get(i).getColor(), false);
				}else{
					GameManager.getInstance().getPlayerInfo().setPlayerIndex(i);
				}
			}
			getJoinGameView().closeModal();
			getSelectColorView().showModal();
		}catch(Exception e){
			getMessageView().setTitle("Join Game Error");
			getMessageView().setMessage("Error in joining the game (it's full)");
			getMessageView().showModal();
			e.printStackTrace();
		}
	}

	@Override
	public void cancelJoinGame() {
		getSelectColorView().closeModal();
		getSelectColorView().setColorEnabled(CatanColor.BLUE, true);
		getSelectColorView().setColorEnabled(CatanColor.GREEN, true);
		getSelectColorView().setColorEnabled(CatanColor.ORANGE, true);
		getSelectColorView().setColorEnabled(CatanColor.BROWN, true);
		getSelectColorView().setColorEnabled(CatanColor.PUCE, true);
		getSelectColorView().setColorEnabled(CatanColor.PURPLE, true);
		getSelectColorView().setColorEnabled(CatanColor.RED, true);
		getSelectColorView().setColorEnabled(CatanColor.YELLOW, true);
		getSelectColorView().setColorEnabled(CatanColor.WHITE, true);
		getJoinGameView().showModal();
	}

	@Override
	public void joinGame(CatanColor color) {
		try{
			getSelectColorView().closeModal();
			GameManager.getInstance().getPlayerInfo().setColor(color);
			GameManager.getInstance().joinGame(gameToJoin.getId(), getColorToString(color));
		}catch(Exception e){
			e.printStackTrace();
		}
		GameManager.getInstance().startPoller();
		GameManager.getInstance().setGameID(gameToJoin.getId());
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
		default: return null;
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(GameManager.getInstance().isGameEnd()){
			start();
		}
	}

}

