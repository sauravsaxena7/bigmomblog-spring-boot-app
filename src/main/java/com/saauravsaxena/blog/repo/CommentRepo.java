package com.saauravsaxena.blog.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.saauravsaxena.blog.model.Comments;
import com.saauravsaxena.blog.model.Post;



public interface CommentRepo extends JpaRepository<Comments,Integer>{
	List<Comments> findByPost(Post post);
	
	
	@Query(nativeQuery = true,value="delete from comments where post_id =:id")
	void deleteCommentByPost(@Param("id") Integer id);
}
