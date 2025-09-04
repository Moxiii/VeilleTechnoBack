package com.moxi.veilletechnoback.Category.SubCat;

import com.moxi.veilletechnoback.Category.Category;
import com.moxi.veilletechnoback.Category.CategoryEnum;
import com.moxi.veilletechnoback.Category.CategoryRepository;
import com.moxi.veilletechnoback.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubCategoryService {
@Autowired
private SubCategoryRepository subCategoryRepository;
@Autowired
private CategoryRepository categoryRepository;
public List<SubCategory> findByUser(User user){
	return subCategoryRepository.findByUser(user);
}

public void create( String name, long categoryId , User user) {
	SubCategory subCategory = new SubCategory();
	subCategory.setUser(user);
	subCategory.setName(name);
	Category linkedCat = categoryRepository.findById(categoryId).orElseThrow();
	subCategory.setCategory(linkedCat);
	subCategoryRepository.save(subCategory);
}

public void deleteSub(long id, User user)  {
	SubCategory subCategory = subCategoryRepository.findById(id).orElseThrow();
	if (!subCategory.getUser().equals(user)) {
		throw new AccessDeniedException("Not your category");
	}
	subCategoryRepository.delete(subCategory);
}


public SubCategory findById(Long subCategoryId) {
	return subCategoryRepository.findById(subCategoryId).orElseThrow();
}

public List<SubCategory> findByCategoryEnum(CategoryEnum category) {
	return subCategoryRepository.findByCategory_Type(category);
}

public List<SubCategory> findByCategory(Category category) {
	return subCategoryRepository.findByCategory(category);
}
}

