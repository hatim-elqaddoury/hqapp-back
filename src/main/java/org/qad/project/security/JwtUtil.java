package org.qad.project.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtUtil {
	
	ScriptEngineManager mgr = new ScriptEngineManager();
    ScriptEngine engine = mgr.getEngineByName("JavaScript");
	private static final Logger log = Logger.getLogger(JwtUtil.class);

	@Value("${jwt.expiration-date}")
	private String EXPIRATION_DATE;
	
	@Value("${jwt.header-prefix}")
	private String HEADER_PREFIX;
	
	@Value("${jwt.secret-key}")
	private String SECRET_KEY;
	
	public String extractToken(String authorisationHeader) {
		log.info(HEADER_PREFIX.trim() +"-- header prefix : " + HEADER_PREFIX.length());
		log.info(authorisationHeader.substring(HEADER_PREFIX.length()));
		return (authorisationHeader != null && authorisationHeader.startsWith(HEADER_PREFIX.trim()))
			? authorisationHeader.substring(HEADER_PREFIX.length()) : null;
	}

	public String extractEmail(String token) {
		return this.extractClaim(token, Claims::getSubject);
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
		try {
			log.info( Long.parseLong(engine.eval(EXPIRATION_DATE).toString()));
			return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(engine.eval(EXPIRATION_DATE).toString()))) 
					.signWith(SignatureAlgorithm.HS256, this.SECRET_KEY).compact();
		} catch (NumberFormatException e) {
			log.error(e.getMessage());
			return null;
		} catch (ScriptException e) {
			log.error(e.getMessage());
			return null;
		}
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		String email = this.extractEmail(token);
		return (token != null && userDetails != null)
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