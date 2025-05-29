package com.moxi.veilletechnoback.Controller;

import com.moxi.veilletechnoback.Config.Security.SecurityUtils;
import com.moxi.veilletechnoback.User.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.moxi.veilletechnoback.DTO.User.PROFILE.UserProfileRes;
@RestController
@RequestMapping("/user")
public class UserController {
@GetMapping
public ResponseEntity<UserProfileRes> getUserProfile() {
	User currentUser = SecurityUtils.getCurrentUser();
	UserProfileRes userProfileRes = new UserProfileRes();
	userProfileRes.setUsername(currentUser.getUsername());
	return ResponseEntity.ok(userProfileRes);
}
}
