package com.moxi.veilletechnoback.Controller;

import com.moxi.veilletechnoback.Config.Security.SecurityUtils;
import com.moxi.veilletechnoback.DTO.Project.ProjectReq;
import com.moxi.veilletechnoback.DTO.Project.UpdateProjectReq;
import com.moxi.veilletechnoback.DTO.Technology.BasicTechnologyRes;
import com.moxi.veilletechnoback.Enum.Status;
import com.moxi.veilletechnoback.Project.Project;
import com.moxi.veilletechnoback.Project.ProjectService;

import com.moxi.veilletechnoback.Technology.Technology;
import com.moxi.veilletechnoback.Technology.TechnologyService;
import com.moxi.veilletechnoback.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.moxi.veilletechnoback.DTO.Project.ProjectRes;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/project")
public class ProjectController {
@Autowired
private ProjectService projectService;
@Autowired
private TechnologyService technologyService;
@Autowired
private SecurityUtils securityUtils;

private ProjectRes toRes(Project project) {
	ProjectRes res = new ProjectRes();
	res.setId(project.getId());
	res.setName(project.getName());
	res.setStatus(project.getStatus());
	List< BasicTechnologyRes> technologyName = Optional.ofNullable(project.getTechnology())
			.orElse(Collections.emptyList())
			.stream()
			.map(tech ->{
				BasicTechnologyRes technologyRes = new BasicTechnologyRes();
				technologyRes.setName(tech.getName());
				return technologyRes;
	}).toList();
	res.setTechnology(technologyName);
	res.setCreatedDate(project.getCreateAt());
	res.setStartDate(project.getStartDate());
	res.setEndDate(project.getEndDate());
	res.setLinks(project.getLinks());
	return res;
}
@GetMapping
public ResponseEntity<?> getProject() {
	User currentUser = securityUtils.getCurrentUser();
	List<ProjectRes> res = projectService.findAllByUser(currentUser)
			.stream()
			.map(this::toRes)
			.toList();
	return new ResponseEntity<>(res , HttpStatus.OK);
}
@GetMapping("/{id}")
public ResponseEntity<?> getProjectById(@PathVariable long id) {
	User currentUser = securityUtils.getCurrentUser();
	Project project = projectService.findByUserAndId(currentUser, id);
	return new ResponseEntity<>(toRes(project) , HttpStatus.OK);
}
@PostMapping
public ResponseEntity<?> createProject(@RequestBody ProjectReq projectReq) {
	User currentUser = securityUtils.getCurrentUser();
	Project newProject = new Project();
	newProject.setName(projectReq.getProjectName());
	LocalDate aujourdhui = LocalDate.now();
	newProject.setCreateAt(aujourdhui);
	if (projectReq.getProjectName() == null || projectReq.getProjectName().isBlank()) {
		return ResponseEntity.badRequest().body("Project name is required");
	}
	newProject.setStatus(projectReq.getStatus() != null ? projectReq.getStatus() : Status.notStarted);
	newProject.setStartDate(projectReq.getStartDate() != null ? projectReq.getStartDate() : null);
	newProject.setEndDate(projectReq.getEndDate() != null ? projectReq.getEndDate() : null);
	newProject.setLinks(projectReq.getLinks() != null ? projectReq.getLinks() : null);
	newProject.setUser(currentUser);
	if (projectReq.getTechnology() != null) {
		List<Technology> technologies = projectReq.getTechnology().stream()
				.map(techRes -> technologyService.findByUserAndId(currentUser,techRes.getId()))
				.toList();

		newProject.setTechnology(technologies);
	} else {
		newProject.setTechnology(null);
	}
	projectService.save(newProject);
	return new ResponseEntity<>(toRes(newProject), HttpStatus.CREATED);
}
@PutMapping("/{id}")
public ResponseEntity<?> updateProject(@PathVariable long id, @RequestBody UpdateProjectReq updateProject) {
	User currentUser = securityUtils.getCurrentUser();
	Project project = projectService.findByUserAndId(currentUser,id);
	project.setName(updateProject.getName() != null ? updateProject.getName() : project.getName());
	project.setLinks(updateProject.getLinks() != null ? updateProject.getLinks() : project.getLinks());
	List<Technology> technologies = updateProject.getTechnology() != null
			? technologyService.findAllById(updateProject.getTechnology())
			: project.getTechnology();

	project.setTechnology(technologies);
	projectService.save(project);
	return new ResponseEntity<>(toRes(project), HttpStatus.OK);
}
@DeleteMapping("/{id}")
public ResponseEntity<?> deleteProject(@PathVariable long id) {
	User currentUser = securityUtils.getCurrentUser();
	Project project = projectService.findByUserAndId(currentUser,id);
	projectService.delete(project);
	return new ResponseEntity<>(HttpStatus.OK);
}
@GetMapping("/status")
public List<String> getStatusJson() {
	return Arrays.stream(Status.values()).map(Enum::name).collect(Collectors.toList());
}
}
