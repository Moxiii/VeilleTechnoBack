package com.moxi.veilletechnoback.Category.SubCat;

import com.moxi.veilletechnoback.Config.Security.SecurityUtils;
import com.moxi.veilletechnoback.DTO.Category.sub.SubCategoryReq;
import com.moxi.veilletechnoback.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/subcategory")
public class SubCategoryController {
@Autowired
private SubCategoryService subCategoryService;


@GetMapping
public List<SubCategory> getAllCategories() {
	User currentUser = SecurityUtils.getCurrentUser();
	return  subCategoryService.findByUser(currentUser);
}
@PostMapping
public ResponseEntity<?> addCategory(@RequestBody SubCategoryReq category) {
	User currentUser = SecurityUtils.getCurrentUser();
	subCategoryService.create(category.getName() , category.getCategoryId() , currentUser);
	return ResponseEntity.status(HttpStatus.CREATED).build();
}
@DeleteMapping("/{id}")
public ResponseEntity<?> deleteCategory(@PathVariable long id)  {
	User currentUser = SecurityUtils.getCurrentUser();
	subCategoryService.deleteSub(id , currentUser);
	return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
}
}
