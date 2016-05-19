package org.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.models.ChallengeLuis;

public class ChallengeController {
	
	private List<ChallengeLuis> challenges;
	
	
	public ChallengeController(){
		
		challenges = new ArrayList<ChallengeLuis>();
		challenges.add(new ChallengeLuis("square"));
		challenges.add(new ChallengeLuis("diagonals"));
	}
	
	
	public ChallengeLuis getRandomChallenge(){
		Random r = new Random();
		int randomIndex = r.nextInt(challenges.size());
		return challenges.get(randomIndex);
			
	}

}
