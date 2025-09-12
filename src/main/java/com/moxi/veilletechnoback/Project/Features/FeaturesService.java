package com.moxi.veilletechnoback.Project.Features;

import com.moxi.veilletechnoback.DTO.Features.FeaturesReq;
import com.moxi.veilletechnoback.DTO.Features.FeaturesRes;
import com.moxi.veilletechnoback.Project.Project;
import com.moxi.veilletechnoback.Project.ProjectService;
import com.moxi.veilletechnoback.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class FeaturesService {
@Autowired
private FeaturesRepository featuresRepository;
@Autowired
private
ProjectService projectService;

public Features createFeature(User user,FeaturesReq req  ) {
	LocalDate today = LocalDate.now();
	Project project = projectService.findByUserAndId(user, req.getProjectId());
	Features feature = new Features();
	feature.setProject(project);
	feature.setName(req.getName());
	feature.setDescription(req.getDescription());
	feature.setCreatedAt(today);
	feature.setStartDate(req.getStartDate());
	feature.setEndDate(req.getEndDate());
	return featuresRepository.save(feature);
}
public List<FeaturesRes> getFeaturesByProject(Long projectId) {
	return featuresRepository.findAllByProjectId( projectId)
			.stream()
			.map(f -> new FeaturesRes(f.getId(), f.getName(),f.getDescription(), f.getStartDate(), f.getEndDate()))
			.toList();
}
public void deleteFeature(Long id) {
	featuresRepository.deleteById(id);
}

public Features updateFeatureByID(User currentUser, Long featureId, FeaturesReq req) {
	Features updatedFeature = featuresRepository.findById(featureId)
			.orElseThrow(() -> new RuntimeException("Feature not found"));
	Project project = projectService.findByUserAndId(currentUser, req.getProjectId());
	updatedFeature.setName(req.getName() != null ? req.getName() : updatedFeature.getName());
	updatedFeature.setDescription(req.getDescription() != null ? req.getDescription() : updatedFeature.getDescription());
	updatedFeature.setStartDate(req.getStartDate() != null ? req.getStartDate() : updatedFeature.getStartDate());
	updatedFeature.setEndDate(req.getEndDate() != null ? req.getEndDate() : updatedFeature.getEndDate());
	updatedFeature.setProject(project != null ? project : updatedFeature.getProject());
	return featuresRepository.save(updatedFeature);
}

}
