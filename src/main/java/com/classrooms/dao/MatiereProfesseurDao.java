package com.classrooms.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.classrooms.model.MatiereProfesseur;

public interface MatiereProfesseurDao extends JpaRepository<MatiereProfesseur, Integer> {

}
