package com.moxi.veilletechnoback.DTO.Category;


import com.moxi.veilletechnoback.Category.CategoryEnum;
import com.moxi.veilletechnoback.DTO.Category.sub.SubCategoryRes;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CatwithSub {
private CategoryEnum defaultType;
private List<SubCategoryRes> subCategories;
private String customCategoryName;
private String customCategoryType;
}
