package com.moxi.veilletechnoback.DTO.AUTH.REGISTER;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterDTO {
private String username;
private String password;
private String email;
private String firstName;
private String lastName;
}
