package server;

import java.util.Timer;
import java.util.TimerTask;

import gameManager.GameManager;
import model.GameModel;
import translators.ModelTranslator;

/**
 * 
 * Polls the ServerInterface to check for updated game model
 *
 */
public class ServerPoller {
	/**
	 * proxy server the poller polls for new information
	 */
	private ServerInterface server;
	private Timer timer;
	private GameManager gameManager;
	private ModelTranslator translator;
	
	public ServerPoller(ServerInterface server, GameManager gameManager){
		this.server = server;
		this.gameManager = gameManager;
		timer = new Timer();
		timer.scheduleAtFixedRate(new Poll(), 0, 2*1000);
	};
	/**
	 * Allows you to change between ServerProxy and MockServerProxy for testing
	 * @param server
	 */
	public void setServer(ServerInterface server){
		this.server = server;
		return;
	};
	/**
	 * Polls the server for updates in game model, if update is found, poller will update game model
	 */
	public class Poll extends TimerTask{

		@Override
		public void run() {
			try {
				String json = server.getModel();
				GameModel newModel = translator.getModelfromJSON(json);
				if(gameManager.getModel().getVersion() != newModel.getVersion()){
					gameManager.setGameModel(newModel);
				}
			} catch (ServerException e) {
				e.printStackTrace();
			}
		}
		
	}
}
