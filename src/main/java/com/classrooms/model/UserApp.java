package com.classrooms.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;


@Entity
@Table(name= "UserApp")
public class UserApp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue (strategy = GenerationType.AUTO) 
	private int id;

	@Column(unique = true)
	private String username;
	@Column
	//@JsonIgnore
	private String password;
	@Column
	private int age;
	/*
	 * @ManyToMany(fetch = FetchType.EAGER)
	 * 
	 * @JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "usee_id")
	 * }, inverseJoinColumns = { @JoinColumn(name = "role_id") }) private
	 * Collection<Role> roles = new ArrayList<>();
	 */

	// @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	// private Collection<Role> roles = new ArrayList<>();
	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	private Set<Role> roles = new HashSet<>();

	public UserApp(String username, String password, int age) {

		this.username = username;
		this.password = password;
		this.age = age;
	}

	public UserApp(String username, String password) {
		this.username = username;
		this.password = password;

	}

	public UserApp() {
		super();
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonSetter
	public void setPassword(String password) {
		this.password = password;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
