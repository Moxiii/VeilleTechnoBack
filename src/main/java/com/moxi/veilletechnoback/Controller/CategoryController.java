package com.moxi.veilletechnoback.Controller;

import com.moxi.veilletechnoback.Category.CategoryEnum;
import com.moxi.veilletechnoback.Category.CategoryService;
import com.moxi.veilletechnoback.Config.Security.SecurityUtils;
import com.moxi.veilletechnoback.DTO.Category.CategoryReq;
import com.moxi.veilletechnoback.DTO.Category.CategoryRes;
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
@RequestMapping("/category")
public class CategoryController {
@Autowired
private CategoryService categoryService;
@Autowired
private SecurityUtils securityUtils;

@GetMapping
public ResponseEntity<Map<String,Object>> getAllCategories() {
	User currentUser = securityUtils.getCurrentUser();
	List<String> defaultCategory = Arrays.stream(CategoryEnum.values()).map(Enum::name).toList();
	List<CategoryRes> customCategory = categoryService.findByUser(currentUser).stream()
			.map(c -> new CategoryRes(c.getName(), c.getType().toString()))
			.toList();
	Map<String,Object> response = new HashMap<>();
	response.put("default", defaultCategory);
	response.put("custom", customCategory);
	return ResponseEntity.ok(response);
}
@PostMapping
public ResponseEntity<?> addCategory(@RequestBody CategoryReq category) {
	User currentUser = securityUtils.getCurrentUser();
	categoryService.createCustomCategory(currentUser , category.getName() , category.getType());
	return ResponseEntity.status(HttpStatus.CREATED).build();
}
@DeleteMapping("/{id}")
public ResponseEntity<?> deleteCategory(@PathVariable long id)  {
	User currentUser = securityUtils.getCurrentUser();
	categoryService.deleteCategoryById(id , currentUser);
	return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
}
@GetMapping("/name")
public List<String> getAllCategoriesName(){
	return Arrays.stream(CategoryEnum.values()).map(Enum::name).toList();
}
}
