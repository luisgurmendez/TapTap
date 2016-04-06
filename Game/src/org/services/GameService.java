package org.services;



import org.models.Game;

public class GameService {

	private Game game;

	public GameService() {
	}



	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

}