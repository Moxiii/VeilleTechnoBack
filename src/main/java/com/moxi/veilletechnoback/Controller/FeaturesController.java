package com.moxi.veilletechnoback.Controller;


import com.moxi.veilletechnoback.DTO.Features.FeaturesReq;
import com.moxi.veilletechnoback.DTO.Features.FeaturesRes;
import com.moxi.veilletechnoback.Project.Features.Features;
import com.moxi.veilletechnoback.Project.Features.FeaturesService;
import com.moxi.veilletechnoback.User.User;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.moxi.veilletechnoback.Config.Security.SecurityUtils;
import java.util.List;

@RestController
@RequestMapping("/features")
@RequiredArgsConstructor
public class FeaturesController {
private final FeaturesService featuresService;
private final SecurityUtils securityUtils;

private FeaturesRes toRes (Features features) {
	FeaturesRes res = new FeaturesRes();
	res.setId(features.getId());
	res.setName(features.getName());
	res.setDescription(features.getDescription());
	res.setCreatedAt(features.getCreatedAt());
	return res;
}

@GetMapping("project/{projectId}")
public List<FeaturesRes> getProjectFeatures(@PathVariable Long projectId) {
	return featuresService.getFeaturesByProject(projectId);
}
@PostMapping
public ResponseEntity<FeaturesRes> addFeatures(@RequestBody FeaturesReq req) {
	User currentUser = securityUtils.getCurrentUser();
	Features feature = featuresService.createFeature(currentUser , req);
	return new ResponseEntity<>(toRes(feature), HttpStatus.CREATED);
}
@PutMapping("/{featureId}")
public ResponseEntity<FeaturesRes> updateFeatures(@PathVariable Long featureId , @RequestBody FeaturesReq req) {
	User currentUser = securityUtils.getCurrentUser();
	Features feature = featuresService.updateFeatureByID(currentUser , featureId , req);
	return new ResponseEntity<>(toRes(feature), HttpStatus.OK);
}
@DeleteMapping("/{featureId}")
public ResponseEntity<?> deleteFeatures(@PathVariable Long featureId) {
	featuresService.deleteFeature(featureId);
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}
}
