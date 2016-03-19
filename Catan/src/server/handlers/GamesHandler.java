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
			User user = null;
			if(!command.equals("/list")){
				String cookie = "";
				try{
					cookie = exchange.getRequestHeaders().get("Cookie").get(0);
				}catch(Exception e){
					throw new ServerException("No user cookie");
				}
				user = ServerManager.getInstance().validateUser(cookie);
				if(user == null){
					throw new ServerException("Invalid user cookie");
				}
			}
			String response = "";
			switch(command){
				case "/list": response = facade.listGames(); break;
				case "/join": gameID = facade.joinGame(json.toString(), user); break;
				case "/create": response = facade.createGame(json.toString()); break;
			}
			if(gameID != -1){
				String cookie = "catan.game=" + gameID + ";Path=/;";
				exchange.getResponseHeaders().add("Set-cookie", cookie);
				response = "Success";
			}
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			exchange.getResponseBody().write(response.getBytes());
			exchange.getResponseBody().close();
		}catch(Exception e){
			e.printStackTrace();
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			exchange.getResponseBody().write(e.getMessage().getBytes());
			exchange.getResponseBody().close();
			e.printStackTrace();
		}
		
	}

}
