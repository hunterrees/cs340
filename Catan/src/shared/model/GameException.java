package shared.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GameException extends Exception implements Serializable{
	
	public GameException() {
		return;
	}

	public GameException(String message) {
		super(message);
	}

	public GameException(Throwable throwable) {
		super(throwable);
	}

	public GameException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
