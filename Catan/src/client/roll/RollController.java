package client.roll;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import client.base.*;
import gameManager.GameManager;
import model.Dice;
import shared.definitions.GameState;


/**
 * Implementation for the roll controller
 */
public class RollController extends Controller implements IRollController {

	private IRollResultView resultView;
	private Timer timer1;
	private Timer timer2;
	private Timer timer3;

	/**
	 * RollController constructor
	 * 
	 * @param view Roll view
	 * @param resultView Roll result view
	 */
	public RollController(IRollView view, IRollResultView resultView) {

		super(view);
		
		setResultView(resultView);
	}
	
	public IRollResultView getResultView() {
		return resultView;
	}
	public void setResultView(IRollResultView resultView) {
		this.resultView = resultView;
	}

	public IRollView getRollView() {
		return (IRollView)getView();
	}
	
	@Override
	public void rollDice() {
		timer1.cancel();
		timer2.cancel();
		timer3.cancel();
		int number = Dice.rollDice();
		try{
			GameManager.getInstance().rollNumber(GameManager.getInstance().getPlayerInfo().getId(), number);
		}catch(Exception e){
			e.printStackTrace();
		}
		getResultView().setRollValue(number);
		getResultView().showModal();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(GameManager.getInstance().getGameState() == GameState.rolling && GameManager.getInstance().isMyTurn()){
			getRollView().setMessage("Rolling automatically in...3 seconds");
			getRollView().showModal();
			timer1 = new Timer();
			timer1.schedule(new TimerTask(){

				@Override
				public void run() {
					getRollView().setMessage("Rolling automatically in...2 seconds");
					
				}
				
			}, 1000);
			
			timer2 = new Timer();
			timer2.schedule(new TimerTask(){

				@Override
				public void run() {
					getRollView().setMessage("Rolling automatically in...1 seconds");
					
				}
				
			}, 2000);
			
			timer3 = new Timer();
			timer3.schedule(new TimerTask(){

				@Override
				public void run() {
					getRollView().setMessage("Rolling automatically");
					rollDice();
				}
				
			}, 3000);
		}
	}

}

