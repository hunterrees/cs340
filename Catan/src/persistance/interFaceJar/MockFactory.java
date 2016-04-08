package persistance.interFaceJar;

public class MockFactory implements AbstractFactory{

	@Override
	public UserDAO createUserDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameDAO createGameDAO() {
		// TODO Auto-generated method stub
		return new MockGameDAO();
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "mock";
	}

}
