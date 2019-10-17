package com.classrooms.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name= "roles")
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	//@GeneratedValue
	@GeneratedValue (strategy = GenerationType.AUTO) 
	private int Id;
	private String role;

	// @ManyToMany(fetch = FetchType.LAZY, cascade = {
	// CascadeType.PERSIST,CascadeType.MERGE }, mappedBy = "roles")
	// private Collection<User> users = new ArrayList<>();

	public Role(String role) {
		super();
		this.role = role;
	}

	public Role() {

	}



	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
