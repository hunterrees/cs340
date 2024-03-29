package client.base;

import java.util.Observable;
import java.util.Observer;

import client.gameManager.GameManager;

/**
 * Base class for controllers
 */
public abstract class Controller implements IController, Observer
{
	
	private IView view;
	
	protected Controller(IView view)
	{
		GameManager.getInstance().addObserver(this);
		setView(view);
	}
	
	private void setView(IView view)
	{
		this.view = view;
	}
	
	@Override
	public IView getView()
	{
		return this.view;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		System.out.println("\n\n\nIN THE CONTROLLER UPDATE\n\n\n");
		
	}
}

