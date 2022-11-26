package com.saauravsaxena.blog.apiresponse;

import java.util.List;

import com.saauravsaxena.blog.payloads.CommentDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor

public class ListCommentApiResponse {
	ApiResponse apiResponse;
	List<CommentDto> comments;
}
