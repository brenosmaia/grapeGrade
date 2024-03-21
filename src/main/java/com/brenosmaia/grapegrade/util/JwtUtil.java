package com.brenosmaia.grapegrade.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtil {

	private String secretkey = "Vx2iFmd4Qp9XKZq3lGyDcvHs6rNbRJt0OoL7Afwk5SuEPjYWhTz1IeMnCaUbgB8d";
	
	private String createToken(Map<String, Object> claims, String username) {
		return Jwts.builder()
				.claims().empty().add(claims)
				.subject(username)
				.issuedAt(new Date(System.currentTimeMillis()))
		        .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)).and()
		        .signWith(getSigningKey()).compact();
	}
	
	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(secretkey.getBytes(StandardCharsets.UTF_8));
	}
	
	private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
	
	private Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith((SecretKey) getSigningKey()).build().parseSignedClaims(token).getPayload();
	}
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	public String generateToken(String username) {
		return createToken(new HashMap<String, Object>(), username);
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
