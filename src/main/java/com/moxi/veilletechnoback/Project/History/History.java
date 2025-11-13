package com.moxi.veilletechnoback.Project.History;

import com.moxi.veilletechnoback.Enum.History.Status;
import com.moxi.veilletechnoback.Project.Project;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Getter
@Setter
public class History {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private Status status;
private String action;
private LocalDate timestamp;
private LocalDate startDate;
private LocalDate endDate = null;
@ManyToOne
@JoinColumn(name = "project_id")
private Project project;
}
