package com.classrooms.service.impl;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.classrooms.dao.RoleDao;
import com.classrooms.dao.UserDao;
import com.classrooms.model.Role;
import com.classrooms.model.UserApp;
import com.classrooms.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserDetailsService, UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private RoleDao roleDao;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		System.out.println("****loadUserByUsername***");
		System.out.println("  loadUserByUsername : " + userName);
		UserApp user = userDao.findByUsername(userName);
		System.out.println("  loadUserByUsername user: " + user);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
			// throw exception(USER, ENTITY_NOT_FOUND, userName);
		}

		List<SimpleGrantedAuthority> autorities = new ArrayList<>();
		user.getRoles().forEach(r -> {
			autorities.add(new SimpleGrantedAuthority(r.getRole()));
		});

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				autorities);
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	@Override
	public UserApp save(UserApp user) {
		String hashPwd = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(hashPwd);
		return userDao.save(user);
	}

	@Override
	public Role saveRole(Role role) {
		return roleDao.save(role);
	}

	@Override
	public void addRoleToUser(String userName, String roleName) {
		// TODO Auto-generated method stub
		Role role = roleDao.findByRole(roleName);
		UserApp user = userDao.findByUsername(userName);
		user.getRoles().add(role);

	}

	@Override
	public UserApp findByUserName(String userName) {
		// TODO Auto-generated method stub
		return userDao.findByUsername(userName);
	}

	@Override
	public void delete(long id) {
		userDao.delete(id);
	}

}
