package com.moxi.veilletechnoback.Technology;

import com.moxi.veilletechnoback.Category.Category;
import com.moxi.veilletechnoback.Category.CategoryService;

import com.moxi.veilletechnoback.DTO.Technology.TechnologyReq;
import com.moxi.veilletechnoback.User.User;

import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TechnologyService {

private final TechnologyRepository technologyRepository;

private final CategoryService categoryService;



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



	return technologyRepository.save(technology);
}
public void update(Technology tech, String name, Category category) {
	if (name != null) tech.setName(name);
	if (category != null) tech.setCategory(category);
	technologyRepository.save(tech);
}
public void delete(Technology technology) {
	technologyRepository.delete(technology);
}

public List<Technology> findAllById(List<Long> id) {
	return technologyRepository.findAllById(id);
}
public List<Technology> findByParentIsNullAndUser(User user) {
	return technologyRepository.findByParentIsNullAndUser(user);
}

}