package serverTests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import gameManager.GameManager;
import model.GameModel;
import server.MockServerProxy;
import server.ServerInterface;
import server.ServerPoller;
import translators.ModelTranslator;

public class ServerPollerTests {

	@Test
	public void pollerChange() throws IOException {
		ServerInterface server = new MockServerProxy();
		final GameManager manager = new GameManager();
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
		final GameManager manager = new GameManager();
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
