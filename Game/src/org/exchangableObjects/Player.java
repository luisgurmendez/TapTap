package org.exchangableObjects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Player {

	private String username;
	private String password;
	private long points;
	
	public Player(){}
	
	public Player(String username, String password){
		this.username = username;
		this.password = password;
		points = (long)0;
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
