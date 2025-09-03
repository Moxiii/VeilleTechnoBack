package com.moxi.veilletechnoback.DTO.Technology;

import com.moxi.veilletechnoback.Category.SubCat.SubCategory;
import com.moxi.veilletechnoback.DTO.Category.CatwithSub;
import com.moxi.veilletechnoback.DTO.Project.BasicProjectRes;
import com.moxi.veilletechnoback.Category.CategoryEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TechnologyRes {
private long id;
private String name;
private LocalDate createdAt;
private CatwithSub category;
private List<BasicProjectRes> projects = new ArrayList<>();
private Duration trainingTime;
}
