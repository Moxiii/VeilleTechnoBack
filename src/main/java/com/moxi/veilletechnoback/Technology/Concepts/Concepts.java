package com.moxi.veilletechnoback.Technology.Concepts;

import java.util.ArrayList;
import java.util.List;

import com.moxi.veilletechnoback.Enum.PDF.SkillCategory;
import com.moxi.veilletechnoback.Project.Project;
import com.moxi.veilletechnoback.Technology.Technology;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Concepts {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String name;
private String description;
@ManyToOne Technology technology;
@ManyToMany 
  @JoinTable(
        name = "concept_project",
        joinColumns = @JoinColumn(name = "concept_id"),
        inverseJoinColumns = @JoinColumn(name = "project_id")
    )
private List<Project> projects = new ArrayList<>();
@Enumerated(EnumType.STRING)
private SkillCategory skillCategory;
}
