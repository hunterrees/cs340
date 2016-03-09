package client.communication;

import java.util.ArrayList;
import java.util.Observable;

import client.base.*;
import client.gameManager.GameManager;
import shared.definitions.CatanColor;
import shared.model.Chat;
import shared.model.Line;
import shared.model.player.Player;


/**
 * Chat controller implementation
 */
public class ChatController extends Controller implements IChatController {

	public ChatController(IChatView view) {
		
		super(view);
	}

	@Override
	public IChatView getView() {
		return (IChatView)super.getView();
	}

	@Override
	public void sendMessage(String message) {
		try{
			GameManager.getInstance().sendChat(GameManager.getInstance().getPlayerInfo().getPlayerIndex(), message);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(GameManager.getInstance().isGameEnd()){
			return;
		}
		if(GameManager.getInstance().isStartingUp()){
			return;
		}
		Chat chat = GameManager.getInstance().getModel().getChat();
		ArrayList<Line> lines = chat.getLines();
		ArrayList<LogEntry> entries = new ArrayList<LogEntry>();
		for(int i = 0; i < lines.size(); i++){
			CatanColor color = null;
			String source = lines.get(i).getSource();
			ArrayList<Player> players = GameManager.getInstance().getModel().getPlayers();
			for(int j = 0; j < players.size(); j++){
				if(players.get(j).getName().equals(source)){
					color = players.get(j).getPlayerColor();
				}
			}
			LogEntry entry = new LogEntry(color, lines.get(i).getMessage());
			entries.add(entry);
		}
		getView().setEntries(entries);
	}
	

}

