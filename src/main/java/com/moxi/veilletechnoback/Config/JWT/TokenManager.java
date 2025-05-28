package com.moxi.veilletechnoback.Config.JWT;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Component
public class TokenManager {
@Autowired
private JwtUtils jwtUtil;
private final Map<String , String> tokenMap = new ConcurrentHashMap<>();


public void addToken(String username , String token){
	tokenMap.put(username , token);
}

public String getValidToken(String username){
	String storedToken = tokenMap.get(username);
	if(storedToken != null && jwtUtil.validateToken(storedToken)){
		return storedToken;
	}
	return null;
}

public void removeToken(String username){
	tokenMap.remove(username);
}
}
