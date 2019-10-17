package com.classrooms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.classrooms.model.Role;

@Repository
public interface RoleDao extends JpaRepository<Role, Integer> {
	 public  Role 	findByRole(String roleName);
}

