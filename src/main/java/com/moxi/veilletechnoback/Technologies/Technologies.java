package com.moxi.veilletechnoback.Technologies;

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
public class Technologies {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String name;
private String category;
private Duration trainingTime;
@ManyToMany(mappedBy = "technologies")
private List<Project> projects;
@OneToMany(mappedBy = "technologies", cascade = CascadeType.ALL)
private List<Ressources> resources;
}
