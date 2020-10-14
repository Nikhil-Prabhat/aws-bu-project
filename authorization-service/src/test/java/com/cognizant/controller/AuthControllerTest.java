package com.cognizant.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import com.cognizant.model.AuthResponse;
import com.cognizant.model.UserHospital;

import com.cognizant.repository.UserRepository;
import com.cognizant.service.CustomerDetailsService;
import com.cognizant.service.JwtUtil;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthControllerTest {
	@InjectMocks
	AuthController authController;

	AuthResponse authResponse;

	UserDetails userdetails;

	@Mock
	JwtUtil jwtutil;

	@Mock
	CustomerDetailsService custdetailservice;

	@Mock
	UserRepository userservice;

	@Test
	public void loginTest() {

		UserHospital user = new UserHospital("admin", "admin", null, null);
		UserDetails loadUserByUsername = custdetailservice.loadUserByUsername("admin");
		UserDetails value = new User(user.getUserid(), user.getUpassword(), new ArrayList<>());
		when(custdetailservice.loadUserByUsername("admin")).thenReturn(value);
		when(jwtutil.generateToken(loadUserByUsername)).thenReturn("token");
		ResponseEntity<?> login = authController.login(user);
		System.out.println(login.getStatusCodeValue());
		assertEquals(login.getStatusCodeValue(), 200);
	}

	@Test
	public void loginTestFailed() {

		UserHospital user = new UserHospital("admin", "admin", null, null);
		UserDetails loadUserByUsername = custdetailservice.loadUserByUsername("admin");
		UserDetails value = new User(user.getUserid(), "admin11", new ArrayList<>());
		when(custdetailservice.loadUserByUsername("admin")).thenReturn(value);
		when(jwtutil.generateToken(loadUserByUsername)).thenReturn("token");
		ResponseEntity<?> login = authController.login(user);
		assertEquals(login.getStatusCodeValue(), 403);
	}

	@Test
	public void validateTestValidtoken() {

		// when(authController.verifyToken("token")).thenReturn(new
		// AuthResponse("admin", "admin", true))
		when(jwtutil.validateToken("token")).thenReturn(true);
		when(jwtutil.extractUsername("token")).thenReturn("admin");
		UserHospital user1 = new UserHospital("admin", "admin", "admin", null);
		Optional<UserHospital> data = Optional.of(user1);
		when(userservice.findById("admin")).thenReturn(data);
		ResponseEntity<?> validity = authController.getValidity("bearer token");
		assertEquals(validity.getBody().toString().contains("true"), false);

	}

	@Test
	public void validateTestInValidtoken() {

		// when(authController.verifyToken("token")).thenReturn(new
		// AuthResponse("admin", "admin", true))
		when(jwtutil.validateToken("token")).thenReturn(false);
		ResponseEntity<?> validity = authController.getValidity("bearer token");
		assertEquals(validity.getBody().toString().contains("false"), false);
	}

}
