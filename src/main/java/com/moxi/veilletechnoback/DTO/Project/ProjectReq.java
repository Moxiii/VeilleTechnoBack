package com.moxi.veilletechnoback.DTO.Project;

import com.moxi.veilletechnoback.DTO.Technology.TechnologyRes;
import com.moxi.veilletechnoback.Enum.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ProjectReq {
private long id;
private String projectName;
private Status status;
private List<TechnologyRes> technology;
private LocalDate startDate;
private LocalDate endDate;
private List<String> links;
}
