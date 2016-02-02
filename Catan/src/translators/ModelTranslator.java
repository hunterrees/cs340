package translators;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

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
		JsonElement rootElement = gson.fromJson(json, JsonElement.class);
		return null;
	};
}
