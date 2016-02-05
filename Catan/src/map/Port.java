package map;

import java.util.HashSet;

import shared.definitions.PortType;
import shared.locations.VertexLocation;



public class Port {

	private PortType type;
	private VertexLocation loc;
	
	
	
	
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

	/**
	 * @return the loc
	 */
	public VertexLocation getLoc() {
		return loc;
	}

	/**
	 * @param loc the loc to set
	 */
	public void setLoc(VertexLocation loc) {
		this.loc = loc;
	}
	
	
	
	
	
}
