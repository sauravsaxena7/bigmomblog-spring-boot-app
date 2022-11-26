package com.saauravsaxena.blog.services.implem;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.saauravsaxena.blog.config.AppConstants;
import com.saauravsaxena.blog.exception.ResourceNotFoundException;
import com.saauravsaxena.blog.model.Role;
import com.saauravsaxena.blog.model.User;
import com.saauravsaxena.blog.payloads.UserDto;
import com.saauravsaxena.blog.repo.RoleRepo;
import com.saauravsaxena.blog.repo.UserRepo;
import com.saauravsaxena.blog.services.UserServices;

@Service
public class UserServiceImplem implements UserServices {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserDto createUser(UserDto userDto) {
		
		User user = this.dtoToUser(userDto);
		User savedUser = this.userRepo.save(user);
		UserDto savedUserDto = this.userToDto(savedUser);
		return savedUserDto;
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(
				()->new 
				
				ResourceNotFoundException("User"," id ", userId));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		
		User updatedUser = this.userRepo.save(user);
		
		UserDto updatedUserDto = this.userToDto(updatedUser);
		
		return updatedUserDto;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(
						()-> new ResourceNotFoundException("User"," Id ", userId));
		
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List <User> users = this.userRepo.findAll();
		
		List<UserDto> userDtos=users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		
		return userDtos;
	}
	
	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(
				()->new 
				
				ResourceNotFoundException("User"," id ", userId));
		
		this.userRepo.delete(user);
	}

	public User dtoToUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
		
		return user;
			
	}
 
	public UserDto userToDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setAbout(user.getAbout());
//		userDto.setPassword(user.getPassword());
		return userDto;
			
	}

	@Override
	public UserDto registerUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto,User.class);
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		Role role = this.roleRepo.findById(AppConstants.ROLE_NORMAL).get();
		
		user.getRoles().add(role);
		User savedUser = this.userRepo.save(user);
		
		
		return this.modelMapper.map(savedUser, UserDto.class);
	}

	
}
