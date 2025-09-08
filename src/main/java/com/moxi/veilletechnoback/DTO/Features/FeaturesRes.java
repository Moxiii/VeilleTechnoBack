package com.moxi.veilletechnoback.DTO.Features;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FeaturesRes {
private Long id;
private String name;
private String description;
private LocalDate startDate;
private LocalDate endDate;
}
