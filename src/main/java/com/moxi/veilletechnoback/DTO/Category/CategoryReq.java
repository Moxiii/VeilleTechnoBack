package com.moxi.veilletechnoback.DTO.Category;

import com.moxi.veilletechnoback.Category.CategoryEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryReq {
private String name;
private CategoryEnum type;
}
