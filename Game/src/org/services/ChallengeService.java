package org.services;

import java.util.ArrayList;
import java.util.List;

import org.daoObjects.ChallengeDAO;
import org.exchangableObjects.Challenge;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ChallengeService {
	
	private static ChallengeService challengeService = null;

	private ChallengeService() {
	}

	public static ChallengeService getInstance() {
		if (challengeService == null) {
			challengeService = new ChallengeService();
		}
		return challengeService;
	}
	
	public List<Challenge> getChallengesPerUser(String username){
		List<Challenge> resultList = new ArrayList<Challenge>();
		Session session = getSession();
		String hql = "SELECT challenges FROM PlayerDAO p "
				+ "join p.doneChallenges challenges WHERE p.username = :username "
				+ "order by challenges.level";
		Query query = session.createQuery(hql);
		query.setParameter("username", username);
		@SuppressWarnings("unchecked")
		List<ChallengeDAO> results = query.list();
		for (ChallengeDAO challengeDAO : results) {
			Challenge challenge = new Challenge();
			challenge.setName(challengeDAO.getName());
			challenge.setImage(challengeDAO.getImage());
			challenge.setLevel(challengeDAO.getLevel());
			challenge.setPoints(challengeDAO.getPoints());
			resultList.add(challenge);
		}
		return resultList;
	}
	
	public Challenge getNextChallenge(String username){
		Challenge returnChallenge = new Challenge();
		int lastChallengeCompleted = 0;
		List<Challenge> challengeList = getChallengesPerUser(username);
		if (challengeList.size() != 0){
			lastChallengeCompleted = (challengeList.get(challengeList.size()-1).getLevel());
		}
		int nextChallenge;
		if (challengeList.size() == 5){ // Hizo todos los desafíos
			nextChallenge = lastChallengeCompleted;
		}else{
			nextChallenge = lastChallengeCompleted + 1;
		}
		Session session = getSession();
		String hql = "FROM ChallengeDAO challenges where challenges.level = " + nextChallenge + " order by challenges.level";
		Query query = session.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<ChallengeDAO> results = query.list();
		for (ChallengeDAO challengeDAO : results) {
			returnChallenge.setName(challengeDAO.getName());
			returnChallenge.setImage(challengeDAO.getImage());
			returnChallenge.setLevel(challengeDAO.getLevel());
			returnChallenge.setPoints(challengeDAO.getPoints());
		}
		return returnChallenge;
	}
	
	private Session getSession(){ 
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		return sessionFactory.openSession();
	}
	
}
