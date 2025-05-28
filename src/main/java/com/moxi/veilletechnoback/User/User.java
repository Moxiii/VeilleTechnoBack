package com.moxi.veilletechnoback.User;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;




@Getter
@Setter
@Entity
public class User {
public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}


public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public String getDateInscription() {
	return dateInscription;
}

public void setDateInscription(String dateInscription) {
	this.dateInscription = dateInscription;
}

public String getDescription() {
	return Description;
}

public void setDescription(String description) {
	Description = description;
}


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String username;
private String email;
private String password;
private String dateInscription;
private String Description;



private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
public User(String username, String email, String password) {
	this.username = username;
	this.email = email;
	this.password = passwordEncoder.encode(password);

}

public User(){
}

}

