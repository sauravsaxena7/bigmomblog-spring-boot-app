package com.saauravsaxena.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.saauravsaxena.blog.exception.ResourceNotFoundException;
import com.saauravsaxena.blog.model.User;
import com.saauravsaxena.blog.repo.UserRepo;

@Service
public class CustomUserDetailsServices implements UserDetailsService {
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//loading user from database by username or email
		User user = this.userRepo.findByEmail(username).orElseThrow(
				()-> new ResourceNotFoundException("User"," email ",username));
		return user;
	}

}
