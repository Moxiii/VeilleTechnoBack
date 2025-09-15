package com.moxi.veilletechnoback.DTO.Project;

import com.moxi.veilletechnoback.DTO.Technology.BasicTechnologyRes;
import com.moxi.veilletechnoback.DTO.Technology.TechnologyRes;
import com.moxi.veilletechnoback.Enum.Status;
import com.moxi.veilletechnoback.Technology.Technology;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
@NoArgsConstructor
@Getter
@Setter
public class ProjectRes {
private long id;
private String name;
private Status status;
private List<BasicTechnologyRes> technology;
private LocalDate createdAt;
private LocalDate startDate;
private LocalDate endDate;
private List<String> links;
}
