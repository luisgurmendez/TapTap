package org.webservices;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.exchangableObjects.Player;
import org.services.UserService;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserWebService {

	@GET
	// Returns true if the player passed as a parameter is valid
	public Boolean loginUser(@QueryParam("username") String username, @QueryParam("password") String password){
		UserService userService = UserService.getInstance();
		return userService.loginUser(new Player(username, password));
	}
	
	@POST
	public Boolean registerUser(Player player){
		UserService userService = UserService.getInstance();
		return userService.registerUser(player.getUsername(), player.getPassword());
	}
	
	@GET
	@Path("{username}")
	public Player getInformationAboutUser(@PathParam("username") String username){
		UserService userService = UserService.getInstance();
		return userService.getInformationAboutUser(username);
	}
}
