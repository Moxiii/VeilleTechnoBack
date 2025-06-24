package com.moxi.veilletechnoback.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {
@Autowired
private UserRepository userRepository;


public Optional<User> findById(String id){
	return userRepository.findById(id);
}

public User saveUser(User user) {
	return userRepository.save(user);
}

@Transactional
public void createIfAbsent(String kcId, String username) {
	if (userRepository.findById(kcId).isEmpty()) {
		User u = new User(kcId, username);
		Date aujourdhui = new Date();
		SimpleDateFormat formatedDate = new SimpleDateFormat("dd-MM-yyyy");
		String dateString = formatedDate.format(aujourdhui);
		u.setDateInscription(dateString);
		userRepository.save(u);
	}
}
}
