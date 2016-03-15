package server.handlers;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import server.facades.MovesFacadeInterface;

public class MovesHandler implements HttpHandler{
	
	/**
	 * Used to make calls to command objects (or return canned results)
	 */
	private MovesFacadeInterface facade;
	
	public MovesHandler(MovesFacadeInterface facade){
		this.facade = facade;
	}
	
	/**
	 * setter for dependency injection
	 * @param facade real or mock ServerFacade
	 */
	public void setFacade(MovesFacadeInterface facade){
		this.facade = facade;
	}
	
	/**
	 * calls the UserFacade to handle the /moves/* commands
	 * Sends back the appropriate response to the client
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		System.out.println("Moves endpoint received");
		
	}

}
