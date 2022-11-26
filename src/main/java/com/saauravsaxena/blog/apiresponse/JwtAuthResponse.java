package com.saauravsaxena.blog.apiresponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class JwtAuthResponse {
	
	private Integer status;
	private String message;
	private String token;
	
	

}
