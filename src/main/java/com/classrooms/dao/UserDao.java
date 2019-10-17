package com.classrooms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.classrooms.model.UserApp;


@Repository
public interface UserDao extends JpaRepository<UserApp, Long>{
	  UserApp findByUsername(String username);
}

