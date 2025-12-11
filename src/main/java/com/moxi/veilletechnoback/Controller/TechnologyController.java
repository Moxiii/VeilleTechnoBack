package com.moxi.veilletechnoback.Controller;


import com.moxi.veilletechnoback.Category.Category;
import com.moxi.veilletechnoback.Category.CategoryService;
import com.moxi.veilletechnoback.Category.SubCat.SubCategory;
import com.moxi.veilletechnoback.Category.SubCat.SubCategoryService;

import com.moxi.veilletechnoback.Config.Security.SecurityUtils;
import com.moxi.veilletechnoback.DTO.Category.CatwithSub;
import com.moxi.veilletechnoback.DTO.Category.sub.SubCategoryRes;
import com.moxi.veilletechnoback.DTO.Project.BasicProjectRes;
import com.moxi.veilletechnoback.DTO.Technology.TechnologyReq;
import com.moxi.veilletechnoback.DTO.Technology.TechnologyRes;
import com.moxi.veilletechnoback.Project.Project;
import com.moxi.veilletechnoback.Project.ProjectService;
import com.moxi.veilletechnoback.Technology.Technology;
import com.moxi.veilletechnoback.Technology.TechnologyService;

import com.moxi.veilletechnoback.User.User;

import lombok.RequiredArgsConstructor;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/technology")
public class TechnologyController {

private final TechnologyService technologyService;

private final CategoryService categoryService;

private final SubCategoryService subCategoryService;

private final SecurityUtils securityUtils;

private final ProjectService projectService;

private TechnologyRes techToRes(Technology technology) {
	TechnologyRes res =  new TechnologyRes();
	res.setName(technology.getName());
		if (technology.getId() != null){
    		res.setId(technology.getId());
    	}
	CatwithSub catDTO = new CatwithSub();
	Category cat = technology.getCategory();
	if (cat != null) {
		catDTO.setDefaultCategory(cat.isDefaultCategory());
		catDTO.setType(cat.getType());
		if (!cat.isDefaultCategory()) {
			catDTO.setName(cat.getName());
		}
	}
	List<SubCategory> subCats = subCategoryService.findByCategory(technology.getCategory());
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
	res.setCreatedAt(technology.getCreatedAt());
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
	User currentUser = securityUtils.getCurrentUser();
	Technology technology = technologyService.findByUserAndId(currentUser,id);
	return new ResponseEntity<>(techToRes(technology) , HttpStatus.OK);
}
@PostMapping
public ResponseEntity<?> createTechnology(@RequestBody TechnologyReq technology) {
	User currentUser = securityUtils.getCurrentUser();
	Technology createdTech = technologyService.create(technology, currentUser);
	return new ResponseEntity<>(techToRes(createdTech), HttpStatus.CREATED);
}
@PutMapping("/{id}")
public ResponseEntity<?> updateTechnology(@PathVariable long id, @RequestBody TechnologyReq updateTechnology) {
	User currentUser = securityUtils.getCurrentUser();
	Technology technology = technologyService.findByUserAndId(currentUser,id);
	Category category = categoryService.findById(updateTechnology.getCategoryId());
	List<SubCategory> subCategories = new ArrayList<>();
	if(updateTechnology.getSubCategoryIds() != null && !updateTechnology.getSubCategoryIds().isEmpty()){
		subCategories = updateTechnology
				.getSubCategoryIds()
				.stream()
				.map(subCategoryService::findById)
				.toList();
	}
	technologyService.update(technology , updateTechnology.getName(), category , subCategories);
	return new ResponseEntity<>(techToRes(technology), HttpStatus.OK);
}
@DeleteMapping("/{id}")
public ResponseEntity<?> deleteTechnology(@PathVariable long id) {
	User currentUser = securityUtils.getCurrentUser();
	Technology technology = technologyService.findByUserAndId(currentUser,id);
	List<Project> projects = projectService.findAllByUser(currentUser);
	for(Project project : projects){
		if(project.getTechnology().contains(technology)){
			project.getTechnology().remove(technology);
			projectService.save(project);
		}
	}
	technologyService.delete(technology);
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}

}
