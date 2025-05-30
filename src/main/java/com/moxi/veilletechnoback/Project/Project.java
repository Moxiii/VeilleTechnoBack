package com.moxi.veilletechnoback.Project;
import com.fasterxml.jackson.annotation.*;
import com.moxi.veilletechnoback.Enum.Status;
import com.moxi.veilletechnoback.Technology.Technology;
import com.moxi.veilletechnoback.User.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
public class Project {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
@ManyToOne
@JsonBackReference
private User user;
private String name;
private LocalDate startDate;
private LocalDate endDate;
private Status status;
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
private List<Technology> technology = new ArrayList<>();
}
