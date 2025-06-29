package com.moxi.veilletechnoback.DTO.Technology;

import com.moxi.veilletechnoback.Category.CategoryEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TechnologyReq {
private long id;
private String name;
private CategoryEnum category;
private List<Long> subCategoryId;
}
