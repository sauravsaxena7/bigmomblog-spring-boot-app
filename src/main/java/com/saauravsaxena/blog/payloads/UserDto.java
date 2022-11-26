package com.saauravsaxena.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.saauravsaxena.blog.model.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	private int id;
	
	@NotEmpty
	@Size(min=3,message="Username must be min of 3 character")
	private String name;
	
	@NotNull @Email(message="Invalid Email !!!")
	private String email;
	
	@NotEmpty
	@Size(min=8,message="Password must be min 8 character!!!")
	//@Pattern
	private String password;
	
	@NotNull
	private String about;
	
	private Set<RoleDto> roles = new HashSet<>();
	

}
