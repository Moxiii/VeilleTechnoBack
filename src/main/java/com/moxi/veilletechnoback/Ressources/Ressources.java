package com.moxi.veilletechnoback.Ressources;

import com.moxi.veilletechnoback.Technologies.Technologies;
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
@JoinColumn(name = "technologies_id")
private Technologies technologies;
}
