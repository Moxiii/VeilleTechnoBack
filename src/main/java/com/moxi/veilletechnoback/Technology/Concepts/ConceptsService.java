package com.moxi.veilletechnoback.Technology.Concepts;

import java.util.List;

import org.springframework.stereotype.Service;

import com.moxi.veilletechnoback.Config.Security.SecurityUtils;
import com.moxi.veilletechnoback.DTO.Concepts.ConceptsReq;
import com.moxi.veilletechnoback.Project.Project;
import com.moxi.veilletechnoback.Project.ProjectRepository;

import com.moxi.veilletechnoback.User.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConceptsService {
    private final ConceptsRepository conceptsRepository;
    private final ProjectRepository projectRepository;
    private final SecurityUtils securityUtils;
    private User getCurrentUser() {
        return securityUtils.getCurrentUser();
    } ;

    public Concepts create(ConceptsReq req) {
        User currentUser = getCurrentUser();
        Concepts c = new Concepts();
        c.setName(req.getName());
        c.setDescription(req.getDescription());
        if(req.getProjectIds() != null && !req.getProjectIds().isEmpty()) {
                    List<Project> projects = req.getProjectIds().stream()
                    .map(id -> projectRepository.findByUserAndId(currentUser, id)
                    .orElseThrow(() -> new IllegalArgumentException("Project not found: " + id)))
                    .toList();

        c.setProjects(projects);
                }
        return conceptsRepository.save(c);
    }
    public Concepts update(Long id, ConceptsReq req) {
        User currentUser = getCurrentUser();
        Concepts c = conceptsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Concept not found"));

        c.setName(req.getName());
        c.setDescription(req.getDescription());
            List<Project> projects = req.getProjectIds().stream()
            .map(projectId -> projectRepository.findByUserAndId(currentUser, projectId)
                    .orElseThrow(() -> new IllegalArgumentException("Project not found: " + projectId)))
            .toList();
        c.setProjects(projects);

        return conceptsRepository.save(c);
    }
    public void delete(Long id) {
        conceptsRepository.deleteById(id);
    }

    public List<Concepts> findAll() {
        return conceptsRepository.findAll();
    }

    public Concepts findById(Long id) {
        return conceptsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Concept not found"));
    }
}
