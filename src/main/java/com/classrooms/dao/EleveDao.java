package com.classrooms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.classrooms.model.Eleve;

@Repository
public interface EleveDao  extends JpaRepository<Eleve, Integer>{

}
