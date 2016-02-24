package client.communication;

import java.util.*;

import client.base.*;
import gameManager.GameManager;
import model.Chat;
import model.Line;
import model.Log;
import player.Player;
import shared.definitions.*;


/**
 * Game history controller implementation
 */
public class GameHistoryController extends Controller implements IGameHistoryController {

	public GameHistoryController(IGameHistoryView view) {
		
		super(view);
		
		initFromModel();
	}
	
	@Override
	public IGameHistoryView getView() {
		
		return (IGameHistoryView)super.getView();
	}
	
	private void initFromModel() {
		if(GameManager.getInstance().isStartingUp()){
			return;
		}
		Log log = GameManager.getInstance().getModel().getLog();
		ArrayList<Line> lines = log.getLines();
		ArrayList<LogEntry> entries = new ArrayList<LogEntry>();
		for(int i = 0; i < lines.size(); i++){
			CatanColor color = CatanColor.RED;
			String source = lines.get(i).getSource();
			ArrayList<Player> players = GameManager.getInstance().getModel().getPlayers();
			for(int j = 0; i < players.size(); i++){
				if(players.get(j).getName().equals(source)){
					color = players.get(j).getPlayerColor();
				}
			}
			LogEntry entry = new LogEntry(color, lines.get(i).getMessage());
			entries.add(entry);
		}
		getView().setEntries(entries);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(GameManager.getInstance().isStartingUp()){
			return;
		}
		initFromModel();
	}
	
}

