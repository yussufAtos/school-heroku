package com.classrooms.service;

import com.classrooms.model.Role;
import com.classrooms.model.UserApp;

public interface UserService {
	
	UserApp save(UserApp user);

	UserApp findByUserName(String userName);

	Role saveRole(Role role);

	void addRoleToUser(String userName, String roleName);
	
	void delete(long id);

}
