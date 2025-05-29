package com.moxi.veilletechnoback.DTO.Project;

import com.moxi.veilletechnoback.Technology.Technology;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ProjectRes {
private String projectName;
private String status;
private List<Technology> technology;
private LocalDate startDate;
private LocalDate endDate;
private List<String> links;
}
