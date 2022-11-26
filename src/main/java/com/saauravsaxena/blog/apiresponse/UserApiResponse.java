package com.saauravsaxena.blog.apiresponse;

import com.saauravsaxena.blog.payloads.UserDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor

public class UserApiResponse {
	
	
	private int status;
	private boolean success;
	private String message;
	private UserDto userDto;
	
	

}
