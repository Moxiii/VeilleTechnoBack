package com.moxi.veilletechnoback.Project.History;

import com.moxi.veilletechnoback.Enum.History.HistoryType;
import com.moxi.veilletechnoback.Project.Project;
import com.moxi.veilletechnoback.Project.Features.Features;

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
private HistoryType type;
private String action;
private LocalDate timestamp;
private String fieldName;
private String oldValue;
private String newValue;
private String message;
@ManyToOne
@JoinColumn(name = "project_id")
private Project project;
@ManyToOne
@JoinColumn(name="feature_id")
private Features feature;
}
