package com.saauravsaxena.blog.apiresponse;

import com.saauravsaxena.blog.payloads.CategoryDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CategoryApiResponse {
	ApiResponse apiResponse;
	CategoryDto categoryDto;
}
