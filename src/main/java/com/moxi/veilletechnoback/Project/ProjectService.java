package com.moxi.veilletechnoback.Project;

import com.moxi.veilletechnoback.User.User;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

private final ProjectRepository projectRepository;


public Project save(Project project) {
	return projectRepository.save(project);
}

public Project update( Project project) {
	return projectRepository.save(project);
}


public void delete(Project project) {
	projectRepository.delete(project);
}

  public Project findByUserAndId(User user, long id) {
        return projectRepository.findByUserAndId(user, id)
            .orElseThrow(() -> new RuntimeException("Project not found or not accessible"));
    }

public List<Project> findAllByUser(User user) {
	return projectRepository.findByUser(user);
}
}
