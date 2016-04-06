package org.models;

import javax.websocket.Session;

public class Game {
	
	private float time;
	private String[][] matrix;
	private int[] listOfUsers;
	private Session[] sessions;
	private boolean waiting;
	private boolean[] readyToPlay;
	private boolean running;
	private Session gameOwner;
	
	public Game(float time, String[][] matrix, int[] listOfUsers, Session session) {
		super();
		this.time = time;
		this.matrix = matrix;
		this.listOfUsers = listOfUsers;
		this.sessions = new Session[2];
		sessions[0] = session;
		readyToPlay = new boolean[2];
		readyToPlay[0] = false;
		readyToPlay[1] = false;
		this.running=false;
		this.waiting=true;
		this.gameOwner = session;
	}
	
	
	
	public Session getGameOwner() {
		return gameOwner;
	}



	public void setGameOwner(Session gameOwner) {
		this.gameOwner = gameOwner;
	}



	public boolean isRunning() {
		return running;
	}


	public void setRunning(boolean running) {
		this.running = running;
	}


	public Session getOtherSession(Session session){
		Session sessionReturn=null;
		for(Session s: sessions){
			if(!s.equals(session)){
				sessionReturn = s;
			}
		}
		return sessionReturn;
	}
	
	public int getOtherUserId(int id){
		int returnId=-1;
		for(int i: listOfUsers){
			if(i != id){
				returnId=i;
			}
		}
		return returnId;
	}
	
	public float getTime() {
		return time;
	}
	public void setTime(float time) {
		this.time = time;
	}
	public String[][] getMatrix() {
		return matrix;
	}
	public void setMatrix(String[][] matrix) {
		this.matrix = matrix;
	}

	public int[] getListOfUsers() {
		return listOfUsers;
	}

	public void setListOfUsers(int[] listOfUsers) {
		this.listOfUsers = listOfUsers;
	}

	public Session[] getSessions() {
		return sessions;
	}

	public void setSessions(Session[] sessions) {
		this.sessions = sessions;
	}

	public boolean isWaiting() {
		return waiting;
	}

	public void setWaiting(boolean waiting) {
		this.waiting = waiting;
	}

	public boolean[] getReadyToPlay() {
		return readyToPlay;
	}

	public void setReadyToPlay(boolean[] readyToPlay) {
		this.readyToPlay = readyToPlay;
	}

}