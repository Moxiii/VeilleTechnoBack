package com.moxi.veilletechnoback.DTO.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Getter
@Setter
public class CategoryRes {
private Long id;
private String name;
private String type;
private boolean defaultCategory;
}
