package com.saauravsaxena.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.saauravsaxena.blog.config.AppConstants;
import com.saauravsaxena.blog.model.Role;
import com.saauravsaxena.blog.repo.RoleRepo;

@SpringBootApplication
public class BigmomblogAppApisApplication implements CommandLineRunner{
	
	@Autowired
	private RoleRepo roleRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(BigmomblogAppApisApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		//System.out.println("lola "+this.passwordEncoder.encode("Saurav@121")); 
		try {
			Role role = new Role();
			role.setId(AppConstants.ROLE_NORMAL);
			role.setName("ROLE_USER");
			
			Role role1 = new Role();
			role1.setId(AppConstants.ROLE_ADMIN);
			role1.setName("ROLE_ADMIN");
			List<Role> roles=List.of(role,role1);
			
			List<Role> result = this.roleRepo.saveAll(roles);
			result.forEach(r->{
				System.out.println(r.getName());
			});
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
