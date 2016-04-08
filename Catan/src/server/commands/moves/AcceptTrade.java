package server.commands.moves;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import client.server.ServerException;
import server.ServerManager;
import server.ServerTranslator;
import server.commands.Command;
import shared.definitions.ResourceType;
import shared.model.GameModel;
import shared.model.Line;
import shared.model.trade.TradeOffer;

public class AcceptTrade extends Command{

	private int playerIndex;
	private boolean willAccept;
	ArrayList<ResourceType> resourceDesired;
	
	public AcceptTrade(int gameID, String json) {
		super(gameID, json);
		// TODO Auto-generated constructor stub
	}

	private void translate()
	{
		Gson gson = new Gson();
		JsonObject root;
		try{
			root = gson.fromJson(json, JsonObject.class);
		}catch(Exception e){
			return;
		}
		playerIndex = root.getAsJsonPrimitive("playerIndex").getAsInt();
		willAccept = root.getAsJsonPrimitive("willAccept").getAsBoolean();
	}
//sdf
	
	private void moveResources()
	{
		TradeOffer myOffer = model.getTradeOffer();
		ArrayList<ResourceType> resourcesOffered = myOffer.getResourceDesired();
		ArrayList<ResourceType> resourcesDesired = myOffer.getResourceOffered();
		resourceDesired = resourcesDesired;
		for (int i = 0; i < resourcesOffered.size(); i++)
		{
			//requesting player loses resources offered
			model.getPlayers().get(myOffer.getRequestingPlayerID()).getPlayerHand().removeResources(1, resourcesOffered.get(i));
			//accepting player gains resources offered
			model.getPlayers().get(myOffer.getAcceptingPlayerID()).getPlayerHand().addResources(1, resourcesOffered.get(i));
		}
		for (int i = 0; i < resourcesDesired.size(); i++)
		{
			model.getPlayers().get(myOffer.getAcceptingPlayerID()).getPlayerHand().removeResources(1, resourcesDesired.get(i));
			model.getPlayers().get(myOffer.getRequestingPlayerID()).getPlayerHand().addResources(1, resourcesDesired.get(i));
		}
	}
	/**
	 * Preconditions: You have been offered a domestic trade.
	 * To accept the trade offer, you have the required resources
	 * Postconditions: If you accepted, you and the player who offered swap the specified resources.
	 * If you declined, no resources are exchanged.
	 * The trade offer is removed after choice is made.
	 * @throws ServerException 
	 */
	@Override
	public Object execute() throws ServerException {
		if(gameID != -1){
			this.model = ServerManager.getInstance().getGame(gameID);
		}
		// TODO Auto-generated method stub
		playingState();
		translate();
		if (willAccept)
		{
			if (!model.acceptTrade(playerIndex))
			{
				throw new ServerException("Player does not have the required resources");
			}
			moveResources();
			Line tempLine = new Line(model.getPlayers().get(playerIndex).getName(), "The trade was accepted");
			model.getLog().addLine(tempLine);
		}
		else
		{
			Line tempLine = new Line(model.getPlayers().get(playerIndex).getName(), "The trade was not accepted");
			model.getLog().addLine(tempLine);
		}
		model.updateVersionNumber();
		model.setTradeOffer(null);
		ServerTranslator temp = new ServerTranslator(model);
		return temp.translate();
	}

}
