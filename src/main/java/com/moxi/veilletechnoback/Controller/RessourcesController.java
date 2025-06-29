package com.moxi.veilletechnoback.Controller;

import com.moxi.veilletechnoback.Config.Security.SecurityUtils;
import com.moxi.veilletechnoback.DTO.Ressources.RessourcesReq;
import com.moxi.veilletechnoback.DTO.Ressources.RessourcesRes;
import com.moxi.veilletechnoback.DTO.Technology.BasicTechnologyRes;
import com.moxi.veilletechnoback.Enum.Ressources.labelName;
import com.moxi.veilletechnoback.Ressources.Ressources;
import com.moxi.veilletechnoback.Ressources.RessourcesService;
import com.moxi.veilletechnoback.Technology.Technology;
import com.moxi.veilletechnoback.Technology.TechnologyService;
import com.moxi.veilletechnoback.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ressources")
public class RessourcesController {
@Autowired
private RessourcesService ressourcesService;
@Autowired
private TechnologyService technologyService;
@Autowired
private SecurityUtils securityUtils;

private RessourcesRes toRes(Ressources ressources) {
	RessourcesRes res = new RessourcesRes();
	res.setLabel(ressources.getLabel());
	res.setUrl(ressources.getUrl());
	BasicTechnologyRes basicTechnologyRes = new BasicTechnologyRes();
	basicTechnologyRes.setName(ressources.getTechnology().getName());
	res.setTechnology(basicTechnologyRes);
	res.setCreateAt(ressources.getCreateAt());
	res.setDescription(ressources.getDescription());
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
	User currentUser = securityUtils.getCurrentUser();
	Ressources ressources = ressourcesService.findByUserAndId(currentUser,id);
	return new ResponseEntity<>(toRes(ressources) , HttpStatus.OK);
}
@PostMapping
public ResponseEntity<?> createRessources(@RequestBody RessourcesReq ressources) {
	Ressources newRessources = new Ressources();
	User currentUser = securityUtils.getCurrentUser();
	Technology tech = technologyService.findByUserAndId(currentUser, ressources.getTechnologyId());
	if(tech == null) {
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	newRessources.setTechnology(tech);
	newRessources.setLabel(ressources.getLabel());
	newRessources.setUrl(ressources.getUrl());
	newRessources.setUser(currentUser);
	ressourcesService.save(newRessources);
	return new ResponseEntity<>(toRes(newRessources), HttpStatus.CREATED);
}
@PutMapping("/{id}")
public ResponseEntity<?> updateRessources(@PathVariable long id, @RequestBody RessourcesReq updateRessources) {
	User currentUser = securityUtils.getCurrentUser();
	Technology tech = technologyService.findByUserAndId(currentUser,updateRessources.getTechnologyId());
	Ressources ressources = ressourcesService.findByUserAndId(currentUser,id);
	ressources.setLabel(updateRessources.getLabel() != null ? updateRessources.getLabel() : ressources.getLabel());
	ressources.setTechnology(tech != null ? tech : ressources.getTechnology());
	ressources.setUrl(updateRessources.getUrl() != null ? updateRessources.getUrl() : ressources.getUrl());
	ressourcesService.save(ressources);
	return new ResponseEntity<>(toRes(ressources), HttpStatus.OK);
}
@DeleteMapping("/{id}")
public ResponseEntity<?> deleteRessources(@PathVariable long id) {
	User currentUser = securityUtils.getCurrentUser();
	Ressources ressources = ressourcesService.findByUserAndId(currentUser,id);
	ressourcesService.delete(ressources);
	return new ResponseEntity<>(HttpStatus.OK);
}
@GetMapping("/label")
public List<String> getLabelName(){
	return Arrays.stream(labelName.values()).map(Enum::name).collect(Collectors.toList());
}
}
