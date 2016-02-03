package translators;

import translators.games.*;
import translators.user.*;
import server.ServerPoller;
import shared.locations.HexLocation;
import translators.game.*;
import translators.moves.*;


//Test main to see if translator objects produce desired output
public class Test {

	public Test(){};
	
	public static void main(String[] args){
		GenericTranslator translate = new MovesRollNumberTranslator(0, 10);
		System.out.println(translate.translate());
	}
}
	
