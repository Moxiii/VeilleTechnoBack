package com.moxi.veilletechnoback.DTO.Technology;

import com.moxi.veilletechnoback.Enum.techCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TechnologyRes {
private long id;
private String name;
private techCategory category;
}
