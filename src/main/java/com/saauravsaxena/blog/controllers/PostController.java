package com.saauravsaxena.blog.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.saauravsaxena.blog.apiresponse.ApiResponse;
import com.saauravsaxena.blog.apiresponse.ListPostApiResponse;
import com.saauravsaxena.blog.apiresponse.PostApiResponse;
import com.saauravsaxena.blog.config.AppConstants;
import com.saauravsaxena.blog.payloads.PostDto;
import com.saauravsaxena.blog.services.FileServices;
import com.saauravsaxena.blog.services.PostServices;

@RestController
@RequestMapping("/api/post")
public class PostController {
	
	@Autowired
	private PostServices postServices;
	
	@Autowired
	private FileServices fileServices; 
	
	@Value("${project.image}")
	private String path;
	
	
	@PostMapping("/user/{userId}/category/{categoryId}")
	public ResponseEntity<PostApiResponse> createPost(@Valid @RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId ){
		ApiResponse apiResponse = new ApiResponse(200,true,"Post Created Successfully");
		PostDto postDTO = this.postServices.createPost(postDto, userId, categoryId);
		
		return new ResponseEntity<PostApiResponse>(new PostApiResponse(apiResponse,postDTO),HttpStatus.CREATED);
		
	}
	
	//getALLpOSTbY USER
	@GetMapping("user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
		List<PostDto> postsDto = this.postServices.getPostByUser(userId);
		
		return new ResponseEntity<List<PostDto>>(postsDto,HttpStatus.OK);
		
		
	}

	// getALLpOSTbY Category
	@GetMapping("category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId) {
		List<PostDto> postsDto = this.postServices.getPostByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(postsDto, HttpStatus.OK);

	}
	//getAllPost
	@GetMapping("/")
	public ResponseEntity<ListPostApiResponse> getAllPost(
			@RequestParam(value="pageNumber",defaultValue=AppConstants.PAGE_NUMBER,required=false) Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue=AppConstants.PAGE_SIZE,required=false)Integer pageSize,
			@RequestParam(value="sortBy",defaultValue=AppConstants.SORT_BY,required=false) String sortBy,
			@RequestParam(value="sortDir",defaultValue=AppConstants.SORT_DIR,required=false) String sortDir
			) {
		ListPostApiResponse listPostApiResponse = this.postServices.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<ListPostApiResponse>(listPostApiResponse, HttpStatus.OK);

	}
	@GetMapping("/{postId}")
	public ResponseEntity<PostApiResponse> getSinglePostDetails(@PathVariable Integer postId){
		
		PostDto postDTO = this.postServices.getSinglePostDetails(postId);
		ApiResponse apiResponse = new ApiResponse(200, true, "Success!!!");
		
		return new ResponseEntity<PostApiResponse>(new PostApiResponse(apiResponse,postDTO),HttpStatus.OK);
		
	}

	@PutMapping("/{postId}")
	public ResponseEntity<PostApiResponse> updatePostDetails(@RequestBody PostDto postDto ,@PathVariable Integer postId) {

		PostDto postDTO = this.postServices.updatePost(postDto, postId);
		ApiResponse apiResponse = new ApiResponse(200, true, "Post Updated SuccessFully");

		return new ResponseEntity<PostApiResponse>(new PostApiResponse(apiResponse, postDTO), HttpStatus.OK);

	}
	
	@DeleteMapping("/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {

		this.postServices.deletePost(postId);
		ApiResponse apiResponse = new ApiResponse(200, true, "Post Deleted SuccessFully");

		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);

	}
	
	@GetMapping("/search")
	public ResponseEntity<ListPostApiResponse> searchPost(
			@RequestParam(value="title",defaultValue=AppConstants.KEYWORDS,required=false) String keywords
			) {
		ListPostApiResponse listPostApiResponse = this.postServices.searchPosts(keywords); 
		return new ResponseEntity<ListPostApiResponse>(listPostApiResponse, HttpStatus.OK);
	}
	
	@PostMapping("/file/image/upload/{postId}")
	public ResponseEntity<PostApiResponse> uploadPostImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId
			) throws IOException{
		String fileName="";
		ApiResponse apiResponse = new ApiResponse();
		PostApiResponse postApiResponse = new PostApiResponse();
		PostDto postDto=null;
		postDto = this.postServices.getSinglePostDetails(postId);
		fileName = this.fileServices.uploadImage(path, image);
		postDto.setImageName(fileName);
		postDto = this.postServices.updatePost(postDto, postId);
		apiResponse.setMessage("Success!!!");
		apiResponse.setStatus(200);
		apiResponse.setSuccess(true);

		postApiResponse.setApiResponse(apiResponse);
		postApiResponse.setPostDto(postDto);
		
		return new ResponseEntity<PostApiResponse>(postApiResponse,HttpStatus.OK);
		
	}
	
	@GetMapping(value="/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imageName") String imageName,
			HttpServletResponse response
	) throws IOException {
		
		InputStream resource = this.fileServices.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
	
	
	
		

}
