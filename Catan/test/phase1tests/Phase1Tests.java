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
		System.out.println("Starting Phase 1 Tests\n");
		String[] testClasses = new String[] {
				"canDoTests.CanAcceptTrade", "canDoTests.CanBuildRoad", "canDoTests.CanBuildCity", "canDoTests.CanBuildSettlement", 
				"canDoTests.CanBuyDevCard", "canDoTests.CanDiscardCards", "canDoTests.CanFinishTurn", "canDoTests.CanMaritimeTrade",
				"canDoTests.CanOfferTrade", "canDoTests.CanPlaceRobber", "canDoTests.CanRollNumber", "canDoTests.CanUseMonopoly",
				"canDoTests.CanUseMonument", "canDoTests.CanUseRoadBuilder", "canDoTests.CanUseSoldier", "canDoTests.CanUseYearOfPlenty",
				"serverTests.ServerProxyTests", "serverTests.ServerPollerTests", "serverTests.InitializeModel", "phase3Tests.AddAITest",
				"phase3Tests.BuildCityTest", "phase3Tests.BuildRoadTest", "phase3Tests.BuildSettlementTest", "phase3Tests.BuyDevCardTest",
				"phase3Tests.CreateGameTest", "phase3Tests.GetModelTest", "phase3Tests.JoinGameTest", "phase3Tests.ListAITest",
				"phase3Tests.ListAITest", "phase3Tests.ListGamesTest", "phase3Tests.LoginTest", "phase3Tests.MonopolyTest",
				"phase3Tests.RegisterTest", "phase3Tests.RoadBuildTest", "phase3Tests.RobPlayerTest", "phase3Tests.SoldierTest",
		}; 

		org.junit.runner.JUnitCore.main(testClasses);
	}
	
}

