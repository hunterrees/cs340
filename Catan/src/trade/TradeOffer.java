package trade;

import java.util.ArrayList;

import shared.definitions.ResourceType;

public class TradeOffer 
{
	//A player attempts to make a domestic Trade. The resource that they are trying to 
	//trade is checked by the game model. Then, the request is called on the server.
	//the recieving or targeted player will then see the trade via the server poller,
	//because this class will be populated. If that player accepts the trade, then
	//execute trade will be called on the server


	private int requestingPlayerID;
	private int acceptingPlayerID;
	private ArrayList<ResourceType> resourceOffered;
	private ArrayList<ResourceType> resourceDesired;
	
	public TradeOffer(int requestingPlayerID, int acceptingPlayerID, ArrayList<ResourceType> resourceOffered,
	ArrayList<ResourceType> resourceDesired)
	{
		this.requestingPlayerID = requestingPlayerID;
		this.acceptingPlayerID = acceptingPlayerID;
		this.resourceOffered = resourceOffered;
		this.resourceDesired = resourceDesired;
	}

	public int getRequestingPlayerID() {
		return requestingPlayerID;
	}

	public void setRequestingPlayerID(int requestingPlayerID) {
		this.requestingPlayerID = requestingPlayerID;
	}

	public int getAcceptingPlayerID() {
		return acceptingPlayerID;
	}

	public void setAcceptingPlayerID(int acceptingPlayerID) {
		this.acceptingPlayerID = acceptingPlayerID;
	}

	public ArrayList<ResourceType> getResourceOffered() {
		return resourceOffered;
	}

	public void setResourceOffered(ArrayList<ResourceType> resourceOffered) {
		this.resourceOffered = resourceOffered;
	}

	public ArrayList<ResourceType> getResourceDesired() {
		return resourceDesired;
	}

	public void setResourceDesired(ArrayList<ResourceType> resourceDesired) {
		this.resourceDesired = resourceDesired;
	}
}
