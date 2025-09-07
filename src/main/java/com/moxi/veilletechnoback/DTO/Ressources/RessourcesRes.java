package com.moxi.veilletechnoback.DTO.Ressources;


import com.moxi.veilletechnoback.Category.CategoryEnum;
import com.moxi.veilletechnoback.DTO.Technology.BasicTechnologyRes;
import com.moxi.veilletechnoback.Enum.Ressources.labelName;
import jakarta.persistence.ElementCollection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
public class RessourcesRes {
private long id;
private String name;
private BasicTechnologyRes technology;
private LocalDate createdAt;
private labelName label;
private String url;
private String description;
@ElementCollection
private Set<String> tags = new HashSet<>();
private LocalDate updatedAt;
private Long categoryId;
private CategoryEnum type;
}
