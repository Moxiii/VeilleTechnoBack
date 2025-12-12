package com.moxi.veilletechnoback.Technology;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.moxi.veilletechnoback.Category.Category;
import com.moxi.veilletechnoback.Category.SubCat.SubCategory;
import com.moxi.veilletechnoback.Project.Project;
import com.moxi.veilletechnoback.User.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.moxi.veilletechnoback.Ressources.Ressources;
import com.moxi.veilletechnoback.Technology.Concepts.Concepts;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id"
)
public class Technology {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
@ManyToOne
private User user;
private String name;
private LocalDate createdAt;
private Duration trainingTime = Duration.ZERO;
@ManyToMany(mappedBy = "technology")
private List<Project> projects;
@OneToMany(mappedBy = "technology", cascade = CascadeType.ALL)
private List<Ressources> resources;
@ManyToOne
private Category category;
@ManyToMany
private List<SubCategory> subCategory = new ArrayList<>();
@ManyToMany
@JoinTable(
		name="technology_links",
		joinColumns = @JoinColumn(name="technology_id"),
		inverseJoinColumns =  @JoinColumn(name="linked_technology_id")
)
private List<Technology> linkedTechnologies = new ArrayList<>();
@ManyToOne
@JoinColumn(name = "parent_id")
private Technology parent;
@OneToMany(mappedBy = "parent")
private List<Technology> subTechnologies = new ArrayList<>();
@ElementCollection
@CollectionTable(name = "technology_concepts", joinColumns = @JoinColumn(name = "technology_id"))
@Column(name = "concept")
private List<Concepts> concepts = new ArrayList<>();	
}
