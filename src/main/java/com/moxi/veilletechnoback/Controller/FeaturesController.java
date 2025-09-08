package com.moxi.veilletechnoback.Controller;


import com.moxi.veilletechnoback.DTO.Features.FeaturesReq;
import com.moxi.veilletechnoback.DTO.Features.FeaturesRes;
import com.moxi.veilletechnoback.Project.Features.Features;
import com.moxi.veilletechnoback.Project.Features.FeaturesService;
import com.moxi.veilletechnoback.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.moxi.veilletechnoback.Config.Security.SecurityUtils;
import java.util.List;

@RestController
@RequestMapping("/features")
public class FeaturesController {
@Autowired
private FeaturesService featuresService;
@Autowired
private SecurityUtils securityUtils;

private FeaturesRes toRes (Features features) {
	FeaturesRes res = new FeaturesRes();
	res.setId(features.getId());
	res.setName(features.getName());
	res.setDescription(features.getDescription());
	if(features.getStartDate() != null) {
		res.setStartDate(features.getStartDate());
	}
	if(features.getEndDate() != null) {
		res.setEndDate(features.getEndDate());
	}
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
@PutMapping
public ResponseEntity<FeaturesRes> updateFeatures(@RequestBody FeaturesReq req) {
	User currentUser = securityUtils.getCurrentUser();
	Features feature = featuresService.updateFeatureByID(currentUser , req);
	return new ResponseEntity<>(toRes(feature), HttpStatus.OK);
}
@DeleteMapping
public ResponseEntity<?> deleteFeatures(@RequestBody FeaturesReq req) {
	featuresService.deleteFeature(req.getId());
	return new ResponseEntity<>(HttpStatus.OK);
}
}
