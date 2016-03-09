package shared.model;

@SuppressWarnings("serial")
public class GameException extends Exception{
	
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
