package com.moxi.veilletechnoback.Technology;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.moxi.veilletechnoback.Project.Project;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.moxi.veilletechnoback.Ressources.Ressources;
import java.time.Duration;
import java.util.List;


@Getter
@Setter
@Entity
public class Technology {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String name;
private String category;
private Duration trainingTime;
@ManyToMany(mappedBy = "technology")
@JsonBackReference
private List<Project> projects;
@OneToMany(mappedBy = "technology", cascade = CascadeType.ALL)
private List<Ressources> resources;
}
