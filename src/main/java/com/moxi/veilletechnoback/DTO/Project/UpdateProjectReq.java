package com.moxi.veilletechnoback.DTO.Project;

import com.moxi.veilletechnoback.Technologies.Technologies;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateProjectReq {
private String name;
private List<String> links;
private List<Technologies> technology;
}
