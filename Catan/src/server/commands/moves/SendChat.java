package server.commands.moves;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import server.commands.Command;
import shared.model.GameModel;

public class SendChat extends Command{

	String type;
	int playerIndex;
	String message;

	public SendChat(int gameID, String json) {
		super(gameID, json);
		// TODO Auto-generated constructor stub
		translate(json);
	}



	public void translate(String json) {
		Gson gson = new Gson();
		JsonObject root;
		try{
			root = gson.fromJson(json, JsonObject.class);
		}catch(Exception e){
			return;
		}

		JsonPrimitive typePrim = root.getAsJsonPrimitive("type");
		type = typePrim.getAsString();

		JsonPrimitive myPlayerIndex = root.getAsJsonPrimitive("playerIndex");
		playerIndex = myPlayerIndex.getAsInt();

		JsonPrimitive myMessage = root.getAsJsonPrimitive("number");
		message = myMessage.getAsString();


	}

	/**
	 * Preconditions: None. Message may be sent at any time by any player.
	 * PostConditions: The message is sent to the chat log.
	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
