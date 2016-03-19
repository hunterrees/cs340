package server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GameInfo {

	private String title;
	private int id;
	private PlayerInfo[] players;
	
	public GameInfo()
	{
		setId(-1);
		setTitle("");
		players = new PlayerInfo[4];
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public void addPlayer(PlayerInfo player){
		for(int i = 0; i < 4; i++){
			if(players[i] == null){
				players[i] = player;
				return;
			}
		}
	}
	
	public PlayerInfo[] getPlayers(){
		return players;
	}
}
