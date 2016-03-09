package client.translators;

import com.google.gson.Gson;

public class GenericTranslator {

	public GenericTranslator(){};
	
	public String translate(){
		Gson gson = new Gson();
		String json = gson.toJson(this);
		return json;
	}
}
