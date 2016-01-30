package trade;

public class tradeOffer 
{
	//A player attempts to make a domestic Trade. The resource that they are trying to 
	//trade is checked by the game model. Then, the request is called on the server.
	//the recieving or targeted player will then see the trade via the server poller,
	//because this class will be populated. If that player accepts the trade, then
	//execute trade will be called on the server

	private int requestingPlayerID;
	private int acceptingPlayerID;
	private int resourceOffered;
	private int resourceDesired;
	
	public tradeOffer(int requestingPlayerID, int acceptingPlayerID, int resourceOffered, int resourceDesired)
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

	public int getResourceOffered() {
		return resourceOffered;
	}

	public void setResourceOffered(int resourceOffered) {
		this.resourceOffered = resourceOffered;
	}

	public int getResourceDesired() {
		return resourceDesired;
	}

	public void setResourceDesired(int resourceDesired) {
		this.resourceDesired = resourceDesired;
	}
}
