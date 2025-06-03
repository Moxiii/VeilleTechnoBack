package com.moxi.veilletechnoback.DTO.Technology;

import com.moxi.veilletechnoback.Category.CategoryEnum;
import com.moxi.veilletechnoback.Category.SubCat.SubCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TechnologyReq {
private long id;
private String name;
private CategoryEnum category;
private Long subCategoryId;
}
