package com.saauravsaxena.blog.model;

import lombok.Data;

@Data
public class JwtAuthRequest {
	
	private String userEmail;
	private String password;

}
