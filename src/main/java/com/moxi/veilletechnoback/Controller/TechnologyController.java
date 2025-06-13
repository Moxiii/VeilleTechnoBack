package com.moxi.veilletechnoback.Controller;


import com.moxi.veilletechnoback.Category.SubCat.SubCategory;
import com.moxi.veilletechnoback.Category.SubCat.SubCategoryService;
import com.moxi.veilletechnoback.Config.JWT.Annotation.RequireAuthorization;
import com.moxi.veilletechnoback.Config.Security.SecurityUtils;
import com.moxi.veilletechnoback.DTO.Category.CatwithSub;
import com.moxi.veilletechnoback.DTO.Category.sub.SubCategoryRes;
import com.moxi.veilletechnoback.DTO.Project.BasicProjectRes;
import com.moxi.veilletechnoback.DTO.Technology.TechnologyReq;
import com.moxi.veilletechnoback.DTO.Technology.TechnologyRes;
import com.moxi.veilletechnoback.Project.Project;
import com.moxi.veilletechnoback.Technology.Technology;
import com.moxi.veilletechnoback.Technology.TechnologyService;

import com.moxi.veilletechnoback.User.User;
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
@Autowired
private SubCategoryService subCategoryService;


private TechnologyRes techToRes(Technology technology) {
	TechnologyRes res =  new TechnologyRes();
	res.setName(technology.getName());
	res.setId(technology.getId());
	CatwithSub catDTO = new CatwithSub();
	catDTO.setType(technology.getCategory());
	List<SubCategory> subCats = subCategoryService.findByCategoryEnum(technology.getCategory());
	List<SubCategoryRes> subCatDTO = subCats.stream().map(sub -> new SubCategoryRes(sub.getName())).toList();
	catDTO.setSubCategories(subCatDTO);
	res.setCategory(catDTO);
	List<BasicProjectRes> basicProjects = new ArrayList<>();
	if(technology.getProjects() != null){
		for (Project project : technology.getProjects()) {
			BasicProjectRes basic = new BasicProjectRes();
			basic.setName(project.getName());
			basicProjects.add(basic);
		}
	}
	res.setCreateAt(technology.getCreateAt());
	res.setProjects(basicProjects);
	res.setTrainingTime(technology.getTrainingTime());
	return res;

}
@GetMapping
public ResponseEntity<?> getTechnology() {
	List<TechnologyRes> res = technologyService.findAll()
			.stream()
			.map(this::techToRes)
			.toList();
	return new ResponseEntity<>(res , HttpStatus.OK);
}
@GetMapping("/{id}")
public ResponseEntity<?> getTechnologyById(@PathVariable long id) {
	User currentUser = SecurityUtils.getCurrentUser();
	Technology technology = technologyService.findByUserAndId(currentUser,id);
	return new ResponseEntity<>(techToRes(technology) , HttpStatus.OK);
}
@PostMapping
public ResponseEntity<?> createTechnology(@RequestBody Technology technology) {
	User currentUser = SecurityUtils.getCurrentUser();
	Technology newTechnology = new Technology();
	newTechnology.setName(technology.getName());
	newTechnology.setCategory(technology.getCategory());
	newTechnology.setUser(currentUser);
	technologyService.create(technology.getName() , technology.getCategory() , currentUser);
	return new ResponseEntity<>(techToRes(newTechnology), HttpStatus.CREATED);
}
@PutMapping("/{id}")
public ResponseEntity<?> updateTechnology(@PathVariable long id, @RequestBody TechnologyReq updateTechnology) {
	User currentUser = SecurityUtils.getCurrentUser();
	Technology technology = technologyService.findByUserAndId(currentUser,id);
	SubCategory subCategory = null;
	if(updateTechnology.getSubCategoryId() != null){
		subCategory = subCategoryService.findById(updateTechnology.getSubCategoryId());
	}
	technologyService.update(technology , updateTechnology.getName(), updateTechnology.getCategory() , subCategory);
	return new ResponseEntity<>(techToRes(technology), HttpStatus.OK);
}
@DeleteMapping("/{id}")
public ResponseEntity<?> deleteTechnology(@PathVariable long id) {
	User currentUser = SecurityUtils.getCurrentUser();
	Technology technology = technologyService.findByUserAndId(currentUser,id);
	technologyService.delete(technology);
	return new ResponseEntity<>(HttpStatus.OK);
}

}
