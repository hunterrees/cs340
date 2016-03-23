package server.commands.games;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import client.server.ServerException;
import server.ServerManager;
import server.User;
import server.commands.Command;
import shared.definitions.CatanColor;
import shared.model.GameModel;
import shared.model.player.Player;

public class JoinGame extends Command{

	private String color;
	private CatanColor catanColor;
	private User user;
	
	public JoinGame(int gameID, String json) {
		super(gameID, json);
		Gson gson = new Gson();
		JsonObject root = null;
		try{
			root = gson.fromJson(json, JsonObject.class);
		}catch(Exception e){
			e.printStackTrace();
		}
		this.gameID = root.getAsJsonPrimitive("id").getAsInt();
		color = root.getAsJsonPrimitive("color").getAsString();
	}

	/**
	 * Preconditions: The user has previously logged in to the server.
	 *  The player may join the game because they are already in the game, or there is space in the game to add a new player.
	 *  The specified game ID is valid.
	 *  The specified color is valid(red, green, blue, yellow, puce, brown, white, purple).
	 * Postconditions: If the operation succeeds,
	 *	1. The server returns an HTTP 200 success response with “Success” in the body.
	 *	2. The player is in the game with the specified color (i.e. calls to /games/list method will
	 *	show the player in the game with the chosen color).
	 *	3. The server response includes the “Set­cookie” response header setting the catan.game
	 *	HTTP cookie
	 *	If the operation fails,
	 *	1. The server returns an HTTP 400 error response, and the body contains an error
	 *	message.
	 * @throws ServerException 
	 */
	@Override
	public Object execute() throws ServerException {
		GameModel game = ServerManager.getInstance().getGame(gameID);
		if(game == null){
			throw new ServerException("Game does not exist");
		}
		catanColor = getColor(color);
		if(catanColor == null){
			throw new ServerException("Invalid color");
		}
		for(int i = 0; i < game.getPlayers().size(); i++){
			if(game.getPlayers().get(i).getName().equals(user.getName())){
				game.getPlayers().get(i).setPlayerColor(catanColor);
				return gameID;
			}
		}
		if(game.getPlayers().size() < 4){
			game.getPlayers().add(new Player(user.getplayerID(), catanColor, user.getName()));
			return gameID;
		}
			throw new ServerException("Game is full and user wasn't in gamea already");
	}
	
	public void setUser(User user){
		this.user = user;
	}
	
	private CatanColor getColor(String type){
		if(type.equals("red")){return CatanColor.RED;};
		if(type.equals("orange")){return CatanColor.ORANGE;};
		if(type.equals("yellow")){return CatanColor.YELLOW;};
		if(type.equals("green")){return CatanColor.GREEN;};
		if(type.equals("blue")){return CatanColor.BLUE;};
		if(type.equals("purple")){return CatanColor.PURPLE;};
		if(type.equals("puce")){return CatanColor.PUCE;};
		if(type.equals("white")){return CatanColor.WHITE;};
		if(type.equals("brown")){return CatanColor.BROWN;};
		return null;
	}

}
