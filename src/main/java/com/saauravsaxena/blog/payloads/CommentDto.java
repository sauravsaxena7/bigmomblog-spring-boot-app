package com.saauravsaxena.blog.payloads;

import java.util.Date;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.saauravsaxena.blog.model.Category;
import com.saauravsaxena.blog.model.Comments;
import com.saauravsaxena.blog.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CommentDto {
	
	private Integer id;
	
	@NotBlank
	private String content;
	
	
	private UserDto user;
	
	
	private PostDto post;

}
