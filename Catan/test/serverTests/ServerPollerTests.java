package serverTests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import client.gameManager.GameManager;
import client.server.MockServerProxy;
import client.server.ServerInterface;
import client.server.ServerPoller;
import client.translators.ModelTranslator;
import shared.model.GameModel;
// test
public class ServerPollerTests {

	@Test
	public void pollerChange() throws IOException {
		System.out.println("Testing Server Poller");
		ServerInterface server = new MockServerProxy();
		final GameManager manager = GameManager.getInstance();
		manager.setServer(server);
		GameModel model = null;
		try{
			File file = new File("translatorTests/pollerTest1.txt");
			String jsonText = FileUtils.readFileToString(file);
			ModelTranslator translator = new ModelTranslator();
			model = translator.getModelfromJSON(jsonText);
		}catch(Exception e){
			e.printStackTrace();
		}
		manager.setModel(model);
		assertTrue(manager.getModel().getVersion() == 0);
		assertTrue(manager.getModel().getLog().getLines().size() == 24);
		ServerPoller poller = new ServerPoller(server, manager);
		
		new Timer().schedule(new TimerTask(){

			@Override
			public void run() {
				assertTrue(manager.getModel().getVersion() == 1);
				assertTrue(manager.getModel().getLog().getLines().size() == 24);
			}
			
		}, 3000);
		
		new Timer().schedule(new TimerTask(){

			@Override
			public void run() {
				assertTrue(manager.getModel().getVersion() == 1);
				assertTrue(manager.getModel().getLog().getLines().size() == 24);
			}
			
		}, 5500);
	}
	
	@Test
	public void pollerConstant(){
		ServerInterface server = new MockServerProxy();
		final GameManager manager = GameManager.getInstance();
		manager.setServer(server);
		GameModel model = null;
		try{
			File file = new File("translatorTests/pollerTest2.txt");
			String jsonText = FileUtils.readFileToString(file);
			ModelTranslator translator = new ModelTranslator();
			model = translator.getModelfromJSON(jsonText);
		}catch(Exception e){
			e.printStackTrace();
		}
		manager.setModel(model);
		assertTrue(manager.getModel().getVersion() == 1);
		assertTrue(manager.getModel().getLog().getLines().size() == 24);
		ServerPoller poller = new ServerPoller(server, manager);
		
		new Timer().schedule(new TimerTask(){

			@Override
			public void run() {
				assertTrue(manager.getModel().getVersion() == 1);
				assertTrue(manager.getModel().getLog().getLines().size() == 24);
			}
			
		}, 3000);
		
		new Timer().schedule(new TimerTask(){

			@Override
			public void run() {
				assertTrue(manager.getModel().getVersion() == 1);
				assertTrue(manager.getModel().getLog().getLines().size() == 24);
			}
			
		}, 5500);
		
		new Timer().schedule(new TimerTask(){

			@Override
			public void run() {
				assertTrue(manager.getModel().getVersion() == 1);
				assertTrue(manager.getModel().getLog().getLines().size() == 24);
			}
			
		}, 7750);
	}

}
