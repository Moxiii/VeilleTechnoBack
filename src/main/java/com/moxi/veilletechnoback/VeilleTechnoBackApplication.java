package com.moxi.veilletechnoback;

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
public CommandLineRunner init(UserRepository userRepository, TechnologyRepository technologyRepository, ProjectRepository projectRepository,  RessourcesRepository ressourcesRepository) {
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
			portfolio.setStatus(Status.onGoing);
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
				tech.setUser(moxi);
				tech.setProjects(List.of(portfolio));
				return tech;
			}).toList();
			technologyRepository.saveAll(technologyList);
			portfolio.setTechnology(technologyList);
			projectRepository.save(portfolio);
			System.out.println("Technologies initialisées.");

		}
		if(ressourcesRepository.count()==0){
			Ressources gsap = new Ressources();
			gsap.setTechnology(technologyRepository.findByUserAndId(moxi , 1));
			gsap.setLabel(labelName.Docs);
			gsap.setUrl("https://github.com/moxi/veilletechnoback");
			gsap.setUser(moxi);
			ressourcesRepository.save(gsap);
		}
	};
}
}
