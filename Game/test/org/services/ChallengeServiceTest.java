package org.services;

import static org.junit.Assert.*;

import java.util.List;

import org.exchangableObjects.Challenge;
import org.junit.Test;

public class ChallengeServiceTest {

	private ChallengeService challengeService = ChallengeService.getInstance();
	
	@Test
	public void testGetChallengesPerUser() {
		List<Challenge> list = challengeService.getChallengesPerUser("Nicolas");
		assertEquals(list.get(0).getName(), "CRUZ");
		assertEquals(list.get(0).getLevel(), 2);
		assertEquals(list.get(0).getPoints(), 100);
		assertEquals(list.get(list.size()-1).getName(), "CUADRADO");
		assertEquals(list.get(1).getPoints(), 50);
		List<Challenge> list2 = challengeService.getChallengesPerUser("Luis");
		assertEquals(list2.size(), 0);
		List<Challenge> list3 = challengeService.getChallengesPerUser("HHH");
		assertEquals(list3.size(), 0);
	}

}
