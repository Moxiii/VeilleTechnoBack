package com.moxi.veilletechnoback.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
