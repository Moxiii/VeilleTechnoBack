package com.moxi.veilletechnoback.Project.History;

import com.moxi.veilletechnoback.Enum.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class History {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private Status status;
private LocalDate startDate;
private LocalDate endDate = null;

}
