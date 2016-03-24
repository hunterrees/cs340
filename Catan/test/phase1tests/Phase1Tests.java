package phase1tests;

import org.junit.* ;
import static org.junit.Assert.* ;

public class Phase1Tests{
	
	@Before
	public void setup() {
	}
	
	@After
	public void teardown() {
	}
	// add
	@Test
	public void test_1() {
		assertEquals("OK", "OK");
		assertTrue(true);
		assertFalse(false);
	}

	public static void main(String[] args) {
		System.out.println("Starting Tests\n");
		String[] testClasses = new String[] {
				"canDoTests.CanAcceptTrade", "canDoTests.CanBuildRoad", "canDoTests.CanBuildCity", "canDoTests.CanBuildSettlement", 
				"canDoTests.CanBuyDevCard", "canDoTests.CanDiscardCards", "canDoTests.CanFinishTurn", "canDoTests.CanMaritimeTrade",
				"canDoTests.CanOfferTrade", "canDoTests.CanPlaceRobber", "canDoTests.CanRollNumber", "canDoTests.CanUseMonopoly",
				"canDoTests.CanUseMonument", "canDoTests.CanUseRoadBuilder", "canDoTests.CanUseSoldier", "canDoTests.CanUseYearOfPlenty",
				"serverTests.ServerProxyTests", "serverTests.ServerPollerTests", "serverTests.InitializeModel", "phase3Tests.LoginTest",
				"phase3Tests.RegisterTest", "phase3Tests.JoinGameTest", "phase3Tests.CreateGameTest", "phase3Tests.ListGamesTest", 
				"phase3Tests.ListAITest", "phase3Tests.AddAITest", "phase3Tests.GetModelTest", "phase3Tests.SendChatTest"
		}; 

		org.junit.runner.JUnitCore.main(testClasses);
	}
	
}

