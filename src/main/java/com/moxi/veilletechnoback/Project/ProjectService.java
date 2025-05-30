package com.moxi.veilletechnoback.Project;

import com.moxi.veilletechnoback.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProjectService {
@Autowired
private ProjectRepository projectRepository;

public List<Project> findByuser(User user) {
	return projectRepository.findByUser(user);
}

public Project save(Project project) {
	return projectRepository.save(project);
}

public Project update( Project project) {
	return projectRepository.save(project);
}


public void delete(Project project) {
	projectRepository.delete(project);
}

public Project findByUserAndId(User user , long id) {
	return projectRepository.findByUserAndId(user , id);
}


public List<Project> findAllByUser(User user) {
	return projectRepository.findByUser(user);
}
}
