package com.moxi.veilletechnoback;

import com.moxi.veilletechnoback.Project.Project;
import com.moxi.veilletechnoback.Project.ProjectRepository;
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

public VeilleTechnoBackApplication(UserRepository userRepository, ProjectRepository projectRepository, TechnologyRepository technologyRepository) {
	this.userRepository = userRepository;
	this.projectRepository = projectRepository;
	this.technologyRepository = technologyRepository;
}

public static void main(String[] args) {
	SpringApplication.run(VeilleTechnoBackApplication.class, args);
}
private final UserRepository userRepository;
private final ProjectRepository projectRepository;
private final TechnologyRepository technologyRepository;
@Bean
public CommandLineRunner init(ProjectRepository projectRepository) {
	return args -> {
		User moxi = new User("moxi", "moxi@moxi.com","ee");
		if (userRepository.count()==0){
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			String formattedDate = dateFormat.format(new Date());
			moxi.setDateInscription(formattedDate);
			userRepository.save(moxi);
		}
		if(projectRepository.count()==0){
			LocalDate now = LocalDate.now();
			List<String> links = List.of("x.fr");
			Project portfolio = new Project();
			portfolio.setName("Portfolio");
			portfolio.setStatus("en cours");
			portfolio.setLinks(links);
			portfolio.setUser(moxi);
			portfolio.setStartDate(now);
			portfolio.setEndDate(now);
			projectRepository.save(portfolio);
		}
		if(technologyRepository.count()==0){
			Project portfolio = projectRepository.findByName("portfolio");
			List<String> techNames = List.of(
					"GSAP", "Framer Motion", "Lenis", "React", "Angular",
					"Java", "C#", "React Native", "Three.js"
			);
			List<Technology> technologyList = techNames.stream().map(name -> {
				Technology tech = new Technology();
				tech.setName(name);
				tech.setProjects(List.of(portfolio));
				return tech;
			}).toList();
			technologyRepository.saveAll(technologyList);
			portfolio.setTechnology(technologyList);
			projectRepository.save(portfolio);
			System.out.println("Technologies initialisées.");

		}

	};
}
}
