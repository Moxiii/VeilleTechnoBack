package com.moxi.veilletechnoback.DTO.Ressources;


import com.moxi.veilletechnoback.Enum.Ressources.labelName;
import com.moxi.veilletechnoback.Technology.Technology;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class RessourcesReq {
private long id;
private long technologyId;
private labelName label;
private String url;
}
