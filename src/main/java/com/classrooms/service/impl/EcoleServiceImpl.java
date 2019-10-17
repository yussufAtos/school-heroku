package com.classrooms.service.impl;


import static com.classrooms.exception.EntityType.MATIERE;
import static com.classrooms.exception.EntityType.CLASSE;
import static com.classrooms.exception.ExceptionType.ENTITY_NOT_FOUND;
import static com.classrooms.exception.ExceptionType.DUPLICATE_ENTITY;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.PreRemove;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.classrooms.dao.ClasseDao;
import com.classrooms.dao.EleveDao;
import com.classrooms.dao.MatiereDao;
import com.classrooms.dao.MatiereProfesseurDao;
import com.classrooms.dao.ProfesseurDao;
import com.classrooms.exception.EcoleException;
import com.classrooms.exception.EntityType;
import com.classrooms.exception.ExceptionType;
import com.classrooms.model.Classe;
import com.classrooms.model.ClasseResult;
import com.classrooms.model.Eleve;
import com.classrooms.model.Matiere;
import com.classrooms.model.MatiereProfesseur;
import com.classrooms.model.Professeur;
import com.classrooms.model.ProfesseurResult;
import com.classrooms.service.EcoleService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service(value = "ecoleService")
public class EcoleServiceImpl implements EcoleService {
	
	private int cpt;
	@Autowired
	private ClasseDao classeDao;

	@Autowired
	private MatiereDao matiereDao;

	@Autowired
	private ProfesseurDao professeurDao;

	@Autowired
	private MatiereProfesseurDao matiereProfDao;

	@Autowired
	private EleveDao eleveDao;

	// classrooms
	@Override
	public Classe findById(int id) {
		return classeDao.findOne(id);
		}

	@Override
	public List<Classe> findAllRooms() {
		return classeDao.findAll();
	}

	public void savClassroom(Classe classe) {
		classeDao.saveAndFlush(classe);
	}

	@Override
	public List<ClasseResult> findAllClassRooms() {
		List<Classe> classesList = classeDao.findAll();
		List<ClasseResult> ClasseResultList = new ArrayList<>();

		for (Classe classe : classesList) {
			boolean isProfExist = false;
			List<MatiereProfesseur> list = (List<MatiereProfesseur>) classe.getMat_prof();
			List<ProfesseurResult> professeurResultList = new ArrayList<>();

			for (MatiereProfesseur mp : list) {
				isProfExist = false;
				for (ProfesseurResult pr : professeurResultList) {
					if (pr.getProfesseur().equals(mp.getMatProf_prof())) {
						ProfesseurResult prRemplace = professeurResultList.get(professeurResultList.indexOf(pr));
						prRemplace.getMatieres().add(mp.getMatProf_mat());
						professeurResultList.set(professeurResultList.indexOf(pr), prRemplace);
						isProfExist = true;
					}
				}

				if (isProfExist == false) {
					ProfesseurResult professeurResult = new ProfesseurResult();
					professeurResult.setProfesseur(mp.getMatProf_prof());
					professeurResult.getMatieres().add(mp.getMatProf_mat());
					professeurResultList.add(professeurResult);
				}
			}
			ClasseResult ClasseResult = new ClasseResult();
			ClasseResult.setClasse(classe);
			ClasseResult.setListProfMatiere(professeurResultList);
			ClasseResultList.add(ClasseResult);

		}
		return ClasseResultList;
	}

	@Override
	@Transactional
	public Classe addClassRoom(ClasseResult classeResult) {
		//Classe classe = classeDao.findByNom(classeResult.getClasse().getNom());
		//if (classe == null) {
		
			cpt=cpt+1;
		Classe cls = new Classe(classeResult.getClasse().getNom()+"** "+cpt);
			List<MatiereProfesseur> list = new ArrayList<>();
			Set<Classe> classes = new HashSet<>();
			classes.add(cls);
			for (ProfesseurResult pr : classeResult.getListProfMatiere()) {
				Professeur prof = new Professeur(pr.getProfesseur().getNom(), pr.getProfesseur().getPrenom());
				for (Matiere m : pr.getMatieres()) {
					MatiereProfesseur matprof = new MatiereProfesseur();
					matprof.setMatProf_prof(prof);
					matprof.setMatProf_mat(m);
					matprof.setClasses(classes);
					list.add(matprof);
					matiereProfDao.save(matprof);

				}
			}
			cls.setMat_prof(list);
			classeDao.save(cls);

			return cls;

		//}
		//throw exception(CLASSE, DUPLICATE_ENTITY, classe.getNom());
	}

