package com.moxi.veilletechnoback.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moxi.veilletechnoback.DTO.Concepts.ConceptsReq;
import com.moxi.veilletechnoback.DTO.Concepts.ConceptsRes;
import com.moxi.veilletechnoback.Project.Project;
import com.moxi.veilletechnoback.Technology.Concepts.Concepts;
import com.moxi.veilletechnoback.Technology.Concepts.ConceptsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/concepts")
@RequiredArgsConstructor
public class ConceptsController {
    private final ConceptsService conceptsService;
       private ConceptsRes toRes(Concepts c) {
        ConceptsRes res = new ConceptsRes();
        res.setId(c.getId());
        res.setName(c.getName());
        res.setDescription(c.getDescription());
        res.setTechnologyId(c.getTechnology().getId());
        res.setProjectIds(c.getProjects().stream().map(Project::getId).toList());
        return res;
    }
@PostMapping
    public ConceptsRes create(@RequestBody ConceptsReq req) {
        return toRes(conceptsService.create(req));
    }

    @PutMapping("/{id}")
    public ConceptsRes update(@PathVariable Long id, @RequestBody ConceptsReq req) {
        return toRes(conceptsService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        conceptsService.delete(id);
    }

    @GetMapping
    public List<ConceptsRes> findAll() {
        return conceptsService.findAll().stream().map(this::toRes).toList();
    }

    @GetMapping("/{id}")
    public ConceptsRes findById(@PathVariable Long id) {
        return toRes(conceptsService.findById(id));
    }
}
