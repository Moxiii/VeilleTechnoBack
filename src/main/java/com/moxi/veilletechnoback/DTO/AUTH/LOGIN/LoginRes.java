package com.moxi.veilletechnoback.DTO.AUTH.LOGIN;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LoginRes {
private String username;


public LoginRes(String username) {
	this.username = username;

}
}
