package serverTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.ServerProxy;
import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

public class ServerProxyTests {
	
	ServerProxy server = new ServerProxy("localhost", 8081);
	//if the response code isn't 200, the ServerProxy will throw an exception and thus fail the test
	@Before
	public void login() {
		
		try{
			server.userLogin("Sam", "sam");
		}catch(Exception e){
			e.printStackTrace(); 
			fail();
		}
		
		try{
			server.joinGame(0, "orange");
		}catch(Exception e){
			e.printStackTrace(); 
			fail();
		}
	}
	
	@Test
	public void rollNumber(){
		
		try{
			server.rollNumber(0, 11);
		}catch(Exception e){
			e.printStackTrace(); 
			fail();
		}
	}
	
	@Test
	public void sendChat(){
		
		try{
			server.sendChat(0, "test");
		}catch(Exception e){
			e.printStackTrace(); 
			fail();
		}
	}
	
	@Test
	public void robPlayer(){
		
		try{
			server.robPlayer(0, 1, new HexLocation(1,1));
		}catch(Exception e){
			e.printStackTrace(); 
			e.printStackTrace(); e.printStackTrace(); 
			fail();
		}
	}
	
	@Test
	public void finishTurn(){
		
		try{
			server.finishTurn(0);
		}catch(Exception e){
			e.printStackTrace();
			e.printStackTrace(); 
			fail();
		}
	}
	
	@Test
	public void buyDevCard(){
		
		try{
			server.buyDevCard(0);
		}catch(Exception e){
			e.printStackTrace(); 
			fail();
		}
	}
	
	@Test
	public void yearOfPlenty(){
		
		try{
			server.yearOfPlenty(0, ResourceType.BRICK, ResourceType.WOOD);
		}catch(Exception e){
			e.printStackTrace(); 
			fail();
		}
	}
	
	@Test
	public void roadBuilding(){
		
		try{
			server.roadBuilding(0, new EdgeLocation(new HexLocation(1,1), EdgeDirection.NorthEast), 
					new EdgeLocation(new HexLocation(1,1), EdgeDirection.NorthWest));
		}catch(Exception e){
			e.printStackTrace(); 
			fail();
		}
	}
	
	@Test
	public void soldier(){
		
		try{
			server.knight(0, 1, new HexLocation(1,1));
		}catch(Exception e){
			e.printStackTrace(); 
			fail();
		}
	}
	
	@Test
	public void monopoly(){
		
		try{
			server.monopoly(0, ResourceType.BRICK);
		}catch(Exception e){
			e.printStackTrace(); 
			fail();
		}
	}
	
	@Test
	public void monument(){
		
		try{
			server.monument(0);
		}catch(Exception e){
			e.printStackTrace(); 
			fail();
		}
	}
	
	@Test
	public void buildRoad(){
		
		try{
			server.buildRoad(0, new EdgeLocation(new HexLocation(1,1), EdgeDirection.NorthEast), true);
		}catch(Exception e){
			e.printStackTrace(); 
			fail();
		}
	}
	
	@Test
	public void buildSettlement(){
		
		try{
			server.buildSettlment(0, new VertexLocation(new HexLocation(1,1), VertexDirection.SouthWest), true);
		}catch(Exception e){
			e.printStackTrace(); 
			fail();
		}
	}
	
	@Test
	public void buildCity(){
		
		try{
			server.buildCity(0, new VertexLocation(new HexLocation(1,1), VertexDirection.SouthWest));
		}catch(Exception e){
			e.printStackTrace(); 
			fail();
		}
	}
	
	@Test
	public void offerTrade(){
		
		ArrayList<ResourceType> resourceGive = new ArrayList<ResourceType>();
		ArrayList<ResourceType> resourceReceive = new ArrayList<ResourceType>();
		resourceGive.add(ResourceType.BRICK);
		resourceReceive.add(ResourceType.SHEEP);
		try{
			server.offerTrade(0, resourceGive, resourceReceive, 1);
		}catch(Exception e){
			e.printStackTrace(); 
			fail();
		}
	}
	
	@Test
	public void acceptTrade(){
		
		try{
			server.acceptTrade(1, false);
		}catch(Exception e){
			e.printStackTrace(); 
			fail();
		}
	}
	
	@Test
	public void maritimeTrade(){
		
		try{
			server.maritimeTrade(0, 4, ResourceType.WHEAT, ResourceType.ORE);
		}catch(Exception e){
			e.printStackTrace(); 
			fail();
		}
	}
	
	@Test
	public void discardCards(){
		ArrayList<ResourceType> resources = new ArrayList<ResourceType>();
		resources.add(ResourceType.BRICK);
		try{
			server.discardCards(0, resources);
		}catch(Exception e){
			e.printStackTrace(); 
			fail();
		}
	}

}
