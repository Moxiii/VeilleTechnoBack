package com.moxi.veilletechnoback.DTO.Category;


import com.moxi.veilletechnoback.Category.CategoryEnum;
import com.moxi.veilletechnoback.DTO.Category.sub.SubCategoryRes;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CatwithSub {
private CategoryEnum type;
private List<SubCategoryRes> subCategories;
@Setter
private boolean defaultCategory;
private String name;
}
