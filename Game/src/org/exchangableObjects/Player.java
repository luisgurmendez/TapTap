package org.exchangableObjects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Player {

	private String username;
	private String password;
	private long points;
	private long matchesPlayed;
	private long matchesWon;
	
	public Player(){}
	
	public Player(String username, String password){
		this.username = username;
		this.password = password;
		points = (long)0;
		matchesPlayed = (long)0;
		matchesWon = (long)0;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getPoints() {
		return points;
	}

	public void setPoints(long points) {
		this.points = points;
	}
	
	public long getMatchesPlayed() {
		return matchesPlayed;
	}

	public void setMatchesPlayed(long matchesPlayed) {
		this.matchesPlayed = matchesPlayed;
	}

	public long getMatchesWon() {
		return matchesWon;
	}

	public void setMatchesWon(long matchesWon) {
		this.matchesWon = matchesWon;
	}

	@Override
	public boolean equals(Object other){
		boolean areTheyEqual = false;
		if (other instanceof Player){
			Player playerObject = (Player)other;
			if (playerObject.getUsername().equals(this.getUsername()) 
					&& playerObject.getPassword().equals(this.getPassword()) 
							&& playerObject.getPoints() == this.getPoints()){
				areTheyEqual = true;
			}
		}
		return areTheyEqual;
	}

}
