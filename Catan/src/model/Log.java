package model;

import java.util.ArrayList;

public class Log {
	
	private ArrayList<Line> lines;

	public Log(ArrayList<Line> lines){
		this.lines = lines;
	};
	
	public Log(){
		
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
