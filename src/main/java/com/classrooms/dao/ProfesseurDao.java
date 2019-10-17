package com.classrooms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.classrooms.model.Professeur;

@Repository
public interface ProfesseurDao extends JpaRepository<Professeur, Integer> {
	
	@Query("delete from MatiereProfesseur mp where mp.matProf_prof.id=:id ")
    void  deletFromMatiereProfesseur(@Param("id") int id);
}
