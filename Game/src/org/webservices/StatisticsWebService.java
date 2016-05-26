package org.webservices;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.manager.SessionManager;
import org.services.StatisticsService;

@Path("/statistics")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StatisticsWebService {

	@GET
	@Path("/byUsername")
	// Returns the matches played on 0 and the matches won on 0.
	public Long[] getMatchesPlayedMatchesWon(@Context HttpServletRequest request) {
		Long[] matches = null;
		String username = SessionManager.getInstance().getUser(request.getSession().getId());
		matches = StatisticsService.getInstance().getMatchesPlayedWon(username);
		return matches;
	}

}
