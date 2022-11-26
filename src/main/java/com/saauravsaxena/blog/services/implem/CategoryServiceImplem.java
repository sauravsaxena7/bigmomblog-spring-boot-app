package com.saauravsaxena.blog.services.implem;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saauravsaxena.blog.exception.ResourceNotFoundException;
import com.saauravsaxena.blog.model.Category;
import com.saauravsaxena.blog.payloads.CategoryDto;
import com.saauravsaxena.blog.repo.CategoryRepo;
import com.saauravsaxena.blog.services.CategoryServices;

@Service
public class CategoryServiceImplem implements CategoryServices {
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = this.modelMapper.map(categoryDto, Category.class);
		Category savedCategory = this.categoryRepo.save(category);
		return this.modelMapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto,Integer id) {
		Category category = this.categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",id));
		
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		Category updatedCategory = this.categoryRepo.save(category);
		return this.modelMapper.map(updatedCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer id) {
		Category category = this.categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",id));
		this.categoryRepo.delete(category);
	}

	@Override
	public CategoryDto getSingleCategory(Integer id) {
		Category category = this.categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",id));
		return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCaregory() {
		List<Category> categories = this.categoryRepo.findAll();
		List<CategoryDto> categoriesDto=categories.stream().map(
				(cat)->
				this.modelMapper.map(cat,CategoryDto.class)).collect(Collectors.toList());
		return categoriesDto;
	}

}
