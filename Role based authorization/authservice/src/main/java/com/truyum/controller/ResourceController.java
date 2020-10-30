package com.truyum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.truyum.dao.userDAO;
import com.truyum.model.AuthResponse;
import com.truyum.service.CustomerDetailsService;
import com.truyum.service.JwtUtil;

@RestController
public class ResourceController {
	
	@Autowired
	private JwtUtil jwtutil;
	
	@Autowired
	private CustomerDetailsService Custdetailservice;
	@Autowired
	private userDAO userservice;
	
	@GetMapping("/user")
	public String getUser(@RequestHeader("Authorization") String token) throws Exception
	{
		token=token.substring(7);
		
		if(jwtutil.validateToken(token) && jwtutil.getRolesFromToken(token).get(0).getAuthority().equals("ROLE_USER"))
		{
			return "Hello User";
			
		}
		else
			throw new Exception("User Not Found");
		
		
	}
	
	@GetMapping("/admin")
	public String getAdmin(@RequestHeader("Authorization") String token) throws Exception
	{
token=token.substring(7);
		
		if(jwtutil.validateToken(token) && jwtutil.getRolesFromToken(token).get(0).getAuthority().equals("ROLE_ADMIN"))
		{
			return "Hello Admin";
			
		}
		else
			throw new Exception("Admin Not Found");
	}

}
