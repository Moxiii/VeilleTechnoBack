package com.moxi.veilletechnoback.Technology;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.moxi.veilletechnoback.Category.CategoryEnum;
import com.moxi.veilletechnoback.Category.SubCat.SubCategory;
import com.moxi.veilletechnoback.Project.Project;
import com.moxi.veilletechnoback.User.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.moxi.veilletechnoback.Ressources.Ressources;
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
private LocalDate createAt;
private Duration trainingTime = Duration.ZERO;
@ManyToMany(mappedBy = "technology")
private List<Project> projects;
@OneToMany(mappedBy = "technology", cascade = CascadeType.ALL)
private List<Ressources> resources;
private CategoryEnum category;
@ManyToMany
private List<SubCategory> subCategory = new ArrayList<>();
}
