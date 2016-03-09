package server;

import com.sun.net.httpserver.HttpServer;

public class Server {
	
	/**
	 * server itself
	 */
	private HttpServer server;
	/**
	 * which port the server is listening on
	 */
	private int port;
	/**
	 * the max number of pending connections
	 */
	private int connections;
	
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
