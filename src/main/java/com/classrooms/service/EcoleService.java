package com.classrooms.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.classrooms.model.Classe;
import com.classrooms.model.ClasseResult;
import com.classrooms.model.Eleve;
import com.classrooms.model.Matiere;
import com.classrooms.model.Professeur;
import com.classrooms.model.ProfesseurResult;



public interface EcoleService {
	void  savClassroom(Classe classe);
    List<Classe> findAllRooms();
	List<Matiere> findAllSubjects();
	List<ProfesseurResult> findTeachers();
	Classe findById(int id);
	List<ProfesseurResult> findClasseByid(int id);
	List<ClasseResult> findAllClassRooms();
	Matiere getMatiereById(int id);
    Classe addClassRoom(ClasseResult classeResult);
    Matiere uploadFile(MultipartFile file) throws IOException;
    List<Eleve> findAllStudents();
    void removeClassRoom(int id);
    Professeur findProfesseurById(int id);
    void deletprofesseur(int id);

}
