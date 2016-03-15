package server.handlers;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import server.facades.GamesFacadeInterface;

public class GamesHandler implements HttpHandler{
	
	/**
	 * Used to make calls to command objects (or return canned results)
	 */
	private GamesFacadeInterface facade;
	
	public GamesHandler(GamesFacadeInterface facade){
		this.facade = facade;
	}
	
	/**
	 * setter for dependency injection
	 * @param facade real or mock ServerFacade
	 */
	public void setFacade(GamesFacadeInterface facade){
		this.facade = facade;
	}
	
	/**
	 * calls the UserFacade to handle the /games/* commands
	 * Sends back the appropriate response to the client with the catan-game cookie
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		System.out.println("Games endpoint received");
		
	}

}
