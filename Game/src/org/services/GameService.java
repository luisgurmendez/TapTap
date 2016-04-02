package org.services;

import java.util.HashMap;

import org.models.Game;

public class GameService {

	private Game game;

	public GameService() {
		game = new Game(20, new String[5][5], new HashMap<Integer, String>());
	}

	public String returnGame(int id) {
		String result = null;
		if (id == 1) {
			result = "{\"action\":\"startGame\", \"time\":" + game.getTime()
					+ ", \"matrixSize\":" + game.getMatrix().length
					+ ", \"color\":\"Blue\", \"userId\":" + id + "}";
			game.getListOfUsers().put(id, "Blue");
		} else if (id == 2) {
			result = "{\"action\":\"startGame\",\"time\":" + game.getTime()
					+ ", \"matrixSize\":" + game.getMatrix().length
					+ ", \"color\":\"Red\", \"userId\":" + id + "}";
			game.getListOfUsers().put(id, "Red");
		}
		return result;
	}

	public String paintSpot(int x, int y, int userId) {
		String color = game.getListOfUsers().get(userId);
		game.getMatrix()[x][y] = color;
		return color;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

}