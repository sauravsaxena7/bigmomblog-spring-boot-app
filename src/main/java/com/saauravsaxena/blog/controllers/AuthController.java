package com.saauravsaxena.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saauravsaxena.blog.apiresponse.ApiResponse;
import com.saauravsaxena.blog.apiresponse.JwtAuthResponse;
import com.saauravsaxena.blog.apiresponse.UserApiResponse;
import com.saauravsaxena.blog.exception.ApiException;
import com.saauravsaxena.blog.model.JwtAuthRequest;
import com.saauravsaxena.blog.payloads.UserDto;
import com.saauravsaxena.blog.security.JwtTokenHelper;
import com.saauravsaxena.blog.services.UserServices;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserServices userServices;
	
	@PostMapping("/login")
	
	public ResponseEntity<JwtAuthResponse> loginCreateToken(@RequestBody JwtAuthRequest request) throws Exception{
		System.out.println("request "+request.getUserEmail());
		this.authenticate(request.getUserEmail(),request.getPassword());
		System.out.println("After Authenticate ");
		UserDetails userDetails=this.userDetailsService.loadUserByUsername(request.getUserEmail());
		String token =this.jwtTokenHelper.generateToken(userDetails);
		
		JwtAuthResponse res = new JwtAuthResponse();
		res.setToken(token);
		res.setMessage("Success!!!");
		res.setStatus(200);
		return new ResponseEntity<JwtAuthResponse>(res,HttpStatus.OK);
	}

	private void authenticate(String userEmail, String password) throws Exception {
		UsernamePasswordAuthenticationToken u = new UsernamePasswordAuthenticationToken(userEmail,password);
		System.out.println("before Authenticate "+ u);
		
		try {
			this.authenticationManager.authenticate(u);
		}catch(BadCredentialsException e) {
			throw new ApiException("Invalid Usewrname or Password !!");
		}catch(Exception e) {
			System.out.println(e);
		}
		
	}
	
	//register user
	@PostMapping("/register")
	public ResponseEntity<UserApiResponse> rgisterUser(@RequestBody UserDto userDto){
		UserDto registeredUser = this.userServices.registerUser(userDto);
		UserApiResponse res = new UserApiResponse(200,true,"UserRegistered SuccessfullY!!!",registeredUser);
		return new ResponseEntity<UserApiResponse>(res,HttpStatus.CREATED);
	}
}
