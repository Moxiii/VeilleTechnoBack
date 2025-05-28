package com.moxi.veilletechnoback;

import com.moxi.veilletechnoback.User.User;
import com.moxi.veilletechnoback.User.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class VeilleTechnoBackApplication {

public VeilleTechnoBackApplication(UserRepository userRepository) {
	this.userRepository = userRepository;
}

public static void main(String[] args) {
	SpringApplication.run(VeilleTechnoBackApplication.class, args);
}
private final UserRepository userRepository;
@Bean
public CommandLineRunner init() {
	return args -> {
		if (userRepository.count()==0){
			User moxi = new User(" moxi", "moxi@moxi.com","ee");
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			String formattedDate = dateFormat.format(new Date());
			moxi.setDateInscription(formattedDate);
			userRepository.save(moxi);
		}
	};
}
}
