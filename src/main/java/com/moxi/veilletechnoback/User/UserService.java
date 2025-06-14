package com.moxi.veilletechnoback.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
@Autowired
private UserRepository userRepository;


public Optional<User> findById(String id){
	User existingUser = userRepository.findById(id).orElse(null);
	if(existingUser !=null){
		return Optional.of(existingUser);
	}
	else return null;
}

public User saveUser(User user) {
	return userRepository.save(user);
}


}
