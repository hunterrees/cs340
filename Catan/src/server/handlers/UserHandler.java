package server.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import client.server.ServerException;
import server.facades.UserFacadeInterface;

public class UserHandler implements HttpHandler{
	
	/**
	 * Used to make calls to command objects (or return canned results)
	 */
	private UserFacadeInterface facade;
	
	public UserHandler(UserFacadeInterface facade){
		this.facade = facade;
	}
	
	/**
	 * setter for dependency injection
	 * @param facade real or mock ServerFacade
	 */
	public void setFacade(UserFacadeInterface facade){
		this.facade = facade;
	}
	
	/**
	 * calls the UserFacade to handle the /user/* commands
	 * Sends back the appropriate response to the client with a catan-user cookie
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String command = exchange.getRequestURI().toString().replace("/user", "");
		BufferedReader in = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
		StringBuilder json = new StringBuilder();
		String inputLine;
		while((inputLine = in.readLine()) != null){
			json.append(inputLine);
		}
		String cookie ="";
		exchange.getResponseHeaders().set("Content-Type", "application/text");
		try{
			switch(command){
				case "/login": cookie = facade.login(json.toString()); break;
				case "/register": cookie = facade.register(json.toString()); break;
				default: System.out.println("Unavailable method");
			}
			String userCookie = "catan.user=" + cookie + ";Path=/;";
			exchange.getResponseHeaders().add("Set-cookie", userCookie);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			String body = "Success";
			byte[] response = body.getBytes();
			exchange.getResponseBody().write(response);
			exchange.getResponseBody().close();
		}catch(ServerException e){
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			exchange.getResponseBody().write(e.getMessage().getBytes());
			exchange.getResponseBody().close();
		} 
	}
}
