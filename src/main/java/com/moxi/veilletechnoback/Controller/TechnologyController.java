package com.moxi.veilletechnoback.Controller;


import com.moxi.veilletechnoback.DTO.Technology.TechnologyReq;
import com.moxi.veilletechnoback.DTO.Technology.TechnologyRes;
import com.moxi.veilletechnoback.Technology.Technology;
import com.moxi.veilletechnoback.Technology.TechnologyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/technology")
public class TechnologyController {
@Autowired
private TechnologyService technologyService;
private TechnologyRes toRes(Technology technology) {
	TechnologyRes res =  new TechnologyRes();
	res.setName(technology.getName());
	res.setId(technology.getId());
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
	technologyService.save(newTechnology);
	return new ResponseEntity<>(toRes(newTechnology), HttpStatus.CREATED);
}
@PutMapping("/{id}")
public ResponseEntity<?> updateTechnology(@PathVariable long id, @RequestBody TechnologyReq updateTechnology) {
	Technology technology = technologyService.findById(id);
	technology.setName(updateTechnology.getName() != null ? updateTechnology.getName() : technology.getName());
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
