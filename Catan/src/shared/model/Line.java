package shared.model;

import java.io.Serializable;

public class Line implements Serializable {

	private String message;
	private String source;
	
	public Line(String source, String message) {
		this.message = message;
		this.source = source;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
}
