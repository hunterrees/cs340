package model;

import java.util.ArrayList;

public class Chat {
	
	private ArrayList<Line> lines;

	public Chat(ArrayList<Line> lines){
		this.lines = lines;
	};
	
	public Chat(){
		
	}
	
	public void addLine(Line line){
		lines.add(line);	
	}

	public ArrayList<Line> getLines() {
		return lines;
	}

	public void setLines(ArrayList<Line> lines) {
		this.lines = lines;
	}
}
