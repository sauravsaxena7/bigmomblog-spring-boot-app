package com.saauravsaxena.blog.services.implem;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saauravsaxena.blog.exception.ResourceNotFoundException;
import com.saauravsaxena.blog.model.Comments;
import com.saauravsaxena.blog.model.Post;
import com.saauravsaxena.blog.model.User;
import com.saauravsaxena.blog.payloads.CommentDto;
import com.saauravsaxena.blog.payloads.PostDto;
import com.saauravsaxena.blog.payloads.UserDto;
import com.saauravsaxena.blog.repo.CommentRepo;
import com.saauravsaxena.blog.repo.PostRepo;
import com.saauravsaxena.blog.repo.UserRepo;
import com.saauravsaxena.blog.services.CommentsServices;

@Service
public class CommentServiceImplem implements CommentsServices {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modeMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer userId, Integer postId) {
		User user = this.userRepo.findById(userId).orElseThrow(
				()->new 
				
				ResourceNotFoundException("User"," id ", userId));
		
		Post post  = this.postRepo.findById(postId).orElseThrow(
				()->new ResourceNotFoundException("Post", " id ",postId));
		commentDto.setPost(this.modeMapper.map(post, PostDto.class));
		commentDto.setUser(this.modeMapper.map(user, UserDto.class));
		
		Comments comment = this.modeMapper.map(commentDto, Comments.class);	
		Comments savedComment = this.commentRepo.save(comment);
		
		return this.modeMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comments comment = this.commentRepo.findById(commentId)
				.orElseThrow(
				()->new ResourceNotFoundException("Comment", " id ",commentId));
		this.commentRepo.delete(comment);
	}

	@Override
	public void deleteAllCommentByPost(Integer postId) {
		Post post  = this.postRepo.findById(postId).orElseThrow(
				()->new ResourceNotFoundException("Post", " id ",postId));
		this.commentRepo.deleteCommentByPost(post.getPostId());
		
	}

	@Override
	public CommentDto updateComment(CommentDto commentDto,Integer commentId) {
		Comments comment = this.commentRepo.findById(commentId)
				.orElseThrow(
				()->new ResourceNotFoundException("Comment", " id ",commentId));
		comment.setContent(commentDto.getContent());
		Comments updatedComment = this.commentRepo.save(comment);
		return this.modeMapper.map(updatedComment, CommentDto.class);
	}

	@Override
	public List<CommentDto> getAllCommentsByPost(Integer postId) {
		Post post  = this.postRepo.findById(postId).orElseThrow(
				()->new ResourceNotFoundException("Post", " id ",postId));
		List<Comments> comments = this.commentRepo.findByPost(post);
		
		List<CommentDto> commentsDto = comments.stream()
				.map(comment->this.modeMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
		
		return commentsDto;
	}

}
