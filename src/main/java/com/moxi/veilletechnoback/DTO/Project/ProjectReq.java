package com.moxi.veilletechnoback.DTO.Project;
 
import com.moxi.veilletechnoback.Enum.Project.Status;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
public class ProjectReq {
private long id;
private String name;
private Status status;
private List<Long> technology;
private LocalDate startDate;
private LocalDate endDate;
private List<String> links;
}
