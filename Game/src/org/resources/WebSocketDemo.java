package org.resources;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;
import org.services.GameService;

@ServerEndpoint("/gameWS")
public class WebSocketDemo {

	private static Set<Session> allSessions = Collections
			.synchronizedSet(new HashSet<Session>());
	private static GameService service = new GameService();
	private static boolean gameIsRunning;

	@OnOpen
	public void openConnection(Session userSession) {
		allSessions.add(userSession);
		if (allSessions.size() == 2) {
			startGame();
		}
	}

	@OnMessage
	public void gotAMessage(Session session, String msg) {
		if (msg != null) {
			if (gameIsRunning) {
				JSONObject json = new JSONObject(msg);
				String action = json.getString("action");
				String textToSend = null;
				if (action.equals("paintSpot")) {
					String color = service.paintSpot(json.getInt("x"),
							json.getInt("y"), json.getInt("userId"));
					textToSend = "{\"action\":\"paintedSpot\", \"x\":"
							+ json.getInt("x") + ", \"y\":" + json.getInt("y")
							+ ", \"color\":\"" + color + "\"}";
				} else if (action.equals("timeIsOver")) {
					textToSend = "{\"action\":\"timeIsOver\"}";
					gameIsRunning = false;
				}
				if (textToSend != null) {
					for (Session session1 : allSessions) {
						if (!session1.equals(session)) {
							try {
								session1.getBasicRemote().sendText(textToSend);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}

	@OnClose
	public void closeConnection(Session userSession) {
		allSessions.remove(userSession);
	}

	private void startGame() {
		int id = 0;
		for (Session session : allSessions) {
			id += 1;
			try {
				session.getBasicRemote().sendText(service.returnGame(id));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		gameIsRunning = true;
	}

}