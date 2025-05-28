package com.moxi.veilletechnoback.Controller.Project;

import com.moxi.veilletechnoback.Config.Security.SecurityUtils;
import com.moxi.veilletechnoback.DTO.Project.UpdateProjectReq;
import com.moxi.veilletechnoback.Project.Project;
import com.moxi.veilletechnoback.Project.ProjectRepository;
import com.moxi.veilletechnoback.Project.ProjectService;

import com.moxi.veilletechnoback.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.moxi.veilletechnoback.DTO.Project.ProjectRes;

import java.util.List;


@RestController
@RequestMapping("/project")
public class ProjectController {
@Autowired
private ProjectService projectService;
@Autowired
private ProjectRepository projectRepository;

private ProjectRes toRes(Project project) {
	ProjectRes res = new ProjectRes();
	res.setProjectName(project.getName());
	res.setStatus(project.getStatus());
	res.setTechnologies(project.getTechnologies());
	return res;
}
@GetMapping
public ResponseEntity<?> getProject() {
	User currentUser = SecurityUtils.getCurrentUser();
	List<ProjectRes> res = projectService.findAllByUser(currentUser)
			.stream()
			.map(this::toRes)
			.toList();
	return new ResponseEntity<>(res , HttpStatus.OK);
}
@GetMapping("/{id}")
public ResponseEntity<?> getProjectById(@PathVariable long id) {
	Project project = projectService.findById(id);
	return new ResponseEntity<>(toRes(project) , HttpStatus.OK);
}
@PostMapping
public ResponseEntity<?> createProject(@RequestBody Project project) {
	User currentuser = SecurityUtils.getCurrentUser();
	Project newProject = new Project();
	newProject.setName(project.getName());
	newProject.setStatus(project.getStatus());
	newProject.setTechnologies(project.getTechnologies());
	newProject.setUser(currentuser);
	projectService.save(newProject);
	return new ResponseEntity<>(toRes(newProject), HttpStatus.CREATED);
}
@PutMapping("/{id}")
public ResponseEntity<?> updateProject(@PathVariable long id, @RequestBody UpdateProjectReq updateProject) {
	Project project = projectRepository.findById(String.valueOf(id)).orElseThrow(() -> new RuntimeException("Project not found"));
	project.setName(updateProject.getName() != null ? updateProject.getName() : project.getName());
	project.setLinks(updateProject.getLinks() != null ? updateProject.getLinks() : project.getLinks());
	project.setTechnologies(updateProject.getTechnology() != null ? updateProject.getTechnology() : project.getTechnologies());
	projectService.save(project);
	return new ResponseEntity<>(toRes(project), HttpStatus.OK);
}
@DeleteMapping("/{id}")
public ResponseEntity<?> deleteProject(@PathVariable long id) {
	Project project = projectRepository.findById(String.valueOf(id)).orElseThrow(() -> new RuntimeException("Project not found"));
	projectService.delete(project);
	return new ResponseEntity<>(HttpStatus.OK);
}
}
