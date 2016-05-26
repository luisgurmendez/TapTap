package org.services;

import java.util.List;

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
	
	private Session getSession(){ 
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		return sessionFactory.openSession();
	}
	
}
