package com.moxi.veilletechnoback.DTO.Ressources;


import com.moxi.veilletechnoback.DTO.Technology.BasicTechnologyRes;
import com.moxi.veilletechnoback.Enum.Ressources.labelName;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class RessourcesRes {
private BasicTechnologyRes technology;
private LocalDate createAt;
private labelName label;
private String url;
private String description;
}
