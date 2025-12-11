package com.moxi.veilletechnoback.DTO.Features;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@AllArgsConstructor
@Getter
@Setter
public class FeaturesReq {
private Long id ;
private String name ;
private String description;
private Long projectId;

}
