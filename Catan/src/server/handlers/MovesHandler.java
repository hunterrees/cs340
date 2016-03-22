package server.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import client.server.ServerException;
import server.ServerManager;
import server.User;
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
		String command = exchange.getRequestURI().toString().replace("/moves", "");
		BufferedReader in = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
		StringBuilder json = new StringBuilder();
		String inputLine;
		while((inputLine = in.readLine()) != null){
			json.append(inputLine);
		}
		exchange.getResponseHeaders().set("Content-Type", "application/text");
		try{
			String cookie = "";
			String[] cookies = new String[2];
			User user = null;
			try{
				cookie = exchange.getRequestHeaders().get("Cookie").get(0);
				cookies = cookie.split(";");
			}catch(Exception e){
				throw new ServerException("No cookie");
			}
			user = ServerManager.getInstance().validateUser(cookies[0]);
			if(user == null){
				throw new ServerException("Invalid user cookie");
			}
			String gameCookie = cookies[1];
			gameCookie = gameCookie.replace("catan.game=", "");
			gameCookie = gameCookie.trim();
			int gameID = Integer.parseInt(gameCookie);
			
			String response = facade.determineOperation(command, json.toString(), gameID);
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
