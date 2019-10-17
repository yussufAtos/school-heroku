package com.classrooms.model;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Professeur implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private int id;
	private String nom;
	private String prenom;
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "matProf_prof",cascade=CascadeType.REMOVE)
	private Set<MatiereProfesseur> matProf_profCollection = new HashSet<>();

	public Professeur(String nom, String prenom) {
		super();
		this.nom = nom;
		this.prenom = prenom;
	}

	public Professeur() {
		super();
		// TODO Auto-generated constructor stub
		// matProf_profCollection=new ArrayList<MatiereProf>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}



	public Set<MatiereProfesseur> getMatProf_profCollection() {
		return matProf_profCollection;
	}

	public void setMatProf_profCollection(Set<MatiereProfesseur> matProf_profCollection) {
		this.matProf_profCollection = matProf_profCollection;
	}

	@Override
	public String toString() {
		return "Professeur [nom=" + nom + ", prenom=" + prenom + "]";
	}

}
