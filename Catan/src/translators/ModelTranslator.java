package translators;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import model.GameModel;

/**
 * This class should parse through the model that is returned
 */
public class ModelTranslator {
	
	public ModelTranslator(){
	
	};
	
	//takes in an object, casts it a JsonObject then parses through it
	public GameModel getModelfromJSON(String json){
		Gson gson = new Gson();
		JsonObject root = gson.fromJson(json, JsonObject.class);
		JsonObject deck = root.getAsJsonObject("deck");
		JsonPrimitive yearOfPlenty = deck.getAsJsonPrimitive("yearOfPlenty");
		int yop = yearOfPlenty.getAsInt();
		System.out.println(yop);
		return null;
	};
}
