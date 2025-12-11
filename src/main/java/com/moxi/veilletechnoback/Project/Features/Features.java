package com.moxi.veilletechnoback.Project.Features;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.moxi.veilletechnoback.Project.Project;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Features {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String name;
private String description;
private LocalDate createdAt = LocalDate.now();

@ManyToOne
@JoinColumn(name = "project_id")
@JsonBackReference
private Project project;
}
