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
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String username;
private String email;
private String password;
private String dateInscription;

@OneToMany(mappedBy = "user")
@JsonManagedReference
private List<Project> projects;

private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
public User(String username, String email, String password) {
	this.username = username;
	this.email = email;
	this.password = passwordEncoder.encode(password);

}

public User(){
}

}

