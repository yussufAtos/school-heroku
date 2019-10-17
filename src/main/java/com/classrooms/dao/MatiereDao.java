package com.classrooms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.classrooms.model.Matiere;

@Repository
public interface MatiereDao extends JpaRepository<Matiere, Integer>{

}
