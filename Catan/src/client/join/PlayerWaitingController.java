package client.join;

import java.util.Observable;

import client.base.*;
import gameManager.GameManager;


/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends Controller implements IPlayerWaitingController {

	public PlayerWaitingController(IPlayerWaitingView view) {

		super(view);
	}

	@Override
	public IPlayerWaitingView getView() {

		return (IPlayerWaitingView)super.getView();
	}

	@Override
	public void start() {
		try{
			GameManager.getInstance().listAIs();
		}catch(Exception e){
			
		}
		getView().showModal();
	}

	@Override
	public void addAI() {
		try{
			GameManager.getInstance().addAI(getView().getSelectedAI());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
	}

}

