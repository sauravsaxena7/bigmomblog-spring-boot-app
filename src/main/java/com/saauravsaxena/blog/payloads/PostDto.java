package com.saauravsaxena.blog.payloads;

import java.util.Date;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.saauravsaxena.blog.model.Category;
import com.saauravsaxena.blog.model.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {
	

	
	private Integer postId;
	@NotBlank
	@Size(min=10)
	private String title;
	
	@NotBlank
	@Size(min =20)
	private String content;
	
	@NotNull
	private String imageName;
	
	
	private Date addedDate;
	
	
	private UserDto user;
	
	
	private CategoryDto category;
	
	
	
	
	

}
