package com.classrooms.model;

import java.util.List;

public class ClasseResult {

	Classe classe;
	private List<ProfesseurResult> listProfMatiere;

	public Classe getClasse() {
		return classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}

	public List<ProfesseurResult> getListProfMatiere() {
		return listProfMatiere;
	}

	public void setListProfMatiere(List<ProfesseurResult> listProfMatiere) {
		this.listProfMatiere = listProfMatiere;
	}

	@Override
	public String toString() {
		return "ClasseResult [classe=" + classe + ", listProfMatiere=" + listProfMatiere + "]";
	}

}
