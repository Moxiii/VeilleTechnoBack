package com.moxi.veilletechnoback.Project.History;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moxi.veilletechnoback.Config.Security.SecurityUtils;
import com.moxi.veilletechnoback.DTO.History.HistoryRes;
import com.moxi.veilletechnoback.Project.Project;
import com.moxi.veilletechnoback.Project.ProjectService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoryService {
@Autowired
private HistoryRepository historyRepository;
private final ProjectService projectService;
private final SecurityUtils securityUtils;

    private HistoryRes toRes(History history) {
        HistoryRes res = new HistoryRes();
        res.setId(history.getId());
        res.setAction(history.getAction());
        res.setStatus(history.getStatus());
        res.setStartDate(history.getStartDate());
        res.setEndDate(history.getEndDate());
        res.setTimestamp(history.getTimestamp());
        return res;
    }

public List<HistoryRes> getHistoryForProject(Project project){
    return historyRepository.findByProjectOrderByTimestampDesc(project).stream().map(this::toRes).toList();
}


}
