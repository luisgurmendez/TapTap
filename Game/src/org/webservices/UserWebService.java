package org.webservices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.exchangableObjects.Player;
import org.manager.SessionManager;
import org.services.UserService;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserWebService {

	@GET
	// Returns true if the player passed as a parameter is valid
	public Boolean loginUser(@QueryParam("username") String username, @QueryParam("password") String password,
			@Context HttpServletRequest request, @Context HttpServletResponse response){
		UserService userService = UserService.getInstance();
		boolean validInformation = userService.loginUser(new Player(username, password));
		if (validInformation){
			HttpSession session = request.getSession(true);
			SessionManager.getInstance().addUser(session.getId(), username);
		}
		return validInformation;
	}
	
	@POST
	public Boolean registerUser(Player player){
		UserService userService = UserService.getInstance();
		return userService.registerUser(player.getUsername(), player.getPassword());
	}
	
	@GET
	@Path("/userInformation")
	public Player getInformationAboutUser(@Context HttpServletRequest request){
		UserService userService = UserService.getInstance();
		return userService.getInformationAboutUser
				(SessionManager.getInstance().getUser(request.getSession().getId()));
	}
	
	@POST
	@Path("/delete")
	public void logOut(@Context HttpServletRequest request){
		SessionManager.getInstance().deleteUser(request.getSession().getId());
		request.getSession().invalidate();
	}

}
