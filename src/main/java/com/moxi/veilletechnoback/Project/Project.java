package com.moxi.veilletechnoback.Project;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.moxi.veilletechnoback.Technology.Technology;
import com.moxi.veilletechnoback.User.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@Entity
public class Project {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
@ManyToOne
private User user;
private String name;
private LocalDate startDate;
private LocalDate endDate;
private String status;
@ElementCollection
@CollectionTable(name = "project_links", joinColumns = @JoinColumn(name = "project_id"))
@Column(name = "link")
private List<String>  links;
@ManyToMany
@JoinTable(
		name = "project_technology",
		joinColumns = @JoinColumn(name = "project_id"),
		inverseJoinColumns = @JoinColumn(name = "technology_id")
)
@JsonManagedReference
private List<Technology> technology;
}
