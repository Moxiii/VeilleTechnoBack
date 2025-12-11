package com.moxi.veilletechnoback.Project.History;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

import com.moxi.veilletechnoback.DTO.History.HistoryRes;
import com.moxi.veilletechnoback.Enum.History.HistoryType;
import com.moxi.veilletechnoback.Project.Project;
import com.moxi.veilletechnoback.Project.Features.Features;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoryService {
private final HistoryRepository historyRepository;

    private HistoryRes toRes(History history) {
        HistoryRes res = new HistoryRes();
        res.setId(history.getId());
        res.setType(history.getType());
        res.setTimestamp(history.getTimestamp());
        res.setFieldName(history.getFieldName());
        res.setOldValue(history.getOldValue());
        res.setNewValue(history.getNewValue());
        res.setMessage(history.getMessage());
        res.setProjectId(history.getProject() != null ? history.getProject().getId() : null);
        res.setFeatureId(history.getFeature() != null ? history.getFeature().getId() : null);
        res.setAction(history.getAction());
        res.setTimestamp(history.getTimestamp());
        return res;
    }

public List<HistoryRes> getHistoryForProject(Project project){
    return historyRepository.findByProjectOrderByTimestampDesc(project).stream().map(this::toRes).toList();
}

public void logFeatureChange(Project project, Features feature, HistoryType type, String fieldName, String oldValue, String newValue, String message) {
    History history = new History();
    history.setProject(project);
    history.setFeature(feature);
    history.setType(type);
    history.setFieldName(fieldName);
    history.setOldValue(oldValue);
    history.setNewValue(newValue);
    history.setMessage(message);
    history.setTimestamp(LocalDate.now());
    history.setAction(type.name());

    historyRepository.save(history);
}
}