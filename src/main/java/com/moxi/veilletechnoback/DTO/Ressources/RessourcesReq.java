package com.moxi.veilletechnoback.DTO.Ressources;


import com.moxi.veilletechnoback.Enum.Ressources.labelName;
import com.moxi.veilletechnoback.Technology.Technology;
import jakarta.persistence.ElementCollection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
public class RessourcesReq {
private long id;
private long technologyId;
private String name;
private labelName label;
private String url;
private String description;
@ElementCollection
private Set<String> tags = new HashSet<>();
private String type;
private LocalDate updatedAt;
private Long categoryId;
}
