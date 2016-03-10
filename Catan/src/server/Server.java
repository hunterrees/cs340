package server;

import java.util.ArrayList;

import com.sun.net.httpserver.HttpServer;

import server.handlers.GameHandler;
import server.handlers.GamesHandler;
import server.handlers.MovesHandler;
import server.handlers.UserHandler;
import shared.model.*;

public class Server {
	
	/**
	 * server itself
	 */
	private HttpServer server;
	/**
	 * which port the server is listening on
	 */
	private static int PORT = 8081;
	/**
	 * the max number of pending connections
	 */
	private static int MAX_WAITING_CONNECTIONS = 10;
	/**
	 * used for /user/* commands
	 */
	private UserHandler userHandler;
	/**
	 * used for /games/* commands
	 */
	private GamesHandler gamesHandler;
	/**
	 * used for /game/* commands
	 */
	private GameHandler gameHandler;
	/**
	 * used for /moves/* commands
	 */
	private MovesHandler movesHandler;

	public Server(){
	}

	/**
	 * creates the server and creates contexts with handlers
	 */
	private void run(){
		
	}
	/**
	 * Starts the server running
	 * @param args command line argument for port to run on
	 */
	public static void main(String args[]){

	}
}
