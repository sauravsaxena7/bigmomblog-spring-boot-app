package com.saauravsaxena.blog.services;

import java.util.List;

import com.saauravsaxena.blog.payloads.CommentDto;
import com.saauravsaxena.blog.payloads.PostDto;

public interface CommentsServices {
	
	CommentDto createComment(CommentDto commentDto, Integer userId,Integer postId);
	void deleteComment(Integer commentId);
	void deleteAllCommentByPost(Integer postId);	
	CommentDto updateComment(CommentDto commentDto,Integer commentId);
	List<CommentDto> getAllCommentsByPost(Integer postId);
		
}
