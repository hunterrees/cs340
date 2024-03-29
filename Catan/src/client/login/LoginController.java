package client.login;

import client.base.*;
import client.gameManager.GameManager;
import client.misc.*;
import shared.model.GameException;

import java.util.*;


/**
 * Implementation for the login controller
 */
public class LoginController extends Controller implements ILoginController{

	private IMessageView messageView;
	private IAction loginAction;
	
	/**
	 * LoginController constructor
	 * 
	 * @param view Login view
	 * @param messageView Message view (used to display error messages that occur during the login process)
	 */
	public LoginController(ILoginView view, IMessageView messageView) {

		super(view);
		this.messageView = messageView;
	}
	
	public ILoginView getLoginView() {
		
		return (ILoginView)super.getView();
	}
	
	public IMessageView getMessageView() {
		
		return messageView;
	}
	
	/**
	 * Sets the action to be executed when the user logs in
	 * 
	 * @param value The action to be executed when the user logs in
	 */
	public void setLoginAction(IAction value) {
		
		loginAction = value;
	}
	
	/**
	 * Returns the action to be executed when the user logs in
	 * 
	 * @return The action to be executed when the user logs in
	 */
	public IAction getLoginAction() {
		
		return loginAction;
	}

	@Override
	public void start() {
		getLoginView().showModal();
	}

	@Override
	public void signIn() {
		String username = getLoginView().getLoginUsername();
		String password = getLoginView().getLoginPassword();
		try {
			GameManager.getInstance().userLogin(username, password);
			getLoginView().closeModal();
			loginAction.execute();
		} catch (Exception e) {
			e.printStackTrace();
			getMessageView().setTitle("Login Error");
			getMessageView().setMessage("Login failed - bad password or username");
			getMessageView().showModal();
		}		
	}

	@Override
	public void register() {
		String username = getLoginView().getRegisterUsername();
		String password = getLoginView().getRegisterPassword();
		String passAgain = getLoginView().getRegisterPasswordRepeat();
		if(username.length() < 3 || username.length() > 7){
			getMessageView().setTitle("Register Error");
			getMessageView().setMessage("Register failed - username must be 3 - 7 characters");
			getMessageView().showModal();
			return;
		}else if(password.length() < 5){
			getMessageView().setTitle("Register Error");
			getMessageView().setMessage("Register failed - password must be 5 or more characters");
			getMessageView().showModal();
			return;
		}else if(!password.equals(passAgain)){
			getMessageView().setTitle("Register Error");
			getMessageView().setMessage("Register failed - passwords must match");
			getMessageView().showModal();
			return;
		}else{
			 for (char c : password.toCharArray())
             {
                 if (!Character.isLetterOrDigit(c)
                         && c != '_' && c != '-')
                 {
                	 getMessageView().setTitle("Register Error");
                	 getMessageView().setMessage("Register failed - password contains invalid characters");
                	 getMessageView().showModal();
                     return;
                 }
             }
		}
		try{
			GameManager.getInstance().userRegister(username, password);
			getLoginView().closeModal();
			loginAction.execute();
		}catch(GameException e){
			e.printStackTrace();
			getMessageView().setTitle("Register Error");
       	 	getMessageView().setMessage("Register failed - user already exists");
			getMessageView().showModal();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
	}

}

