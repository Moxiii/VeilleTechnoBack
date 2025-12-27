package com.moxi.veilletechnoback.Technology.Concepts;

import java.util.ArrayList;
import java.util.List;

import com.moxi.veilletechnoback.Category.Category;
import com.moxi.veilletechnoback.Project.Project;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

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
@ManyToMany(mappedBy = "concepts")
private List<Project> projects = new ArrayList<>();
@ManyToOne
@JoinColumn(name = "category_id")
private Category category;

}
