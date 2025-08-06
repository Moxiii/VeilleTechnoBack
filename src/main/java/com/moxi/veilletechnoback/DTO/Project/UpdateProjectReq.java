package com.moxi.veilletechnoback.DTO.Project;

import com.moxi.veilletechnoback.Enum.Status;
import com.moxi.veilletechnoback.Technology.Technology;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateProjectReq {
private String name;
private List<String> links;
private List<Long> technology;
private Status status;

}
