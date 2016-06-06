package org.services;

import java.util.List;

import org.daoObjects.ChallengeDAO;
import org.daoObjects.PlayerDAO;
import org.exchangableObjects.Player;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class UserService {

	private static UserService userService = null;
	
	private UserService(){
	}
	
	public static UserService getInstance(){
		if (userService == null){
			userService = new UserService();
		}
		return userService;
	}
	
	// Returns true if the player passed as a parameter is valid
	public Boolean loginUser(Player player){
		boolean validUser = false;
		Session session = getSession();
		String hql = "SELECT p FROM PlayerDAO p WHERE p.username = :username ";
		Query query = session.createQuery(hql);
		query.setParameter("username", player.getUsername());
		@SuppressWarnings("unchecked")
		List<PlayerDAO> results = query.list();
		for (PlayerDAO player2 : results) {
			if (player2.getPassword().equals(player.getPassword())){
				validUser = true;
				break;
			}
		}
		return validUser;
	}
	
	public Boolean registerUser(String username, String password){
		boolean registeredUser = false;
		Session session = getSession();
		if (!userExists(username, session)){
			PlayerDAO player = new PlayerDAO();
			player.setUsername(username);
			player.setPassword(password);
			session.beginTransaction();
			session.save(player);
			session.getTransaction().commit();
			registeredUser = true;
		}
		return registeredUser;
	}
	
	public Player getInformationAboutUser(String username){
		Player player = null;
		Session session = getSession();
		String hql = "SELECT p FROM PlayerDAO p WHERE p.username = :username ";
		Query query = session.createQuery(hql);
		query.setParameter("username", username);
		@SuppressWarnings("unchecked")
		List<PlayerDAO> results = query.list();
		if (results.size()>0){
			PlayerDAO playerFound = results.get(0);
			player = new Player(playerFound.getUsername(), playerFound.getPassword());
			player.setPoints(playerFound.getPoints());
		}
		player.setPassword("");
		return player;
	}
	
	// Returns true if the user exists
	private boolean userExists(String username, Session session){
		boolean userExists = false;
		String hql = "SELECT p FROM PlayerDAO p WHERE p.username = :username ";
		Query query = session.createQuery(hql);
		query.setParameter("username", username);
		@SuppressWarnings("unchecked")
		List<PlayerDAO> results = query.list();
		if (results.size()>0){
			userExists = true;
		}
		return userExists;
	}
	
	public void updateMatchesPlayed(String username) {
		Session session = getSession();
		String hql = "UPDATE PlayerDAO p SET p.matchesPlayed = p.matchesPlayed + 1 "
				+ "WHERE p.username = :username";
		Query query = session.createQuery(hql);
		session.beginTransaction();
		query.setParameter("username", username);
		query.executeUpdate();
		session.getTransaction().commit();
	}
	
	// El score ya me lo pasan completo (puntos pintados + desafío en caso de que se haya cumplido)
	public void updateWinnerInfo(String username, long score,
			boolean challengeCompleted, String challenge) {
		Session session = getSession();
		String hql = "UPDATE PlayerDAO p SET p.matchesPlayed = p.matchesPlayed + 1, "
				+ "p.matchesWon = p.matchesWon + 1, p.points = p.points + :score WHERE p.username = :username";
		Query query = session.createQuery(hql);
		session.beginTransaction();
		query.setParameter("username", username);
		query.setParameter("score", score);
		query.executeUpdate();
		if (challengeCompleted){
			updateChallengeCompleted(username, challenge, session);
		}
		session.getTransaction().commit();
	}
	
	private void updateChallengeCompleted(String username, String challenge, Session session){
		String hql = "SELECT p FROM PlayerDAO p WHERE p.username = :username";
		Query query = session.createQuery(hql);
		query.setParameter("username", username);
		@SuppressWarnings("unchecked")
		List<PlayerDAO> users = query.list();
		String hql2 = "SELECT c FROM ChallengeDAO c WHERE c.name = :challenge";
		Query query2 = session.createQuery(hql2);
		query2.setParameter("challenge", challenge);
		@SuppressWarnings("unchecked")
		List<ChallengeDAO> challenges = query2.list();
		ChallengeDAO challengeDone = null;
		for (ChallengeDAO challengeDAO : challenges) {
			challengeDone = challengeDAO;
		}
		for (PlayerDAO playerDAO : users) {
			if (challengeDone != null){
				playerDAO.getDoneChallenges().add(challengeDone);
			}
		}
	}
	
	private Session getSession(){ 
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		return sessionFactory.openSession();
	}
	
}
