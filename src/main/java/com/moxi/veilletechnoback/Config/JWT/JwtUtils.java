package com.moxi.veilletechnoback.Config.JWT;
import com.moxi.veilletechnoback.User.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import com.moxi.veilletechnoback.User.UserService;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class JwtUtils {
private final Map<Long , String> tokenMap = new ConcurrentHashMap<>();
private final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
private final long ACCESS_TOKEN_VALIDITY = 60*60*1000;
private final long REFRESH_TOKEN_VALIDITY = 24 * 60 * 60 * 1000;
private final String TOKEN_HEADER = "Authorization";
private final String TOKEN_PREFIX = "Bearer ";
@Autowired
private UserService userService;
public JwtUtils() {
	this.jwtParser = Jwts.parser().setSigningKey(SECRET_KEY).build();
}
private final JwtParser jwtParser;




public String createAccessToken(User user){
	if (user.getId() == null) {
		throw new IllegalArgumentException("User ID cannot be null when creating token");
	}
	Claims claims = Jwts.claims().setSubject(String.valueOf(user.getId())).build();
	Date tokenCreateTime = new Date();
	Date tokenValidity = new Date(tokenCreateTime.getTime() + ACCESS_TOKEN_VALIDITY);
	String accessToken =  Jwts.builder()
			.setClaims(claims)
			.setExpiration(tokenValidity)
			.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
			.compact();
	return accessToken;
}
public String createRefreshToken(User user){
	Claims claims = Jwts.claims().setSubject(String.valueOf(user.getId())).build();
	Date tokenCreateTime = new Date();
	Date tokenValidity = new Date(tokenCreateTime.getTime() + REFRESH_TOKEN_VALIDITY);
	String refreshToken =  Jwts.builder()
			.setClaims(claims)
			.setExpiration(tokenValidity)
			.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
			.compact();
	return refreshToken;
}
private Claims parseJwtClaims(String token) {
	return jwtParser.parseClaimsJws(token).getBody();
}


public boolean isTokenExpired(String token) {
	Claims claims = Jwts.parser()
			.setSigningKey(SECRET_KEY).
			build().parseClaimsJws(token)
			.getPayload();
	Date expiration = claims.getExpiration();
	return expiration.before(new Date());
}

public boolean validateToken(String token) {
	try{
		jwtParser.parseClaimsJws(token).getPayload();
		return true;
	}catch (JwtException e) {
		return false;
	} catch (IllegalArgumentException e) {
		return false;
	} catch (Exception e) {
		return false;
	}
}

public Long extractUserId(String token) {
	Claims claims = parseJwtClaims(token);
	return Long.parseLong(claims.getSubject());
}


public void addToken(long userId , String token){
	tokenMap.put(userId, token);
}

public String checkToken(HttpServletRequest request){
	String token = extractTokenFromCookie(request);
	if(validateToken(token) && token != null){
		if(isTokenExpired(token)){
			Long userID = extractUserId(token);
			User currentuser = userService.findById(userID);
			if(currentuser != null){
				String refreshToken = tokenMap.get(currentuser.getId()+ "_refresh");
				if(refreshToken != null && validateToken(refreshToken)){
					String newAccessToken =  createAccessToken(currentuser);
					addToken(currentuser.getId(), newAccessToken);
					return newAccessToken;
				}
			}
		}
		return token;

	}
	return null;
}
public String extractTokenFromCookie(HttpServletRequest request) {
	if(request.getCookies() != null){
		for(Cookie cookie : request.getCookies()){
			if("access_token".equals(cookie.getName())){
				return cookie.getValue();
			}
		}
	}
	return null;
}
}
