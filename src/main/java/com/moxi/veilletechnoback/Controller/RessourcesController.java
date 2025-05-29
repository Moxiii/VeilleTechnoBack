package com.moxi.veilletechnoback.Controller;

import com.moxi.veilletechnoback.DTO.Ressources.RessourcesReq;
import com.moxi.veilletechnoback.DTO.Ressources.RessourcesRes;
import com.moxi.veilletechnoback.Ressources.Ressources;
import com.moxi.veilletechnoback.Ressources.RessourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/ressources")
public class RessourcesController {
@Autowired
private RessourcesService ressourcesService;

private RessourcesRes toRes(Ressources ressources) {
	RessourcesRes res = new RessourcesRes();
	res.setLabel(ressources.getLabel());
	res.setUrl(ressources.getUrl());
	res.setTechnology(ressources.getTechnology());
	return res;
}

@GetMapping
public ResponseEntity<?> getRessources() {
	List<RessourcesRes> res = ressourcesService.findAll()
			.stream()
			.map(this::toRes)
			.toList();
	return new ResponseEntity<>(res , HttpStatus.OK);
}
@GetMapping("/{id}")
public ResponseEntity<?> getRessourcesById(@PathVariable long id) {
	Ressources ressources = ressourcesService.findById(id);
	return new ResponseEntity<>(toRes(ressources) , HttpStatus.OK);
}
@PostMapping
public ResponseEntity<?> createRessources(@RequestBody RessourcesReq ressources) {
	Ressources newRessources = new Ressources();
	newRessources.setTechnology(ressources.getTechnology());
	newRessources.setLabel(ressources.getLabel());
	newRessources.setUrl(ressources.getUrl());
	ressourcesService.save(newRessources);
	return new ResponseEntity<>(toRes(newRessources), HttpStatus.CREATED);
}
@PutMapping("/{id}")
public ResponseEntity<?> updateRessources(@PathVariable long id, @RequestBody RessourcesReq updateRessources) {
	Ressources ressources = ressourcesService.findById(id);
	ressources.setLabel(updateRessources.getLabel() != null ? updateRessources.getLabel() : ressources.getLabel());
	ressources.setTechnology(updateRessources.getTechnology() != null ? updateRessources.getTechnology() : ressources.getTechnology());
	ressources.setUrl(updateRessources.getUrl() != null ? updateRessources.getUrl() : ressources.getUrl());
	ressourcesService.save(ressources);
	return new ResponseEntity<>(toRes(ressources), HttpStatus.OK);
}
@DeleteMapping("/{id}")
public ResponseEntity<?> deleteRessources(@PathVariable long id) {
	Ressources ressources = ressourcesService.findById(id);
	ressourcesService.delete(ressources);
	return new ResponseEntity<>(HttpStatus.OK);
}
}
