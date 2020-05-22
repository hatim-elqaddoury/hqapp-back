package org.qad.project.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@SuppressWarnings("unused")
@Service
public class JwtUtil {
	private static final Logger log = Logger.getLogger(JwtUtil.class);
	private String SECRET_KEY = "secret";

	public String extractEmail(String token) {
		return (String) this.extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return (Date) this.extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		Claims claims = this.extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return (Claims) Jwts.parser().setSigningKey(this.SECRET_KEY).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		return this.extractExpiration(token).before(new Date());
	}

	public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<String, Object>();
        return this.createToken(claims, userDetails.getUsername());
	}

	public String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() * 1000 * 60 * 60 * 1/15)) 
				.signWith(SignatureAlgorithm.HS256, this.SECRET_KEY).compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		String email = this.extractEmail(token);
		return token != null && userDetails != null
				? email.equals(userDetails.getUsername()) && !this.isTokenExpired(token)
				: false;
		/*
		 *  if (token != null && userDetails != null) {
            	return email.equals(userDetails.getUsername()) && !this.isTokenExpired(token);
	        }
	        return false;
		 */
	}
}