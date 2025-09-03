package com.moxi.veilletechnoback.Technology;

import com.moxi.veilletechnoback.Category.Category;
import com.moxi.veilletechnoback.Category.CategoryEnum;
import com.moxi.veilletechnoback.Category.SubCat.SubCategory;
import com.moxi.veilletechnoback.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class TechnologyService {
@Autowired
private TechnologyRepository technologyRepository;

public List<Technology> findAll() {
	return technologyRepository.findAll();
}

public Technology findByUserAndId(User user , long id) {
	return technologyRepository.findByUserAndId(user,id);
}

public Technology create(String name , CategoryEnum category, List<SubCategory> subCategories ,User user) {
	Technology technology = new Technology();
	technology.setName(name);
	technology.setUser(user);
	technology.setCreatedAt(LocalDate.now());
	technology.setCategory(category);
	technology.setSubCategory(subCategories);
	technologyRepository.save(technology);
	return technology;
}
public void update(Technology tech, String name, CategoryEnum category, List<SubCategory> subCategories) {
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
