package server.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import client.server.ServerException;
import server.facades.GameFacadeInterface;

public class GameHandler implements HttpHandler{
	
	/**
	 * Used to make calls to command objects (or return canned results)
	 */
	private GameFacadeInterface facade;
	
	public GameHandler(GameFacadeInterface facade){
		this.facade = facade;
	}
	
	/**
	 * setter for dependency injection
	 * @param facade real or mock ServerFacade
	 */
	public void setFacade(GameFacadeInterface facade){
		this.facade = facade;
	}
	
	/**
	 * calls the UserFacade to handle the /game/* commands
	 * Sends back the appropriate response to the client
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		System.out.println("Game endpoint received");
		
		String command = exchange.getRequestURI().toString().replace("/game", "");
		BufferedReader in = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
		StringBuilder json = new StringBuilder();
		String inputLine;
		while((inputLine = in.readLine()) != null){
			json.append(inputLine);
		}
		exchange.getResponseHeaders().set("Content-Type", "application/text");
		try{
			String response = "Success";
			switch(command){
				case "/addAI": facade.addAI(json.toString()); break;
				case "/listAI": response = facade.listAIs(); break;
				default: System.out.println("Unavailable method");
			}
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			exchange.getResponseBody().write(response.getBytes());
			exchange.getResponseBody().close();
		}catch(ServerException e){
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, -1);
			exchange.getResponseBody().write(e.getMessage().getBytes());
			exchange.getResponseBody().close();
			e.printStackTrace();
		} 
	}

}
