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
	
	@Test
	public void test_1() {
		assertEquals("OK", "OK");
		assertTrue(true);
		assertFalse(false);
	}

	public static void main(String[] args) {
		//add new JUnit files to run here
		String[] testClasses = new String[] {
				"canDoTests.CanAcceptTrade", "canDoTests.CanBuildRoad", "canDoTests.CanBuildCity", "canDoTests.CanBuildSettlement", 
				"canDoTests.CanBuyDevCard", "canDoTests.CanDiscardCards", "canDoTests.CanFinishTurn", "canDoTests.CanMaritimeTrade",
				"canDoTests.CanOfferTrade", "canDoTests.CanPlaceRobber", "canDoTests.CanRollNumber", "canDoTests.CanUseMonopoly",
				"canDoTests.CanUseMonument", "canDoTests.CanUseRoadBuilder", "canDoTests.CanUseSoldier", "canDoTests.CanUseYearOfPlenty",
				"serverTests.ServerProxy", "serverTests.ServerPoller", "serverTests.InitializeModel"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}
	
}

