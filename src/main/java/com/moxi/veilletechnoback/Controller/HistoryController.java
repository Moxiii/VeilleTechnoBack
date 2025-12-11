package com.moxi.veilletechnoback.Controller;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moxi.veilletechnoback.Config.Security.SecurityUtils;
import com.moxi.veilletechnoback.DTO.History.HistoryRes;
import com.moxi.veilletechnoback.Project.Project;
import com.moxi.veilletechnoback.Project.ProjectService;
import com.moxi.veilletechnoback.Project.History.HistoryService;

import com.moxi.veilletechnoback.User.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/history")
public class HistoryController {

private final  HistoryService historyService;

private final SecurityUtils securityUtils;

private final ProjectService projectService;

@GetMapping("/get/{projectId}")
public ResponseEntity<List<HistoryRes>> getProjectHistory(@PathVariable Long projectId){
    User currentUser = securityUtils.getCurrentUser();
    Project project = projectService.findByUserAndId(currentUser, projectId);
    List<HistoryRes> history = historyService.getHistoryForProject(project);
    return new ResponseEntity<>(history , HttpStatus.OK);
}
}