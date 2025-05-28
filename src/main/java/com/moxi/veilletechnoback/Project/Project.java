package com.moxi.veilletechnoback.Project;
import com.moxi.veilletechnoback.Technologies.Technologies;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@Entity
public class Project {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
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
		name = "project_technologies",
		joinColumns = @JoinColumn(name = "project_id"),
		inverseJoinColumns = @JoinColumn(name = "technologies_id")
)
private List<Technologies> technologies;
}
