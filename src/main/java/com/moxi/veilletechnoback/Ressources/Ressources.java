package com.moxi.veilletechnoback.Ressources;

import com.moxi.veilletechnoback.Technology.Technology;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;




@Getter
@Setter
@Entity
public class Ressources {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String title;
private String url;
@ManyToOne
@JoinColumn(name = "technology_id")
private Technology technology;
}
