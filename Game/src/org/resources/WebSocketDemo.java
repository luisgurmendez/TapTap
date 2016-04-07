package org.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;
import org.models.Game;

@ServerEndpoint("/gameWS")
public class WebSocketDemo {

	private static Set<Session> allSessions = Collections
			.synchronizedSet(new HashSet<Session>());
	private static Map<Session, Game> sessionGame = new HashMap<Session, Game>();
	private static List<Game> games = new ArrayList<Game>();
	private static int counter = 0;

	@OnOpen
	public void openConnection(Session userSession) {
		allSessions.add(userSession);
		Game newGame = availableGame();
		if (newGame != null) {
			newGame.setWaiting(false);
			newGame.getSessions()[1] = userSession;
			newGame.getListOfUsers()[1] = counter;
			counter++;
			sessionGame.put(userSession, newGame);
			sendArrivals(newGame);
		} else {
			int[] array = new int[2];
			array[0] = counter;
			newGame = new Game(30, new String[5][5], array, userSession);
			games.add(newGame);
			counter++;
			sessionGame.put(userSession, newGame);
		}
	}

	private Game availableGame() {
		Game returnGame = null;
		for (Game game : games) {
			if (game.isWaiting() == true) {
				returnGame = game;
				break;
			}
		}
		return returnGame;
	}

	@OnMessage
	public void gotAMessage(Session session, String msg) {
		if (msg != null) {
			JSONObject json = new JSONObject(msg);
			System.out.println(json.toString());
			String action = json.getString("action");
			String textToSend = null;
			if (action.equals("sendSpecifications")) {
				sendSpecifications(session);
			} else if (action.equals("startGame")) {
				Game thisGame = findGame(session);
				int index = 0;
				if (session.equals(thisGame.getSessions()[index])) {
					thisGame.getReadyToPlay()[index] = true;
					if (thisGame.getReadyToPlay()[index + 1] == true) {
						sendStartGame(thisGame);
					}
				} else {
					thisGame.getReadyToPlay()[index + 1] = true;
					if (thisGame.getReadyToPlay()[index] == true) {
						sendStartGame(thisGame);
					}
				}
			} else if (action.equals("paintSpot")) {
				textToSend = "{\"action\":\"paintedSpot\", \"x\":"
						+ json.getInt("x") + ", \"y\":" + json.getInt("y")
						+ ", \"userId\":" + json.getInt("userId") + "}";
			} else if (action.equals("timeIsOver")) {
				textToSend = "{\"action\":\"timeIsOver\"}";
			}
			if (textToSend != null) {
				Game newGame = sessionGame.get(session);
				Session otherSession = newGame.getOtherSession(session);
				try {
					otherSession.getBasicRemote().sendText(textToSend);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}

	@OnClose
	public void closeConnection(Session userSession) {
		allSessions.remove(userSession);
		Game game = findGame(userSession);
		// MANEJAR CUANDO SE DESCONECTA Y ESTA ESPERANDO JUGADOR O ESTA JUGANDO
		game.setRunning(false);

	}

	private void sendStartGame(Game game) {
		try {
			for (Session s : game.getSessions()) {
				String textToSend = "{\"action\":\"startGame\"}";
				System.out.println("Server Send: " + textToSend);
				s.getBasicRemote().sendText(textToSend);
			}
			game.setRunning(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void sendArrivals(Game game) {
		String json = "";
		try {
			for (int i = 0; i < game.getSessions().length; i++) {
				json = "{\"action\":\"opponentEnterRoom\", \"userId\":"
						+ game.getOtherUserId(game.getListOfUsers()[i]) + "}";
				System.out.println(json);
				game.getSessions()[i].getBasicRemote().sendText(json);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void sendSpecifications(Session userSession) {

		Game newGame = findGame(userSession);
		for (int i = 0; i < newGame.getListOfUsers().length; i++) {
			if (newGame.getSessions()[i].equals(userSession)) {
				try {
					int id;
					if (userSession.equals(newGame.getSessions()[0])) {
						id = newGame.getListOfUsers()[0];
					} else {
						id = newGame.getListOfUsers()[1];
					}
					String json = "{\"action\":\"specifications\", \"time\":"
							+ newGame.getTime() + ", \"matrixSize\":"
							+ newGame.getMatrix().length + ", \"userId\":" + id
							+ "}";
					userSession.getBasicRemote().sendText(json);
					break;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private Game findGame(Session userSession) {
		return sessionGame.get(userSession);
	}

}