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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Classe implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private int id;

	private String nom;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "classes", cascade = CascadeType.PERSIST)
	private Collection<MatiereProfesseur> mat_prof = new ArrayList<MatiereProfesseur>();

	//@LazyCollection(LazyCollectionOption.FALSE)
	//@OneToMany(cascade = { CascadeType.PERSIST,CascadeType.MERGE})
	 @ManyToMany(cascade = { CascadeType.PERSIST,CascadeType.MERGE})
	 @JoinTable(
              name="ClasseEleves",
              joinColumns={@JoinColumn(name="classe_id", referencedColumnName="id")},
              inverseJoinColumns={@JoinColumn(name="eleves_elv_id", referencedColumnName="elv_id")})
	private Set<Eleve> eleves = new HashSet<Eleve>();
	public Classe() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Classe(String nom) {
		super();
		this.nom = nom;
	}

	public Collection<MatiereProfesseur> getMat_prof() {
		return mat_prof;
	}

	public void setMat_prof(Collection<MatiereProfesseur> mat_prof) {
		this.mat_prof = mat_prof;
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

	@Override
	public String toString() {
		return "Classe [id=" + id + ", nom=" + nom + ", mat_prof=" + mat_prof + "]";
	}


	
	public Set<Eleve> getEleves() {
		return eleves;
	}

	public void setEleves(Set<Eleve> eleves) {
		this.eleves = eleves;
	}

	@PreRemove
	private void removeMatProf() {
	    for (MatiereProfesseur mp : mat_prof) {
	    	mp.getClasses().remove(this);
	     
	    }
	}	

}
