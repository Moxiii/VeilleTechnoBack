package com.moxi.veilletechnoback.Controller;

import com.moxi.veilletechnoback.Config.JWT.Annotation.RequireAuthorization;
import com.moxi.veilletechnoback.Config.Security.SecurityUtils;
import com.moxi.veilletechnoback.User.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.moxi.veilletechnoback.DTO.User.PROFILE.UserProfileRes;
@RequireAuthorization
@RestController
@RequestMapping("/user")
public class UserController {
@GetMapping
public ResponseEntity<UserProfileRes> getUserProfile() {
	User currentUser = SecurityUtils.getCurrentUser();
	UserProfileRes userProfileRes = new UserProfileRes();
	if (currentUser != null) {
		userProfileRes.setUsername(currentUser.getUsername());
		return ResponseEntity.ok(userProfileRes);
	}
	return new ResponseEntity<>(HttpStatus.NOT_FOUND);

}
}
