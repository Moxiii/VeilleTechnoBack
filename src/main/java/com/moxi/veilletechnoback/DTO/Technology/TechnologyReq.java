package com.moxi.veilletechnoback.DTO.Technology;


import com.moxi.veilletechnoback.Category.CategoryEnum;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TechnologyReq {
private Long id;
private String name;

private Long categoryId;
private String customCategoryName;
private CategoryEnum customCategoryType;

private List<Long> subCategoryIds;


}
