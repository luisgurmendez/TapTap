package org.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.models.Challenge;

public class ChallengeController {
	
	private List<Challenge> challenges;
	
	
	public ChallengeController(){
		
		challenges = new ArrayList<Challenge>();
		challenges.add(new Challenge("square"));
		challenges.add(new Challenge("diagonals"));
	}
	
	
	public Challenge getRandomChallenge(){
		Random r = new Random();
		int randomIndex = r.nextInt(challenges.size());
		return challenges.get(randomIndex);
			
	}

}
