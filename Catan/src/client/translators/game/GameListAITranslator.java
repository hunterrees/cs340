package client.translators.game;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

public class GameListAITranslator {

	private String json;
	
	public GameListAITranslator(String json){
		this.json = json;
	}
	
	public String[] getAIs(){
		Gson gson = new Gson();
		JsonArray AIs = gson.fromJson(json, JsonArray.class);
		String[] aiArray = new String[AIs.size()];
		for(int i = 0; i < AIs.size(); i++){
			aiArray[i] = AIs.getAsString();
		}
		return aiArray;
	}
}
