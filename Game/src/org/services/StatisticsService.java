package org.services;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class StatisticsService {
	
	private static StatisticsService statisticsService = null;
	
	private StatisticsService(){
	}
	
	public static StatisticsService getInstance(){
		if (statisticsService == null){
			statisticsService = new StatisticsService();
		}
		return statisticsService;
	}
	
	public Long[] getMatchesPlayedWon(String username){
		Long[] arrayOfStatistics = null;
		Session session = getSession();
		String hql = "SELECT p.matchesPlayed, p.matchesWon FROM PlayerDAO p WHERE p.username = :username ";
		Query query = session.createQuery(hql);
		query.setParameter("username", username);
		@SuppressWarnings("unchecked")
		List<Object[]> results = query.list();
		for (Object[] numbers : results) {
			arrayOfStatistics = new Long[2];
			arrayOfStatistics[0] = (Long) numbers[0];
			arrayOfStatistics[1] = (Long) numbers[1];
		}
		return arrayOfStatistics;
	}

	private Session getSession(){ 
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		return sessionFactory.openSession();
	}
	
}
