package com.saauravsaxena.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saauravsaxena.blog.apiresponse.ApiResponse;
import com.saauravsaxena.blog.apiresponse.CommentApiResponse;
import com.saauravsaxena.blog.apiresponse.ListCommentApiResponse;
import com.saauravsaxena.blog.payloads.CommentDto;
import com.saauravsaxena.blog.services.CommentsServices;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
	
	@Autowired
	private CommentsServices commentServices;
	
	@PostMapping("/post/{postId}/user/{userId}")
	public ResponseEntity<CommentApiResponse> createComment(@Valid @RequestBody CommentDto commentDto ,
			@PathVariable Integer userId, @PathVariable Integer postId
			){
		
		CommentDto savedCommentDto = this.commentServices.createComment(commentDto, userId, postId);
		
		ApiResponse apiResponse = new ApiResponse(200,true,"Comment Created SuccesFully !!!");
		
		CommentApiResponse commentApiResponse = new CommentApiResponse(apiResponse,savedCommentDto);
		
		return new ResponseEntity<CommentApiResponse>(commentApiResponse,HttpStatus.CREATED) ;
	}
	
	@DeleteMapping("/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
		this.commentServices.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(200,true,"Comment Deleted Successfully!!!"),HttpStatus.OK);
	}
	@PutMapping("/{commentId}")
	public ResponseEntity<CommentApiResponse> updateComment(@Valid @RequestBody CommentDto commentDto,@PathVariable Integer commentId){
		CommentDto updatedCommentDto = this.commentServices.updateComment(commentDto, commentId);
		
		ApiResponse apiResponse = new ApiResponse(200, true, "Comment Updated SuccesFully !!!");

		CommentApiResponse commentApiResponse = new CommentApiResponse(apiResponse, updatedCommentDto);

		return new ResponseEntity<CommentApiResponse>(commentApiResponse, HttpStatus.OK);

	}
	
	@DeleteMapping("/post/{postId}")
	public ResponseEntity<ApiResponse> deleteAllCommentByPost(@PathVariable Integer postId){
		this.commentServices.deleteAllCommentByPost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(200,true,"All Comment Deleted Successfully Whose belong to this post!!!"),HttpStatus.OK);
				
	}
	@GetMapping("/post/{postId}")
	public ResponseEntity<ListCommentApiResponse> getAllCommentByPost(@PathVariable Integer postId){
		List<CommentDto> commentsDto = this.commentServices.getAllCommentsByPost(postId);
		ApiResponse apiResponse = new ApiResponse(200,true,"SuccesFully !!!");
		
		return new ResponseEntity<ListCommentApiResponse>(new ListCommentApiResponse(apiResponse,commentsDto),HttpStatus.OK);
		
		
	}

}
