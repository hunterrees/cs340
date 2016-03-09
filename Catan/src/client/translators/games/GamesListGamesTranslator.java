package client.translators.games;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import client.data.GameInfo;
import client.data.PlayerInfo;
import client.translators.GenericTranslator;
import shared.definitions.CatanColor;

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
			PlayerInfo player = getPlayerInfo((JsonObject)jsonPlayers.get(i));
			if(player != null){
				game.addPlayer(player);
			}
		}
		return game;
	}
	
	public PlayerInfo getPlayerInfo(JsonObject jsonPlayer){
		PlayerInfo player = new PlayerInfo();
		
		JsonPrimitive jsonName = jsonPlayer.getAsJsonPrimitive("name");
		if(jsonName == null){
			return null;
		}
		String name = jsonName.getAsString();
		player.setName(name);
		JsonPrimitive jsonId = jsonPlayer.getAsJsonPrimitive("id");
		int id = jsonId.getAsInt();
		player.setId(id);
		JsonPrimitive jsonColor = jsonPlayer.getAsJsonPrimitive("color");
		String type = jsonColor.getAsString();
		CatanColor color = getColor(type);
		player.setColor(color);
		return player;
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
