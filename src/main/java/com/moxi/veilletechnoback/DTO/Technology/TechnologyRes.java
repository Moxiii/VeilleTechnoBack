package com.moxi.veilletechnoback.DTO.Technology;

import com.moxi.veilletechnoback.DTO.Project.BasicProjectRes;
import com.moxi.veilletechnoback.Enum.techCategory;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TechnologyRes {
private long id;
private String name;
private techCategory category;
private List<BasicProjectRes> projects = new ArrayList<>();
private Duration trainingTime;
}
