package client.login;

import client.base.*;
import client.misc.*;
import gameManager.GameManager;
import model.GameException;

import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.reflect.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;


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
		} catch (GameException e) {
			getLoginView().closeModal();
			loginAction.execute();
			System.out.println(e.getMessage());
			e.printStackTrace();
		}		
	}

	@Override
	public void register() {
		String username = getLoginView().getRegisterUsername();
		String password = getLoginView().getRegisterPassword();
		try{
			GameManager.getInstance().userRegister(username, password);
			getLoginView().closeModal();
			loginAction.execute();
		}catch(GameException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
	}

}

