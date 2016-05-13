package org.services;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.Random;

import org.exchangableObjects.Player;
import org.junit.Test;

public class UserServiceTest {
	
	UserService userService = UserService.getInstance();

	@Test
	public void testLoginUser() {
		assertTrue(loginUser("Nicolas", "Contraseña"));
		assertFalse(loginUser("Nicolas", "contraseña"));
		assertTrue(loginUser("nicolas", "Contraseña"));
		assertFalse(loginUser("HHH", "Contraseña"));
		assertFalse(loginUser("Nicolas", "Contrasena"));
	}

	@Test
	public void testRegisterUser() {
		String randomWord = new BigInteger(130, new Random()).toString(32);
		assertTrue(userService.registerUser(randomWord, randomWord));
		assertFalse(userService.registerUser("Nicolas", "Contraseña"));
		assertFalse(userService.registerUser("nicolas", "Contraseña"));
		assertFalse(userService.registerUser("NICOLAS", "Contra"));
	}

	@Test
	public void testGetInformationAboutUser() {
		Player player = userService.getInformationAboutUser("Nicolas");
		assertEquals(player.getUsername(), "Nicolas");
		assertEquals(player.getPassword(), "Contraseña");
		assertEquals(player.getPoints(), 0);
		Player player2 = userService.getInformationAboutUser("HHH");
		assertNull(player2);	
	}

	private boolean loginUser(String username, String password){
		Player player = new Player();
		player.setUsername(username);
		player.setPassword(password);
		return userService.loginUser(player);
	}

}
