package client.states;

import shared.locations.HexLocation;
import shared.model.map.Map;

/**
 * Created by Brian on 16/2/17.
 */
public class Robbing extends State {
	
	
    public Robbing(Map map) {
        super(map);
       
        
      
    }

	/* (non-Javadoc)
	 * @see client.states.State#canPlaceRobber(shared.locations.HexLocation)
	 */
	@Override
	public boolean canPlaceRobber(HexLocation hexLoc) {
		// TODO Auto-generated method stub
		return map.canPlaceRobber(hexLoc);
	}

	/* (non-Javadoc)
	 * @see client.states.State#placeRobber(shared.locations.HexLocation, int)
	 */
	@Override
	public void placeRobber(HexLocation hexLoc, int playerID) {
		// TODO Auto-generated method stub
		
	}
    
    
}
