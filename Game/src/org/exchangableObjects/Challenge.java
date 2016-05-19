package org.exchangableObjects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Challenge {

	private String name;
	private byte[] image;
	private long points;
	private int level; // How difficult it is. The lower the number, the easier it is.
	
	public Challenge(){}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public long getPoints() {
		return points;
	}

	public void setPoints(long points) {
		this.points = points;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	@Override
	public boolean equals(Object object){
		boolean areTheyEqual = false;
		if (object instanceof Challenge){
			Challenge challengeObject = (Challenge) object; 
			if (this.getImage().equals(challengeObject.getImage())
					&& (this.getLevel() == challengeObject.getLevel())
					&& this.getName().equals(challengeObject.getName())
					&& (this.getPoints() == challengeObject.getPoints())){
				areTheyEqual = true;
			}
		}
		return areTheyEqual;
	}
	
}
