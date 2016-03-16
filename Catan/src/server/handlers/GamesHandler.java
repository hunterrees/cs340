package server.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import client.server.ServerException;
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
		String command = exchange.getRequestURI().toString().replace("/games", "");
		if(!command.equals("/list")){
			System.out.println(command);
			try{
				System.out.println("Cookie " + exchange.getRequestHeaders().get("Cookie").get(0));
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
		StringBuilder json = new StringBuilder();
		String inputLine;
		while((inputLine = in.readLine()) != null){
			json.append(inputLine);
		}
		int gameID = -1;
		try{
			switch(command){
				case "/list": facade.listGames(); break;
				case "/joinGame": gameID = facade.joinGame(json.toString()); break;
				case "/createGame": facade.createGame(json.toString()); break;
			}
			if(gameID != -1){
				String cookie = "catan.game=" + gameID + ";Path=/;";
				exchange.getResponseHeaders().add("Set-cookie", cookie);
			}
		}catch(ServerException e){
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			byte[] response = e.getMessage().getBytes();
			exchange.getResponseBody().write(response);
			exchange.close();
			e.printStackTrace();
		}
		
	}

}
