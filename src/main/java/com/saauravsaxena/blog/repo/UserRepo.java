package com.saauravsaxena.blog.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.saauravsaxena.blog.model.User;

@Repository
@EnableJpaRepositories ("domain.repositroy-package") 
public interface UserRepo extends JpaRepository<User,Integer> {
	Optional<User> findByEmail(String email);
}
