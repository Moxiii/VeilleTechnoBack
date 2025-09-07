package com.moxi.veilletechnoback.Category;
import com.moxi.veilletechnoback.User.User;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
@Autowired
private CategoryRepository categoryRepository;
public List<Category> findByUser(User user){
	return categoryRepository.findByUser(user);
}

public Category createCustomCategory( String name, CategoryEnum type , User user) {
	Category category = new Category();
	category.setUser(user);
	category.setName(name);
	category.setType(type);
	category.setDefaultCategory(false);
	return categoryRepository.save(category);

}

public void deleteCategoryById(long id, User user)  {
	Category category = categoryRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Not found"));
	if (!category.getUser().equals(user)) {
		throw new AccessDeniedException("Not your category");
	}
	categoryRepository.delete(category);
}

public Category findById(Long categoryId) {
	Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Not found"));;
	return category;
}
public List<Category> findAll(User user) {
	for (CategoryEnum type : CategoryEnum.values()) {
		if(!categoryRepository.existsByTypeAndDefaultCategory(type,true)){
			Category defaultCategory = new Category();
			defaultCategory.setDefaultCategory(true);
			defaultCategory.setType(type);
			defaultCategory.setUser(null);
			categoryRepository.save(defaultCategory);
		}
	}
	List<Category> defaults = categoryRepository.findByDefaultCategory(true);
	List<Category> customs = categoryRepository.findByUser(user);
	List<Category> categories = new ArrayList<>();
	categories.addAll(defaults);
	categories.addAll(customs);
	return categories;
}
}

