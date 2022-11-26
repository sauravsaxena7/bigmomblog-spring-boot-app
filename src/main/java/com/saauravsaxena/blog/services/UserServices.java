package com.saauravsaxena.blog.services;

import com.saauravsaxena.blog.payloads.UserDto;

import java.util.List;

public interface UserServices {
	
	UserDto createUser(UserDto user);
	UserDto registerUser(UserDto user);
	UserDto updateUser(UserDto user,Integer id);
	UserDto getUserById(Integer userId);
	List<UserDto> getAllUsers();
	void deleteUser(Integer userId);
	

}
