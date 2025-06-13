package com.moxi.veilletechnoback;

import com.moxi.veilletechnoback.Category.Category;
import com.moxi.veilletechnoback.Category.CategoryEnum;
import com.moxi.veilletechnoback.Category.CategoryRepository;
import com.moxi.veilletechnoback.Category.SubCat.SubCategory;
import com.moxi.veilletechnoback.Category.SubCat.SubCategoryRepository;
import com.moxi.veilletechnoback.Enum.Ressources.labelName;
import com.moxi.veilletechnoback.Enum.Status;
import com.moxi.veilletechnoback.Project.Project;
import com.moxi.veilletechnoback.Project.ProjectRepository;
import com.moxi.veilletechnoback.Ressources.Ressources;
import com.moxi.veilletechnoback.Ressources.RessourcesRepository;
import com.moxi.veilletechnoback.Technology.Technology;
import com.moxi.veilletechnoback.Technology.TechnologyRepository;
import com.moxi.veilletechnoback.User.User;
import com.moxi.veilletechnoback.User.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class VeilleTechnoBackApplication {


public static void main(String[] args) {
	SpringApplication.run(VeilleTechnoBackApplication.class, args);
}
@Bean
public CommandLineRunner init(UserRepository userRepository, TechnologyRepository technologyRepository, ProjectRepository projectRepository, RessourcesRepository ressourcesRepository, CategoryRepository categoryRepository, SubCategoryRepository subCategoryRepository
) {
	return args -> {
		LocalDate now = LocalDate.now();
		User moxi = new User("moxi", "moxi@moxi.com","ee");

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			String formattedDate = dateFormat.format(new Date());
			moxi.setDateInscription(formattedDate);
			userRepository.save(moxi);
			List<String> links = List.of("x.fr");
			Project portfolio = new Project();
			portfolio.setName("Portfolio");
			portfolio.setStatus(Status.onGoing);
			portfolio.setLinks(links);
			portfolio.setUser(moxi);
			portfolio.setCreateAt(now);
			portfolio.setStartDate(now);
			portfolio.setEndDate(now);
			projectRepository.save(portfolio);
			Category frontCategory = new Category();
			frontCategory.setType(CategoryEnum.front);
			frontCategory.setName("UX");
			frontCategory.setUser(moxi);
			categoryRepository.save(frontCategory);
			SubCategory animationSubCategory = new SubCategory();
			animationSubCategory.setName("Animation");
			animationSubCategory.setUser(moxi);
			animationSubCategory.setCategory(frontCategory);
			subCategoryRepository.save(animationSubCategory);
			List<String> techNames = List.of(
					"GSAP", "Framer Motion", "Lenis", "React", "Angular",
					"Java", "C#", "React Native", "Three.js"
			);
			List<Technology> technologyList = techNames.stream().map(name -> {
				Technology tech = new Technology();
				tech.setName(name);
				tech.setUser(moxi);
				tech.setProjects(List.of(portfolio));
				tech.setCreateAt(now);
				tech.setCategory(CategoryEnum.front);
				tech.setSubCategory(animationSubCategory);
				return tech;
			}).toList();
			technologyRepository.saveAll(technologyList);
			portfolio.setTechnology(technologyList);
			projectRepository.save(portfolio);
			System.out.println("Technologies initialisées.");
			Ressources gsap = new Ressources();
			Technology tech = technologyRepository.findByUserAndId(moxi , 1);
			gsap.setTechnology(tech);
			gsap.setLabel(labelName.Docs);
			gsap.setUrl("https://github.com/moxi/veilletechnoback");
			gsap.setUser(moxi);
			gsap.setCreateAt(now);
			ressourcesRepository.save(gsap);
			Ressources doc = new Ressources();
			doc.setTechnology(tech);
			doc.setLabel(labelName.Docs);
			doc.setUrl("https://github.com/moxi/veilletechnoback");
			doc.setUser(moxi);
			doc.setCreateAt(now);
			ressourcesRepository.save(doc);
			Ressources tutorial = new Ressources();
			tutorial.setTechnology(tech);
			tutorial.setLabel(labelName.Tutorial);
			tutorial.setUrl("https://github.com/moxi/veilletechnoback");
			tutorial.setUser(moxi);
			tutorial.setCreateAt(now);
			ressourcesRepository.save(tutorial);
			Ressources example = new Ressources();
			example.setTechnology(technologyRepository.findByUserAndId(moxi , 1));
			example.setUser(moxi);
			example.setUrl("https://github.com/moxi/veilletechnoback");
			example.setCreateAt(now);
			example.setLabel(labelName.Example);
			ressourcesRepository.save(example);
	};
}
}
