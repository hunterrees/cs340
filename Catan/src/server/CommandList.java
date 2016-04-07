package server;

import java.util.ArrayList;

import client.server.ServerException;
import server.commands.Command;

public class CommandList {
	
	private ArrayList<Command> commands;
	
	public CommandList(){
	}
	
	public void addCommand(Command command){
		commands.add(command);
	}
	
	public void execute() throws ServerException{
		for(int i = 0; i < commands.size(); i++){
			commands.get(i).execute();
		}
	}
	
	public int size(){
		return commands.size();
	}
	
	public void clear(){
		commands.clear();
	}
}
