package com.cafe.website.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.cafe.website.exception.CafeAPIException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	private String jwtSecret = "daf66e01593f61a15b857cf433aae03a005812b31234e149036bcc8dee755dbb";

	private long jwtExpirationDate  = 604800000;

	// generate JWT token
	public String generateToken(Authentication authentication) {
		String username = authentication.getName();

		Date currentDate = new Date();

		Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

		String token = Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(expireDate)
				.signWith(key()).compact();
		return token;
	}

	public String generateToken(String username) {

		Date currentDate = new Date();

		Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);
		String token = Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(expireDate)
				.signWith(key()).compact();
		return token;
	}

	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}

	// get username from Jwt token
	public String getUsername(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody();
		String username = claims.getSubject();
		return username;
	}

	// validate Jwt token
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
			return true;
		} catch (MalformedJwtException ex) {
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "JWT claims string is empty.");
		}
	}
}
