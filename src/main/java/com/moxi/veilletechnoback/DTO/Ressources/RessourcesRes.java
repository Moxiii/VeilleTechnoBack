package com.moxi.veilletechnoback.DTO.Ressources;


import com.moxi.veilletechnoback.DTO.Technology.BasicTechnologyRes;
import com.moxi.veilletechnoback.Enum.Ressources.labelName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RessourcesRes {
private long id;
private BasicTechnologyRes technology;
private labelName label;
private String url;
}
