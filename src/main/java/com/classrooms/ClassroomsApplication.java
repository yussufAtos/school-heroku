package com.classrooms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.classrooms.dao.RoleDao;
import com.classrooms.dao.UserDao;
import com.classrooms.model.Role;
import com.classrooms.model.UserApp;
import com.classrooms.service.UserService;


@SpringBootApplication
public class ClassroomsApplication implements CommandLineRunner {
	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(ClassroomsApplication.class, args);
		
	}

	@Bean
	public BCryptPasswordEncoder getBCPE() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void run(String... agr0) {

	}

}
