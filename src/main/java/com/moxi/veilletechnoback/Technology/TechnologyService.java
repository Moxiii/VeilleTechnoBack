package com.moxi.veilletechnoback.Technology;

import com.moxi.veilletechnoback.Category.Category;
import com.moxi.veilletechnoback.Category.CategoryService;
import com.moxi.veilletechnoback.Category.SubCat.SubCategory;
import com.moxi.veilletechnoback.Category.SubCat.SubCategoryService;
import com.moxi.veilletechnoback.DTO.Technology.TechnologyReq;
import com.moxi.veilletechnoback.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.List;

@Service
public class TechnologyService {
@Autowired
private TechnologyRepository technologyRepository;
@Autowired
private CategoryService categoryService;
@Autowired
private SubCategoryService subCategoryService;

public List<Technology> findAll() {
	return technologyRepository.findAll();
}

public Technology findByUserAndId(User user , long id) {
	return technologyRepository.findByUserAndId(user,id);
}

public Technology create(TechnologyReq req, User user) {
	Technology technology = new Technology();
	technology.setName(req.getName());
	technology.setUser(user);
	Category category = null;
	if (req.getCategoryId() != null) {
		category = categoryService.findById(req.getCategoryId());
	} else if (req.getCustomCategoryName() != null && req.getCustomCategoryType() != null ) {
		category = categoryService.createCustomCategory(req.getCustomCategoryName(), req.getCustomCategoryType(), user);
	}
	technology.setCategory(category);
	technology.setCreatedAt(LocalDate.now());

	if (req.getSubCategoryIds() != null && !req.getSubCategoryIds().isEmpty()) {
		List<SubCategory> subCategories = req.getSubCategoryIds()
				.stream()
				.map(subCategoryService::findById)
				.toList();
		technology.setSubCategory(subCategories);
	}
	if (req.getLinkedTechnologyIds() != null) {
		List<Technology> linked = technologyRepository.findAllById(req.getLinkedTechnologyIds());
		technology.setLinkedTechnologies(linked);
	}

	return technologyRepository.save(technology);
}
public void update(Technology tech, String name, Category category, List<SubCategory> subCategories) {
	if (name != null) tech.setName(name);
	if (category != null) tech.setCategory(category);
	if (subCategories != null && !subCategories.isEmpty()) tech.setSubCategory(subCategories);

	technologyRepository.save(tech);
}
public void delete(Technology technology) {
	technologyRepository.delete(technology);
}

public List<Technology> findAllById(List<Long> id) {
	return technologyRepository.findAllById(id);
}
}
