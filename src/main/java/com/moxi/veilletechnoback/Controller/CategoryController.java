package com.moxi.veilletechnoback.Controller;

import com.moxi.veilletechnoback.Category.Category;
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

import java.util.*;

@RestController
@RequestMapping("/category")
public class CategoryController {
@Autowired
private CategoryService categoryService;
@Autowired
private SecurityUtils securityUtils;

@GetMapping
public ResponseEntity<List<CategoryRes>> getAllCategories() {
	User currentUser = securityUtils.getCurrentUser();
	List<Category> categories = categoryService.findAll(currentUser);
	List<CategoryRes> response = categories.stream().map(
			c -> new CategoryRes(
					c.getId(),
					c.getName(),
					c.getType().toString(),
					c.isDefaultCategory()
			)
	).toList();
	return ResponseEntity.ok(response);
}
@PostMapping
public ResponseEntity<?> addCategory(@RequestBody CategoryReq category) {
	User currentUser = securityUtils.getCurrentUser();
	Category createdCat = categoryService.createCustomCategory( category.getName() , category.getType() , currentUser);
	CategoryRes categoryRes = new CategoryRes();
	categoryRes.setId(createdCat.getId());
	categoryRes.setName(createdCat.getName());
	categoryRes.setDefaultCategory(createdCat.isDefaultCategory());
	categoryRes.setType(String.valueOf(createdCat.getType()));
	return new ResponseEntity<>(categoryRes , HttpStatus.CREATED);
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
