package com.moxi.veilletechnoback.DTO.History;

import java.time.LocalDate;

import com.moxi.veilletechnoback.Enum.History.HistoryType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoryRes {
    private Long id;
    private HistoryType type;
    private String action;
    private LocalDate timestamp;

    private String fieldName;
    private String oldValue;
    private String newValue;

    private String message;

    private Long projectId;
    private Long featureId;
}
