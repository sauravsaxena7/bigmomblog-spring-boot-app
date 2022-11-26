package com.saauravsaxena.blog.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saauravsaxena.blog.apiresponse.ApiResponse;
import com.saauravsaxena.blog.apiresponse.UserApiResponse;
import com.saauravsaxena.blog.payloads.UserDto;
import com.saauravsaxena.blog.services.UserServices;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")

public class UserController {
	//agar hum autoWired no krenge to userservice mein null jyega
	
	@Autowired 
	private UserServices userService;
	
	//post CreateUser
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		
		UserDto createUserDto = this.userService.createUser(userDto);
		
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
		
		
	}
	
	//Put -update user
	//here useriD is path Uri variable
	@PutMapping("/{userId}")
	public ResponseEntity<UserApiResponse> updateUser(@RequestBody UserDto userDto,@PathVariable("userId") Integer userId){
		
		UserDto updateUserDto = this.userService.updateUser(userDto, userId);
		
		return new ResponseEntity<UserApiResponse>(new UserApiResponse(200,true,"User Updated SuccessfullY",updateUserDto),HttpStatus.OK);
		 
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer id){
		this.userService.deleteUser(id);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse(200,true,"User Deleted Succesfully"),HttpStatus.OK);
		
	}
	
	//Get All Users
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		return ResponseEntity.ok(this.userService.getAllUsers());
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserApiResponse> getSingleUser(@PathVariable Integer userId){
		
		UserDto singleUserDto = this.userService.getUserById(userId);
		return new ResponseEntity<UserApiResponse>(new UserApiResponse(200,true,"User Found!!!",singleUserDto),HttpStatus.ACCEPTED);
	}
	

	@GetMapping("/test")
	public String test() {
		return "Welcome to backend Api SaxenaBlog";
	}
	
	

}
