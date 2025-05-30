package com.moxi.veilletechnoback.Ressources;

import com.moxi.veilletechnoback.Enum.Ressources.labelName;
import com.moxi.veilletechnoback.Technology.Technology;
import com.moxi.veilletechnoback.User.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;




@Getter
@Setter
@Entity
public class Ressources {
@ManyToOne
private User user;
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String url;
private labelName label;
@ManyToOne
@JoinColumn(name = "technology_id")
private Technology technology;
}
