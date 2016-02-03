package translators;

import translators.games.*;
import translators.user.*;
import server.ServerPoller;
import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.HexLocation;
import translators.game.*;
import translators.moves.*;


//Test main to see if translator objects produce desired output
public class Test {

	public Test(){};
	
	public static void main(String[] args){
		GenericTranslator translate = new MovesBuildRoadTranslator(0, new HexLocation(2,1), EdgeDirection.South, true);
		System.out.println(translate.translate());
	}
}
	
