package com.moxi.veilletechnoback.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
@Autowired
private UserRepository userRepository;

public User findByUsername(String username) {
	return userRepository.findByUsername(username);
}
public User findById(long id){
	return userRepository.findById(id);
}

public User saveUser(User user) {
	return userRepository.save(user);
}


}
