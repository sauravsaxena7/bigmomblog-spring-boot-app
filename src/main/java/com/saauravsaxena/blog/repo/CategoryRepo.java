package com.saauravsaxena.blog.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saauravsaxena.blog.model.Category;

public interface CategoryRepo extends JpaRepository<Category,Integer>{
	
}