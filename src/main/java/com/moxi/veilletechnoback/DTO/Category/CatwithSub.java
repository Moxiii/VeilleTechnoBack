package com.moxi.veilletechnoback.DTO.Category;


import com.moxi.veilletechnoback.Category.CategoryEnum;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CatwithSub {

private CategoryEnum type;
private boolean defaultCategory;
private String name;
}
