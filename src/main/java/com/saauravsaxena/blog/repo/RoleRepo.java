package com.saauravsaxena.blog.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saauravsaxena.blog.model.Role;

public interface RoleRepo extends JpaRepository<Role,Integer> {
	

}
