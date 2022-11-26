package com.saauravsaxena.blog.apiresponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor

public class ApiResponse {
	
	
	private int status;
	private boolean success;
	private String message;
	
	
	

}
