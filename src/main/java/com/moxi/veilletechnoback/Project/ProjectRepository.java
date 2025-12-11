package com.moxi.veilletechnoback.Project;

import com.moxi.veilletechnoback.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {
Project findByName(String name);

List<Project> findByUser(User user);
Optional<Project> findByUserAndId(User user , long id);
}
