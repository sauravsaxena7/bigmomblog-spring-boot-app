package com.saauravsaxena.blog.services.implem;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.saauravsaxena.blog.apiresponse.ApiResponse;
import com.saauravsaxena.blog.apiresponse.ListPostApiResponse;
import com.saauravsaxena.blog.exception.ResourceNotFoundException;
import com.saauravsaxena.blog.model.Category;
import com.saauravsaxena.blog.model.Post;
import com.saauravsaxena.blog.model.User;
import com.saauravsaxena.blog.payloads.CategoryDto;
import com.saauravsaxena.blog.payloads.PostDto;
import com.saauravsaxena.blog.payloads.UserDto;
import com.saauravsaxena.blog.repo.CategoryRepo;
import com.saauravsaxena.blog.repo.PostRepo;
import com.saauravsaxena.blog.repo.UserRepo;
import com.saauravsaxena.blog.services.PostServices;

@Service
public class PostServiceImplem implements PostServices{

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public PostDto createPost(PostDto postDto,Integer userId, Integer categoryId) {
		User user = this.userRepo.findById(userId).orElseThrow(
				()->new 
				
				ResourceNotFoundException("User"," id ", userId));
		
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",categoryId));
		
		
		postDto.setUser(this.modelMapper.map(user, UserDto.class));
		postDto.setCategory(this.modelMapper.map(category,CategoryDto.class));
		postDto.setImageName("lola.png");
		postDto.setAddedDate(new Date());
		Post post = this.modelMapper.map(postDto, Post.class);
		
		Post newPost = this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		// TODO Auto-generated method stub
		Post post  = this.postRepo.findById(postId).orElseThrow(
				()->new ResourceNotFoundException("Post", " id ",postId));
		post.setImageName(postDto.getImageName());
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		
		Post updatedPost = this.postRepo.save(post);
		
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post  = this.postRepo.findById(postId).orElseThrow(
				()->new ResourceNotFoundException("Post", " id ",postId));
		this.postRepo.delete(post);
		
	}

	@Override
	public ListPostApiResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase("asc")? Sort.by(sortBy).ascending()
				:Sort.by(sortBy).descending();
		
		Pageable p = PageRequest.of(pageNumber, pageSize,sort);
		Page<Post> pagePosts = this.postRepo.findAll(p);
		
		List<Post> posts = pagePosts.getContent();
		List<PostDto> postsDto = posts.stream().map((post)->this.modelMapper.map(post,PostDto.class ))
				.collect(Collectors.toList());
		ApiResponse apiResponse = new ApiResponse(200,true,"Success");
		ListPostApiResponse listPostApiResponse = new ListPostApiResponse();
		listPostApiResponse.setTotalElements(pagePosts.getTotalElements());
		listPostApiResponse.setPageNumber(pagePosts.getNumber());
		listPostApiResponse.setPageSize(pagePosts.getSize());
		listPostApiResponse.setPosts(postsDto);
		listPostApiResponse.setTotalPages(0);
		listPostApiResponse.setApiResponse(apiResponse);
		listPostApiResponse.setLastPage(pagePosts.isLast());
		listPostApiResponse.setFirstPage(pagePosts.isFirst());
		return listPostApiResponse;
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		
		
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(
				()-> new ResourceNotFoundException("Category","categoryId",categoryId));
		
		List<Post> posts = this.postRepo.findByCategory(category);
		List<PostDto> postsDto = posts.stream().map((post)->this.modelMapper.map(post,PostDto.class ))
		.collect(Collectors.toList());
		
		return postsDto;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(
				()->new 
				
				ResourceNotFoundException("User"," id ", userId));
		List<Post> posts = this.postRepo.findByUser(user);
		List<PostDto> postsDto = posts.stream().map((post)->this.modelMapper.map(post,PostDto.class ))
		.collect(Collectors.toList());
		return postsDto;
	}

	@Override
	public ListPostApiResponse searchPosts(String keyword) {
		// TODO Auto-generated method stub
		//List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		List<Post> posts = this.postRepo.searchByTitle("%"+keyword+"%");
		List<PostDto> postsDto = posts.stream().map((post)->this.modelMapper.map(post,PostDto.class ))
				.collect(Collectors.toList());
		ApiResponse apiResponse = new ApiResponse(200,true,"Success");
		ListPostApiResponse listPostApiResponse = new ListPostApiResponse();
		listPostApiResponse.setTotalElements(0);
		listPostApiResponse.setPageNumber(0);
		listPostApiResponse.setPageSize(0);
		listPostApiResponse.setPosts(postsDto);
		listPostApiResponse.setTotalPages(0);
		listPostApiResponse.setApiResponse(apiResponse);
		listPostApiResponse.setLastPage(false);
		listPostApiResponse.setFirstPage(false);
		return listPostApiResponse;
	}

	@Override
	public PostDto getSinglePostDetails(Integer postId) {
		// TODO Auto-generated method stub
		Post post  = this.postRepo.findById(postId).orElseThrow(
				()->new ResourceNotFoundException("Post", " id ",postId));
		return this.modelMapper.map(post, PostDto.class);
	}

}
