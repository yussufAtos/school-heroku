package com.classrooms.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Eleve implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private int elv_id;
	// @Column(name:'elv_nom')
	@Column(name = "elv_nom")
	private String lastName;
	@Column(name = "elv_prenom")
	private String firstName;

	@JsonIgnore
	@ManyToMany(mappedBy = "eleves")
	private Set<Classe> classes = new HashSet<Classe>();

	public Eleve(int elv_id, String lastName, String firstName) {
		this.elv_id = elv_id;
		this.lastName = lastName;
		this.firstName = firstName;

	}

	public Eleve(String lastName, String firstName) {
		this.lastName = lastName;
		this.firstName = firstName;

	}

	public Eleve() {
		super();
	}

	public int getElv_id() {
		return elv_id;
	}

	public void setElv_id(int elv_id) {
		this.elv_id = elv_id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Set<Classe> getClasses() {
		return classes;
	}

	public void setClasses(Set<Classe> classes) {
		this.classes = classes;
	}

	@Override
	public String toString() {
		return "Eleve [elv_id=" + elv_id + ", lastName=" + lastName + ", firstName=" + firstName + "]";
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
