package com.moxi.veilletechnoback.Category.SubCat;

import com.moxi.veilletechnoback.Config.Security.SecurityUtils;
import com.moxi.veilletechnoback.DTO.Category.sub.SubCategoryReq;
import com.moxi.veilletechnoback.User.User;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/subcategory")
public class SubCategoryController {
private final SubCategoryService subCategoryService;
private final SecurityUtils securityUtils;

@GetMapping
public List<SubCategory> getAllCategories() {
	User currentUser = securityUtils.getCurrentUser();
	return  subCategoryService.findByUser(currentUser);
}
@PostMapping
public ResponseEntity<?> addCategory(@RequestBody SubCategoryReq category) {
	User currentUser = securityUtils.getCurrentUser();
	subCategoryService.create(category.getName() , category.getCategoryId() , currentUser);
	return ResponseEntity.status(HttpStatus.CREATED).build();
}
@DeleteMapping("/{id}")
public ResponseEntity<?> deleteCategory(@PathVariable long id)  {
	User currentUser = securityUtils.getCurrentUser();
	subCategoryService.deleteSub(id , currentUser);
	return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
}
}
