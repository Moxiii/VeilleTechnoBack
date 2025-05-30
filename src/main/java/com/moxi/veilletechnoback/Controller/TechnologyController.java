package com.moxi.veilletechnoback.Controller;


import com.moxi.veilletechnoback.Config.JWT.Annotation.RequireAuthorization;
import com.moxi.veilletechnoback.DTO.Project.BasicProjectRes;
import com.moxi.veilletechnoback.DTO.Technology.TechnologyReq;
import com.moxi.veilletechnoback.DTO.Technology.TechnologyRes;
import com.moxi.veilletechnoback.Project.Project;
import com.moxi.veilletechnoback.Technology.Technology;
import com.moxi.veilletechnoback.Technology.TechnologyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RequireAuthorization
@RestController
@RequestMapping("/technology")
public class TechnologyController {
@Autowired
private TechnologyService technologyService;
private TechnologyRes toRes(Technology technology) {
	TechnologyRes res =  new TechnologyRes();
	res.setName(technology.getName());
	res.setId(technology.getId());
	res.setCategory(technology.getCategory());
	List<BasicProjectRes> basicProjects = new ArrayList<>();
	if(technology.getProjects() != null){
		for (Project project : technology.getProjects()) {
			BasicProjectRes basic = new BasicProjectRes();
			basic.setName(project.getName());
			basicProjects.add(basic);
		}
	}
	res.setProjects(basicProjects);
	res.setTrainingTime(technology.getTrainingTime());
	return res;

}
@GetMapping
public ResponseEntity<?> getTechnology() {
	List<TechnologyRes> res = technologyService.findAll()
			.stream()
			.map(this::toRes)
			.toList();
	return new ResponseEntity<>(res , HttpStatus.OK);
}
@GetMapping("/{id}")
public ResponseEntity<?> getTechnologyById(@PathVariable long id) {
	Technology technology = technologyService.findById(id);
	return new ResponseEntity<>(toRes(technology) , HttpStatus.OK);
}
@PostMapping
public ResponseEntity<?> createTechnology(@RequestBody Technology technology) {
	Technology newTechnology = new Technology();
	newTechnology.setName(technology.getName());
	newTechnology.setCategory(technology.getCategory());
	technologyService.save(newTechnology);
	return new ResponseEntity<>(toRes(newTechnology), HttpStatus.CREATED);
}
@PutMapping("/{id}")
public ResponseEntity<?> updateTechnology(@PathVariable long id, @RequestBody TechnologyReq updateTechnology) {
	Technology technology = technologyService.findById(id);
	technology.setName(updateTechnology.getName() != null ? updateTechnology.getName() : technology.getName());
	technology.setCategory(updateTechnology.getCategory() != null ? updateTechnology.getCategory() : technology.getCategory());
	technologyService.save(technology);
	return new ResponseEntity<>(toRes(technology), HttpStatus.OK);
}
@DeleteMapping("/{id}")
public ResponseEntity<?> deleteTechnology(@PathVariable long id) {
	Technology technology = technologyService.findById(id);
	technologyService.delete(technology);
	return new ResponseEntity<>(HttpStatus.OK);
}
}
