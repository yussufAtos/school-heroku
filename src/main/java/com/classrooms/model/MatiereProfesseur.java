package com.classrooms.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class MatiereProfesseur implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private Professeur matProf_prof;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private Matiere matProf_mat;
	
	@ManyToMany
	@JoinTable(
              name="matiereProfesseurClasses",
              joinColumns={@JoinColumn(name="matProfId", referencedColumnName="id")},
              inverseJoinColumns={@JoinColumn(name="classesId", referencedColumnName="id")})
	private Set<Classe> classes = new HashSet<>();

	public Set<Classe> getClasses() {
		return classes;
	}

	public void setClasses(Set<Classe> classes) {
		this.classes = classes;
	}

	public MatiereProfesseur() {
		super();
	}

	public Professeur getMatProf_prof() {
		return matProf_prof;
	}

	public void setMatProf_prof(Professeur matProf_prof) {
		this.matProf_prof = matProf_prof;
	}

	public Matiere getMatProf_mat() {
		return matProf_mat;
	}

	public void setMatProf_mat(Matiere matProf_mat) {
		this.matProf_mat = matProf_mat;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
