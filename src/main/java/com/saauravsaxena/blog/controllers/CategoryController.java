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
import com.saauravsaxena.blog.apiresponse.CategoryApiResponse;
import com.saauravsaxena.blog.payloads.CategoryDto;
import com.saauravsaxena.blog.services.CategoryServices;

@RequestMapping("/api/categories")
@RestController
public class CategoryController {
	
	@Autowired
	private CategoryServices categoryServices;
	
	//create
	@PostMapping("/")
	public ResponseEntity<CategoryApiResponse> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto createCategoryDto = this.categoryServices.createCategory(categoryDto);
		ApiResponse apiResponse = new ApiResponse(200,true,"Category Added SuccessFully !!!");
		
		return new ResponseEntity<CategoryApiResponse>(new CategoryApiResponse(apiResponse,createCategoryDto),HttpStatus.CREATED);
		
	}
	
	//update
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryApiResponse> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId){
		CategoryDto updateCategoryDto = this.categoryServices.updateCategory(categoryDto,categoryId);
		ApiResponse apiResponse = new ApiResponse(200,true,"Category updated SuccessFully !!!");
		
		return new ResponseEntity<CategoryApiResponse>(new CategoryApiResponse(apiResponse,updateCategoryDto),HttpStatus.OK);
		
	}
	
	//delete
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId){
		this.categoryServices.deleteCategory(categoryId);
		ApiResponse apiResponse = new ApiResponse(200,true,"Category deleted SuccessFully !!!");
		
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
		
	}
	
	//getSingleCategory
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryApiResponse> getSingleCategory(@PathVariable Integer categoryId){
		CategoryDto categoryDto=this.categoryServices.getSingleCategory(categoryId);
		ApiResponse apiResponse = new ApiResponse(200,true,"Category deleted SuccessFully !!!");
		
		return new ResponseEntity<CategoryApiResponse>(new CategoryApiResponse(apiResponse,categoryDto),HttpStatus.FOUND);
		
	}
	
	//getAllCategory
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategory(){
		List<CategoryDto> categories = this.categoryServices.getAllCaregory();
		return new ResponseEntity<List<CategoryDto>>(categories,HttpStatus.OK);
		
	}
	
	

}
