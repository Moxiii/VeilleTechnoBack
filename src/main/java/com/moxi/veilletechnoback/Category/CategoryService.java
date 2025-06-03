package com.moxi.veilletechnoback.Category;
import com.moxi.veilletechnoback.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
@Autowired
private CategoryRepository categoryRepository;
public List<Category> findByUser(User user){
	return categoryRepository.findByUser(user);
}

public void createCustomCategory(User user, String name, CategoryEnum type) {
	Category category = new Category();
	category.setUser(user);
	category.setName(name);
	category.setType(type);
	categoryRepository.save(category);
}

public void deleteCategoryById(long id, User user)  {
	Category category = categoryRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Not found"));
	if (!category.getUser().equals(user)) {
		throw new AccessDeniedException("Not your category");
	}
	categoryRepository.delete(category);
}
}

