package com.moxi.veilletechnoback.DTO.Concepts;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConceptsReq {
    private String name;
    private String description;
    private Long technologyId;
    private List<Long> projectIds;
}
