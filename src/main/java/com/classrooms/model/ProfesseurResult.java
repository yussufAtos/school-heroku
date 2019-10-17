package com.classrooms.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProfesseurResult implements Serializable {

	private static final long serialVersionUID = 1L;
	private Professeur professeur;
	private List<Matiere> matieres;

	public ProfesseurResult() {
		super();
		matieres = new ArrayList<>();
		// TODO Auto-generated constructor stub
	}

	public Professeur getProfesseur() {
		return professeur;
	}

	public void setProfesseur(Professeur professeur) {
		this.professeur = professeur;
	}

	public List<Matiere> getMatieres() {
		return matieres;
	}

	public void setMatieres(List<Matiere> matieres) {
		this.matieres = matieres;
	}

	@Override
	public String toString() {
		return "ProfesseurResult [professeur=" + professeur + ", matieres=" + matieres + "]";
	}

}
