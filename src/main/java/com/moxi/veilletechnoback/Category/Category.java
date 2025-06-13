package com.moxi.veilletechnoback.Category;

import com.moxi.veilletechnoback.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Category {
@GeneratedValue
@Id
private long id;
@ManyToOne
private User user;
private String name;
@Enumerated(EnumType.STRING)
private CategoryEnum type;
}
