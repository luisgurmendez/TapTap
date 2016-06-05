package org.webservices;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.exchangableObjects.Challenge;
import org.manager.SessionManager;
import org.services.ChallengeService;

@Path("/challenges")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ChallengeWebService {

	@GET
	@Path("/byUsername")
	public List<Challenge> getChallengesPerUser(@Context HttpServletRequest request){
		ChallengeService challengeService = ChallengeService.getInstance();
		return challengeService.getChallengesPerUser
				(SessionManager.getInstance().getUser(request.getSession().getId()));
	}
	
	@GET
	@Path("/byUsernameApp/{username}")
	public List<Challenge> getChallengesPerUserApp(@PathParam("username") String username){
		ChallengeService challengeService = ChallengeService.getInstance();
		return challengeService.getChallengesPerUser(username);
	}
	
}
