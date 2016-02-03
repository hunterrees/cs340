package map;

import java.util.HashSet;

import shared.definitions.PortType;



public class Port {

	private PortType type;
	
	
	
	
	
	public Port(PortType type) {
		this.type = type;
	}

	/**
	 * @return the PortType (wood, wheat, 3 for 1, etc..)
	 */
	public PortType getType() {
		return type;
	}

	/**
	 * @param type the PortType to set
	 */
	public void setType(PortType type) {
		this.type = type;
	}
	
	
	
	
	
}
