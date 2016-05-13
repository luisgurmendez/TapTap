package org.webservices;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.exchangableObjects.Challenge;
import org.services.ChallengeService;

@Path("/challenges")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ChallengeWebService {

	@GET
	@Path("{username}")
	public List<Challenge> getChallengesPerUser(@PathParam("username") String username){
		ChallengeService challengeService = ChallengeService.getInstance();
		return challengeService.getChallengesPerUser(username);
	}
	
}