	@Override
	@Transactional
	// @PreRemove
	public void removeClassRoom(int id) {
		classeDao.delete(id);
	}

	// teachers
	@Override
	public Professeur findProfesseurById(int id) {
		return professeurDao.findOne(id);
	}

	@Override
	// @Transactional
	public void deletprofesseur(int id) {
		professeurDao.delete(id);
	}

	@Override
	public List<ProfesseurResult> findTeachers() {
		// TODO Auto-generated method stub
		List<Professeur> profs = professeurDao.findAll();
		List<ProfesseurResult> professeurResultList = new ArrayList<>();
		for (Professeur prof : profs) {
			List<Matiere> matieres = new ArrayList<>();
			for (MatiereProfesseur matProf : prof.getMatProf_profCollection()) {
				matieres.add(matProf.getMatProf_mat());
			}
			ProfesseurResult professeurResult = new ProfesseurResult();
			professeurResult.setProfesseur(prof);
			professeurResult.setMatieres(matieres);

			professeurResultList.add(professeurResult);
		}

		return professeurResultList;
	}

	@Override
	public List<ProfesseurResult> findClasseByid(int id) {
		Optional<Classe> clsOptional = Optional.ofNullable(classeDao.findOne(id));
		Classe cls;

		if (clsOptional.isPresent()) {
			cls = classeDao.findOne(id);
			List<MatiereProfesseur> list = (List<MatiereProfesseur>) cls.getMat_prof();
			List<ProfesseurResult> professeurResultList = new ArrayList<>();
			boolean isProfExist = false;
			for (MatiereProfesseur mp : list) {
				isProfExist = false;
				for (ProfesseurResult pr : professeurResultList) {
					if (pr.getProfesseur().equals(mp.getMatProf_prof())) {
						ProfesseurResult prRemplace = professeurResultList.get(professeurResultList.indexOf(pr));
						prRemplace.getMatieres().add(mp.getMatProf_mat());
						professeurResultList.set(professeurResultList.indexOf(pr), prRemplace);
						isProfExist = true;
					}
				}

				if (isProfExist == false) {
					ProfesseurResult professeurResult = new ProfesseurResult();
					professeurResult.setProfesseur(mp.getMatProf_prof());
					professeurResult.getMatieres().add(mp.getMatProf_mat());
					professeurResultList.add(professeurResult);
				}
			}

			return professeurResultList;
		}
		throw exception(CLASSE, ENTITY_NOT_FOUND, Integer.toString(id));
	}

	// subjects
	@Override
	public Matiere getMatiereById(int id) {
		// 1
		Optional<Matiere> mat = Optional.ofNullable(matiereDao.findOne(id));
		if (mat.isPresent()) {
			System.err.println(mat);
			return matiereDao.findOne(id);
		}
		throw exception(MATIERE, ENTITY_NOT_FOUND, Integer.toString(id));
		// 2

		// Matiere mat=matiereDao.findOne(id);
		// if(mat!=null) {
		// return matiereDao.findOne(id);
		// }
		// throw exception(MATIERE, ENTITY_NOT_FOUND, Integer.toString(id));

	}

	@Override
	public List<Matiere> findAllSubjects() {
		// TODO Auto-generated method stub
		return matiereDao.findAll();
	}

	// students
	@Override
	public List<Eleve> findAllStudents() {

		return eleveDao.findAll();
	}

	// file upload
	@Override
	public Matiere uploadFile(MultipartFile file) throws IOException {
		System.err.println("file   :" + file);
		byte[] bytes = file.getBytes();
		String mt = new String(bytes);
		ObjectMapper mapper = new ObjectMapper();

		Matiere mat = mapper.readValue(mt, Matiere.class);
		System.err.println("mat.getName()   :" + mat.getName());
		Matiere matiere = new Matiere(mat.getName());
		Matiere m = matiereDao.save(matiere);
		return m;
	}

	private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
		return EcoleException.throwException(entityType, exceptionType, args);
	}

}
