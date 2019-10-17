package com.classrooms.controller;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import com.classrooms.dao.ClasseDao;
import com.classrooms.model.Classe;
import com.classrooms.model.ClasseResult;
import com.classrooms.model.Eleve;
import com.classrooms.model.Matiere;
import com.classrooms.model.Professeur;
import com.classrooms.model.ProfesseurResult;
import com.classrooms.model.Role;
import com.classrooms.model.UserApp;
import com.classrooms.service.EcoleService;
import com.classrooms.service.UserService;
//@CrossOrigin("http://localhost:4200")
@RestController
public class EcoleController {

	@Autowired
	private EcoleService ecoleService;

	@Autowired
	private ClasseDao classeDao;
	
	@Autowired
	private UserService userService;
	// classrooms
	@GetMapping(value = "/rooms")
	public List<Classe> getRooms() {
		return ecoleService.findAllRooms();
	}

	@GetMapping(value = "/classes")
	public List<ClasseResult> afficherLesclasses() {
		return ecoleService.findAllClassRooms();

	}

	@PostMapping(value = "/classe")
	public List<ClasseResult> saveClassroom(@RequestBody ClasseResult classeResult) {
		
		for (int i = 0; i <10; i++) {
			ecoleService.addClassRoom(classeResult);
		}
		
		//ecoleService.addClassRoom(classeResult);
		
		
		
		return ecoleService.findAllClassRooms();

	}

	@DeleteMapping(value = "/classe/{id}")
	public List<ClasseResult> deletClassRoom(@PathVariable int id) {
		// Classe classe =ecoleService.findById(id);
		ecoleService.removeClassRoom(id);
		return ecoleService.findAllClassRooms();

	}
	// subjects

	@GetMapping(value = "/matiere/{id}")
	public Matiere getMatiereById(@PathVariable int id) {
		return ecoleService.getMatiereById(id);
	}

	// 1
	@GetMapping(value = "/matieres")
	public List<Matiere> getSubjects() {
		return ecoleService.findAllSubjects();
	}
	// 2
	// @GetMapping(value = "/matiers")
	// public ResponseEntity<List<Matiere>> getSubjects() {
	// List<Matiere> resp =ecoleService.findAllSubjects();
	// return new ResponseEntity<List<Matiere>>(resp, HttpStatus.OK);
	// }

	// teachers
	@GetMapping(value = "/professeurs")
	public List<ProfesseurResult> afficherLesProf() {
		return ecoleService.findTeachers();
	}

	@GetMapping(value = "/classe/{id}")
	public List<ProfesseurResult> afficherLesClassesParId(@PathVariable int id) {
		return ecoleService.findClasseByid(id);
	}

	@GetMapping(value = "/professeur/{id}")
	public Professeur getTeacherByid(@PathVariable int id) {
		Professeur prof = ecoleService.findProfesseurById(id);
		return prof;
	}

	@DeleteMapping(value = "/professeur/{id}")
	public List<ProfesseurResult> deletTeacher(@PathVariable int id) {
		System.err.println("deleting");
		// Professeur prof= ecoleService.findProfesseurById(id);
		ecoleService.deletprofesseur(id);
		return ecoleService.findTeachers();
	}

	// students
	@GetMapping(value = "/eleves/{id}")
	public List<Eleve> getStudentsByclasse(@PathVariable int id) {
		Classe classe = classeDao.findOne(id);
		return (List<Eleve>) classe.getEleves();

	}

	@GetMapping(value = "/eleves")
	public List<Eleve> getStudents() {
		return ecoleService.findAllStudents();

	}
	// with path variable
	// @PostMapping(value = "/eleve/{id}")
	// public void saveStudent(@PathVariable int id, @RequestBody Eleve eleve) {
	// Classe classe = classeDao.findOne(id);
	// classe.getEleves().add(eleve);
	// ecoleService.savClassroom(classe);
	// }

	@PostMapping(value = "/eleve")
	public List<ClasseResult> saveStudent(@RequestParam("id") int id, @RequestBody Eleve eleve) {

		System.err.println(" id : " + id);
		Classe classe = classeDao.findOne(id);
		classe.getEleves().add(eleve);
		ecoleService.savClassroom(classe);
		return ecoleService.findAllClassRooms();
	}

	// upload file
	@PostMapping("/upload")
	public Matiere singleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
		return ecoleService.uploadFile(file);
	}
	
	@PostMapping(value = "/create")
	public void createUser(@RequestBody UserApp userApp) {	
	//UserApp user = new UserApp("salim", "salim", 25);
		System.out.println("create UserApp : "+userApp);
		System.out.println("create UserApp : "+userApp.getUsername());
		System.out.println("create UserApp : "+userApp.getPassword());
		System.out.println("create UserApp : "+userApp.getRoles());
//		Role r1 = new Role("admin");
//		Role r2 = new Role("user");
//		userApp.getRoles().add(r1);
//		userApp.getRoles().add(r2);
		userService.save(userApp);

	}

	// call other microserviuce started on port 9095
//	@GetMapping(value = "/Produits/{id}")
//	public Product getProduits(@PathVariable int id) {
//
//		final String uri = "http://localhost:9095/Produits" + "/" + id;
//		RestTemplate restTemplate = new RestTemplate();
//		Product product = restTemplate.getForObject(uri, Product.class);
//
//		System.err.println("result :  " + product.getClass());
//		System.err.println("result :  " + product);
//		return product;
//	}

}
