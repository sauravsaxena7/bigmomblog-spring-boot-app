package com.saauravsaxena.blog.services;

import java.util.List;

import com.saauravsaxena.blog.apiresponse.ListPostApiResponse;
import com.saauravsaxena.blog.model.Post;
import com.saauravsaxena.blog.payloads.PostDto;

public interface PostServices {
	
	PostDto createPost(PostDto postDto,Integer userId, Integer categoryId);
	PostDto updatePost(PostDto postDto, Integer id);
	void deletePost(Integer id);
	PostDto getSinglePostDetails(Integer postId);
	ListPostApiResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	List<PostDto> getPostByCategory(Integer categoryId);
	List<PostDto> getPostByUser(Integer userId);
	ListPostApiResponse searchPosts(String keyword);
	
	

}
