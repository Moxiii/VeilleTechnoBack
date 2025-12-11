package com.moxi.veilletechnoback.Project.Features;

import com.moxi.veilletechnoback.DTO.Features.FeaturesReq;
import com.moxi.veilletechnoback.DTO.Features.FeaturesRes;
import com.moxi.veilletechnoback.Enum.History.HistoryType;
import com.moxi.veilletechnoback.Project.Project;
import com.moxi.veilletechnoback.Project.ProjectService;
import com.moxi.veilletechnoback.Project.History.HistoryService;
import com.moxi.veilletechnoback.User.User;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeaturesService {

private final FeaturesRepository featuresRepository;
private final ProjectService projectService;
private final  HistoryService historyService;

public Features createFeature(User user,FeaturesReq req  ) {
	LocalDate today = LocalDate.now();
	Project project = projectService.findByUserAndId(user, req.getProjectId());
	Features feature = new Features();
	feature.setProject(project);
	feature.setName(req.getName());
	feature.setDescription(req.getDescription());
	feature.setCreatedAt(today);
	Features savedFeature = featuresRepository.save(feature);
	historyService.logFeatureChange(
  project,
        savedFeature,
        HistoryType.FEATURE_CREATED,
        "name",
        null,
        savedFeature.getName(),
        "Création de la feature : " + savedFeature.getName()
		);
	return savedFeature;
}
public List<FeaturesRes> getFeaturesByProject(Long projectId) {
	return featuresRepository.findAllByProjectId( projectId)
			.stream()
			.map(f -> new FeaturesRes(f.getId(), f.getName(),f.getDescription() , f.getCreatedAt()))
			.toList();
}
public void deleteFeature(Long id) {
	Features feature = featuresRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Feature not found"));
	featuresRepository.deleteById(id);
		historyService.logFeatureChange(
		feature.getProject(),
		feature,
		HistoryType.FEATURE_DELETED,
		null,
		null,
		feature.getName(),
		"Suppression de la feature : " + feature.getName()
		);
}

public Features updateFeatureByID(User currentUser, Long featureId, FeaturesReq req) {
	Features updatedFeature = featuresRepository.findById(featureId)
			.orElseThrow(() -> new RuntimeException("Feature not found"));
	Project project = projectService.findByUserAndId(currentUser, req.getProjectId());
	if (req.getName() != null && !req.getName().equals(updatedFeature.getName())) {
            historyService.logFeatureChange(
                    project,
                    updatedFeature,
                    HistoryType.FEATURE_UPDATED,
                    "name",
                    updatedFeature.getName(),
                    req.getName(),
                    "Modification du nom de la feature"
            );
            updatedFeature.setName(req.getName());
        }
 if (req.getDescription() != null && !req.getDescription().equals(updatedFeature.getDescription())) {
            historyService.logFeatureChange(
                    project,
                    updatedFeature,
                    HistoryType.FEATURE_UPDATED,
                    "description",
                    updatedFeature.getDescription(),
                    req.getDescription(),
                    "Modification de la description"
            );
            updatedFeature.setDescription(req.getDescription());
        }	updatedFeature.setProject(project != null ? project : updatedFeature.getProject());
	return featuresRepository.save(updatedFeature);
}

}
