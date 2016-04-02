package org.models;

import java.util.Map;

public class Game {
	
	private float time;
	private String[][] matrix;
	private Map<Integer, String> listOfUsers;
	
	public Game(float time, String[][] matrix, Map<Integer, String> listOfUsers) {
		super();
		this.time = time;
		this.matrix = matrix;
		this.listOfUsers = listOfUsers;
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
	public Map<Integer, String> getListOfUsers() {
		return listOfUsers;
	}
	public void setListOfUsers(Map<Integer, String> listOfUsers) {
		this.listOfUsers = listOfUsers;
	}
	
}