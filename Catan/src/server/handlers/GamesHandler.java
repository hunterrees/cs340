package server.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import client.server.ServerException;
import server.ServerManager;
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
		
		BufferedReader in = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
		StringBuilder json = new StringBuilder();
		String inputLine;
		while((inputLine = in.readLine()) != null){
			json.append(inputLine);
		}
		int gameID = -1;
		exchange.getResponseHeaders().set("Content-Type", "application/text");
		try{
			if(!command.equals("/list")){
				String cookie = exchange.getRequestHeaders().get("Cookie").get(0);
				//System.out.println(cookie);
				if(!ServerManager.getInstance().validateUser(cookie)){
					throw new ServerException("Invalid User cookie");
				}else{
					System.out.println("Valid user cookie");
				}
			}
			switch(command){
				case "/list": facade.listGames(); break;
				case "/join": gameID = facade.joinGame(json.toString()); break;
				case "/create": facade.createGame(json.toString()); break;
			}
			if(gameID != -1){
				String cookie = "catan.game=" + gameID + ";Path=/;";
				exchange.getResponseHeaders().add("Set-cookie", cookie);
			}
		}catch(Exception e){
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, -1);
			exchange.getResponseBody().write(e.getMessage().getBytes());
			exchange.getResponseBody().close();
			e.printStackTrace();
		}
		
	}

}
