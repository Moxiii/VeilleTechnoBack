package com.moxi.veilletechnoback.User;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.moxi.veilletechnoback.Project.Project;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;


@Getter
@Setter
@Entity
public class User {
@Id
private String id;
private String username;
private String dateInscription;

@OneToMany(mappedBy = "user")
@JsonManagedReference
private List<Project> projects;

public User(String id , String username) {
    this.id = id;
	this.username = username;

}

public User(){
}

}

