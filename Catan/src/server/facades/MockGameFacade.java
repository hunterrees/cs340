package server.facades;

import client.server.ServerException;

public class MockGameFacade implements GameFacadeInterface{

	@Override
	public String getModel(int gameID) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addAI(String json, int gameID) throws ServerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] listAIs() throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

}
