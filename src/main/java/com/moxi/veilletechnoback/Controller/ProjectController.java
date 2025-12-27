package com.moxi.veilletechnoback.Controller;

import com.moxi.veilletechnoback.Config.Security.SecurityUtils;
import com.moxi.veilletechnoback.DTO.Concepts.ConceptsRes;
import com.moxi.veilletechnoback.DTO.Project.ProjectReq;
import com.moxi.veilletechnoback.DTO.Project.UpdateProjectReq;
import com.moxi.veilletechnoback.DTO.Technology.BasicTechnologyRes;
import com.moxi.veilletechnoback.Enum.Project.Status;
import com.moxi.veilletechnoback.Project.Project;
import com.moxi.veilletechnoback.Project.ProjectService;

import com.moxi.veilletechnoback.Technology.Technology;
import com.moxi.veilletechnoback.Technology.TechnologyService;
import com.moxi.veilletechnoback.User.User;

import lombok.RequiredArgsConstructor;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.moxi.veilletechnoback.DTO.Project.ProjectRes;


import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

private final ProjectService projectService;

private final TechnologyService technologyService;

private final SecurityUtils securityUtils;

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
				technologyRes.setId(tech.getId());
				technologyRes.setName(tech.getName());

				return technologyRes;
	}).toList();
	res.setTechnology(technologyName);
	res.setCreatedAt(project.getCreatedAt());
	res.setStartDate(project.getStartDate());
	res.setEndDate(project.getEndDate());
	res.setLinks(project.getLinks());
	List<ConceptsRes> conceptsRes = project.getConcepts().stream()
    .map(c -> {
        ConceptsRes cr = new ConceptsRes();
        cr.setId(c.getId());
        cr.setName(c.getName());
        cr.setDescription(c.getDescription());
        cr.setCategoryName(c.getCategory().getName());
		cr.setProjectsId(c.getProjects().stream().map(Project::getId).toList());
        return cr;
    }).toList();

	res.setConcepts(conceptsRes);
	return res;
}
@GetMapping
public ResponseEntity<List<ProjectRes>> getProject() {
	User currentUser = securityUtils.getCurrentUser();
	List<ProjectRes> res = projectService.findAllByUser(currentUser)
			.stream()
			.map(this::toRes)
			.toList();
	return new ResponseEntity<>(res , HttpStatus.OK);
}
@GetMapping("/{id}")
public ResponseEntity<ProjectRes> getProjectById(@PathVariable long id) {
	User currentUser = securityUtils.getCurrentUser();
	Project project = projectService.findByUserAndId(currentUser, id);
	return new ResponseEntity<>(toRes(project) , HttpStatus.OK);
}
@PostMapping
public ResponseEntity<ProjectRes> createProject(@RequestBody ProjectReq projectReq) {
	User currentUser = securityUtils.getCurrentUser();
	Project newProject = new Project();
	newProject.setName(projectReq.getName());
	LocalDate aujourdhui = LocalDate.now();
	newProject.setCreatedAt(aujourdhui);
	newProject.setStatus(projectReq.getStatus() != null ? projectReq.getStatus() : Status.notStarted);
	newProject.setStartDate(projectReq.getStartDate() != null ? projectReq.getStartDate() : null);
	newProject.setEndDate(projectReq.getEndDate() != null ? projectReq.getEndDate() : null);
	newProject.setLinks(projectReq.getLinks() != null ? projectReq.getLinks() : null);
	newProject.setUser(currentUser);
	if (projectReq.getTechnology() != null) {
		List<Technology> technologies = projectReq.getTechnology().stream()
				.map(id -> technologyService.findByUserAndId(currentUser,id))
				.filter(Objects::nonNull)
				.toList();

		newProject.setTechnology(technologies);
	} else {
		newProject.setTechnology(null);
	}
	projectService.save(newProject);
	return new ResponseEntity<>(toRes(newProject), HttpStatus.CREATED);
}
@PutMapping("/{id}")
public ResponseEntity<ProjectRes> updateProject(@PathVariable long id, @RequestBody UpdateProjectReq updateProject) {
	User currentUser = securityUtils.getCurrentUser();
	Project project = projectService.findByUserAndId(currentUser,id);
	project.setName(updateProject.getName() != null ? updateProject.getName() : project.getName());
	project.setLinks(updateProject.getLinks() != null ? updateProject.getLinks() : project.getLinks());
	List<Technology> technologies = updateProject.getTechnology() != null
			? technologyService.findAllById(updateProject.getTechnology())
			: project.getTechnology();

	project.setTechnology(technologies);
	project.setStatus(updateProject.getStatus() != null ? updateProject.getStatus() : project.getStatus());
	project.setUpdatedAt(LocalDate.now());
	projectService.update(project);
	return new ResponseEntity<>(toRes(project), HttpStatus.OK);
}
@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteProject(@PathVariable long id) {
	User currentUser = securityUtils.getCurrentUser();
	Project project = projectService.findByUserAndId(currentUser,id);
	projectService.delete(project);
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}
@GetMapping("/status")
public List<String> getStatusJson() {
	return Arrays.stream(Status.values()).map(Enum::name).collect(Collectors.toList());
}
}
