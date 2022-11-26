package com.saauravsaxena.blog.services;

import java.util.List;

import com.saauravsaxena.blog.payloads.CategoryDto;

public interface CategoryServices {
	
	//create
	public CategoryDto createCategory(CategoryDto categoryDto);
	
	//update
	public CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
	
	//delete
	public void deleteCategory(Integer categoryId);
	
	//getSingle
	public CategoryDto getSingleCategory(Integer id);
	
	//getAll
	public List<CategoryDto> getAllCaregory();
	

}
