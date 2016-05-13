package org.daoObjects;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

// It tells Hibernate that it needs to store this information
@Entity
public class PlayerDAO {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Column(unique=true)
	private String username;
	private String password;
	private long points;
	@ManyToMany
	@JoinTable(name = "players_challenges")
	@Cascade(CascadeType.DELETE)
	private List<ChallengeDAO> doneChallenges;

	public PlayerDAO(){
		points = (long)0;
		doneChallenges = new ArrayList<ChallengeDAO>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public List<ChallengeDAO> getDoneChallenges() {
		return doneChallenges;
	}

	public void setDoneChallenges(List<ChallengeDAO> doneChallenges) {
		this.doneChallenges = doneChallenges;
	}
	
}
