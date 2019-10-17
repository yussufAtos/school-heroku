package com.classrooms.model;

import java.io.Serializable;
import java.util.Collection;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Matiere implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;

	private String name;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "matProf_mat")
	private Collection<MatiereProfesseur> matProf_matCollection;

	public Matiere(String name) {
		super();
		this.name = name;
	}

	public Matiere() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Matiere [id=" + id + ", name=" + name + ", matProf_matCollection=" + matProf_matCollection + "]";
	}



}
