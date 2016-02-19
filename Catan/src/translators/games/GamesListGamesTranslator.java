package translators.games;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import client.data.GameInfo;
import client.data.PlayerInfo;
import translators.GenericTranslator;

public class GamesListGamesTranslator 
	extends GenericTranslator{
	
	private String json;

	public GamesListGamesTranslator(String json){
		this.json = json;
	}
	
	public GameInfo[] getGameInfo(){
		Gson gson = new Gson();
		JsonArray jsonGames = gson.fromJson(json, JsonArray.class);
		GameInfo[] games = new GameInfo[jsonGames.size()];
		for(int i = 0; i < jsonGames.size(); i++){
			games[i] = getGame((JsonObject) jsonGames.get(i));
		}
		return games;
	}
	
	public GameInfo getGame(JsonObject jsonGame){
		GameInfo game = new GameInfo();
		JsonPrimitive jsonTitle = jsonGame.getAsJsonPrimitive("title");
		String title = jsonTitle.getAsString();
		game.setTitle(title);
		
		JsonPrimitive jsonId = jsonGame.getAsJsonPrimitive("id");
		int id = jsonId.getAsInt();
		game.setId(id);
		
		JsonArray jsonPlayers = jsonGame.getAsJsonArray("players");
		for(int i = 0; i < jsonPlayers.size(); i++){
			game.addPlayer((getPlayerInfo((JsonObject)jsonPlayers.get(i))));
		}
		return game;
	}
	
	public PlayerInfo getPlayerInfo(JsonObject jsonPlayer){
		PlayerInfo player = new PlayerInfo();
		
		JsonPrimitive jsonName = jsonPlayer.getAsJsonPrimitive("name");
		String name = jsonName.getAsString();
		player.setName(name);
		
		return player;
	}
	
}
