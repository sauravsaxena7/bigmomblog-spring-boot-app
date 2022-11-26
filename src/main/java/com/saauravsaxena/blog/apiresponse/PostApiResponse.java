package com.saauravsaxena.blog.apiresponse;

import com.saauravsaxena.blog.payloads.PostDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class PostApiResponse {
	ApiResponse apiResponse;
	PostDto postDto;

}
