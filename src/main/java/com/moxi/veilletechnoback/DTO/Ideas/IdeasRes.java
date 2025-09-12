package com.moxi.veilletechnoback.DTO.Ideas;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class IdeasRes {
private Long id;
private String title;
private String description;
private List<String> imageBase64;
private List<String> links;
private List<String> tags;
private List<Long> ressourcesIds;
}
