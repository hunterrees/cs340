package phase3Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import client.server.ServerException;
import client.translators.ModelTranslator;
import server.ServerManager;
import server.ServerTranslator;
import server.commands.game.GetModel;
import shared.model.GameModel;

public class GetModelTest {

	@Test
	public void test() {
		ServerManager.getInstance().testing();
		System.out.println("Testing get model command");
		GetModel getModel = new GetModel(0, "");
		String json = "";
		try {
			json = (String) getModel.execute();
		} catch (ServerException e) {
			fail();
		}
		try{
			ModelTranslator translator = new ModelTranslator();
			GameModel model = translator.getModelfromJSON(json);
			ServerTranslator serverTranslate = new ServerTranslator(model);
			String newJson = serverTranslate.translate();
			assertTrue(newJson.equals(json));
		}catch(Exception e){
			fail();
		}
	}

}
