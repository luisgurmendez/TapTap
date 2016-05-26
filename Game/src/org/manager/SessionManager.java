package org.manager;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {

	private static SessionManager sessionManager = null;
	private Map<String, String> users;
	
	private SessionManager(){
		users = new HashMap<String, String>();
	}
	
	public static SessionManager getInstance(){
		if (sessionManager==null){
			sessionManager = new SessionManager();
		}
		return sessionManager;
	}
	
	// Associates the username to the session id.
	public void addUser(String id, String username){
		users.put(id, username);
	}
	
	public void deleteUser(String id){
		users.remove(id);
	}
	
	public String getUser(String id){
		String result = users.get(id);
		return result;
	}
	
	public boolean containsUser(String id){
		boolean value = users.containsKey(id);
		return value;
	}
}
