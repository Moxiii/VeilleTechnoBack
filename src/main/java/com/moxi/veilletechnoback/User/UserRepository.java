package com.moxi.veilletechnoback.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
User findByUsername(String username);
User findById(long id);
}
