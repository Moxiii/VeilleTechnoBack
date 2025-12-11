package com.moxi.veilletechnoback.DTO.Features;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FeaturesRes {
private Long id;
private String name;
private String description;
private LocalDate createdAt;
}
