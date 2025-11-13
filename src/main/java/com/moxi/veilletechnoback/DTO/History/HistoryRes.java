package com.moxi.veilletechnoback.DTO.History;

import java.time.LocalDate;

import com.moxi.veilletechnoback.Enum.History.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoryRes {
    private Long id;
    private String action;
    private Status status;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate timestamp;
}
