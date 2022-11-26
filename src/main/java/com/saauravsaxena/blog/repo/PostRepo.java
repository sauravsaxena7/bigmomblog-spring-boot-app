package com.saauravsaxena.blog.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.saauravsaxena.blog.model.Category;
import com.saauravsaxena.blog.model.Post;
import com.saauravsaxena.blog.model.User;

public interface PostRepo extends JpaRepository<Post,Integer> {
	//custom method 
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	List<Post> findByTitleContaining(String title);
	
	@Query("select p from Post p where p.title like :key")
	List<Post> searchByTitle(@Param("key") String title);
}
