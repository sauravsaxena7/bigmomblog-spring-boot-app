package com.saauravsaxena.blog.apiresponse;


import java.util.List;

import com.saauravsaxena.blog.payloads.PostDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ListPostApiResponse {
	ApiResponse apiResponse;
	private int pageNumber;
	private int pageSize;
	private long totalElements;
	private int totalPages;
	private boolean lastPage;
	private boolean firstPage;
	List<PostDto> posts;
	

}
