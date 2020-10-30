package com.truyum.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.truyum.dao.userDAO;
import com.truyum.model.UserTruyum;


@Service
public class CustomerDetailsService implements UserDetailsService {

	@Autowired
	private userDAO userdao;
	@Override
	public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
		List<SimpleGrantedAuthority> roles = null;
		UserTruyum truyum = userdao.findById(uid).orElse(null);
		
		System.out.println("User login -> "+truyum);
		
		if (truyum != null) {
			roles = Arrays.asList(new SimpleGrantedAuthority(truyum.getRoles()));
			return new User(truyum.getUserid(), truyum.getUpassword(), roles);
		}
		
		
		
		throw new UsernameNotFoundException("User not found with the name ");
		
		
	}

}
