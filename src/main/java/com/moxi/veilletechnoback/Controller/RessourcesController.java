package com.moxi.veilletechnoback.Controller;

import com.moxi.veilletechnoback.Category.Category;
import com.moxi.veilletechnoback.Category.CategoryService;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@RestController
@RequestMapping("/ressources")
public class RessourcesController {
@Autowired
private RessourcesService ressourcesService;
@Autowired
private TechnologyService technologyService;
@Autowired
private SecurityUtils securityUtils;
@Autowired
private CategoryService categoryService;

private RessourcesRes toRes(Ressources ressources) {
	RessourcesRes res = new RessourcesRes();
	res.setId(ressources.getId());
	res.setLabel(ressources.getLabel());
	res.setUrl(ressources.getUrl());
	res.setName(ressources.getName());
	BasicTechnologyRes basicTechnologyRes = new BasicTechnologyRes();
	basicTechnologyRes.setId(ressources.getTechnology().getId());
	basicTechnologyRes.setName(ressources.getTechnology().getName());
	res.setTechnology(basicTechnologyRes);
	res.setCreatedAt(ressources.getCreatedAt());
	res.setDescription(ressources.getDescription());
	res.setTags(ressources.getTags());
	res.setUpdatedAt(ressources.getUpdatedAt());
	if (ressources.getCategory() != null) {
		res.setCategoryId(ressources.getCategory().getId());
		res.setType(ressources.getCategory().getType());
	}
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
	LocalDate aujourdhui = LocalDate.now();
	User currentUser = securityUtils.getCurrentUser();
	Technology tech = technologyService.findByUserAndId(currentUser, ressources.getTechnologyId());
	if(tech == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	newRessources.setTechnology(tech);
	if(ressources.getCategoryId() !=null){
		Category category = categoryService.findById(ressources.getCategoryId());
		newRessources.setCategory(category);
	}
	newRessources.setName(ressources.getName());
	newRessources.setLabel(ressources.getLabel());
	newRessources.setUrl(ressources.getUrl());
	newRessources.setUser(currentUser);
	newRessources.setCreatedAt(aujourdhui);
	newRessources.setDescription(ressources.getDescription());
	newRessources.setTags(ressources.getTags() != null ? ressources.getTags() : new HashSet<>());
	newRessources.setUpdatedAt(aujourdhui);

	ressourcesService.save(newRessources);
	return new ResponseEntity<>(toRes(newRessources), HttpStatus.CREATED);
}
@PutMapping("/{id}")
public ResponseEntity<?> updateRessources(@PathVariable long id, @RequestBody RessourcesReq updateRessources) {
	User currentUser = securityUtils.getCurrentUser();
	Technology tech = technologyService.findByUserAndId(currentUser,updateRessources.getTechnologyId());
	Ressources ressources = ressourcesService.findByUserAndId(currentUser,id);
	Category category = categoryService.findById(updateRessources.getCategoryId());
	ressources.setLabel(updateRessources.getLabel() != null ? updateRessources.getLabel() : ressources.getLabel());
	ressources.setName(updateRessources.getName() != null ? updateRessources.getName() : ressources.getName());
	ressources.setTechnology(tech != null ? tech : ressources.getTechnology());
	ressources.setUrl(updateRessources.getUrl() != null ? updateRessources.getUrl() : ressources.getUrl());
	ressources.setDescription(updateRessources.getDescription() != null ? updateRessources.getDescription() : ressources.getDescription());
	ressources.setTags(updateRessources.getTags() != null ? updateRessources.getTags() : ressources.getTags());
	ressources.setCategory(category != null ? category : ressources.getCategory());
	ressources.setUpdatedAt(LocalDate.now());
	ressourcesService.update(ressources);
	return new ResponseEntity<>(toRes(ressources), HttpStatus.OK);
}
@DeleteMapping("/{id}")
public ResponseEntity<?> deleteRessources(@PathVariable long id) {
	User currentUser = securityUtils.getCurrentUser();
	Ressources ressources = ressourcesService.findByUserAndId(currentUser,id);
	ressourcesService.delete(ressources);
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}
@GetMapping("/label")
public List<String> getLabelName(){
	return Arrays.stream(labelName.values()).map(Enum::name).collect(Collectors.toList());
}
}
