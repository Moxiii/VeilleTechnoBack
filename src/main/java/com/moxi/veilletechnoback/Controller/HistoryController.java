package com.moxi.veilletechnoback.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/history")
public class HistoryController {
@Autowired
private HistoryService historyService;
@Autowired
private SecurityUtils securityUtils;
@Autowired
private ProjectService projectService;
private HistoryRes historyToRes(com.moxi.veilletechnoback.Project.History.History history){
    HistoryRes historyRes = new HistoryRes();
    historyRes.setId(history.getId());
    historyRes.setAction(history.getAction());
    historyRes.setTimestamp(history.getTimestamp());
    return historyRes;
}
@GetMapping("/getProjectHistory/{projectId}")
public ResponseEntity<List<HistoryRes>> getProjectHistory(@PathVariable Long projectId){
    User currentUser = securityUtils.getCurrentUser();
    Project project = projectService.findByUserAndId(currentUser, projectId);
    List<HistoryRes> history = historyService.getHistoryForProject(project);
    return new ResponseEntity<>(history , HttpStatus.OK);
}
}