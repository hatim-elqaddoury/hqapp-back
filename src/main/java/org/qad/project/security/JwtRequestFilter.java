package org.qad.project.security;

import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private JwtUtil jwtUtil;
	private static final Logger log = Logger.getLogger(JwtRequestFilter.class);

	@Value("${jwt.header-name}")
	private String HEADER_NAME;
		
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Headers","Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, HQ-authorise, Access-Control-Headers, authorization,Content-Disposition");
		response.addHeader("Access-Control-Expose-Headers","Access-Control-Allow-Origin, Access-Control-Allow-Credentials, authorization, HQ-authorise, Accept, Content-Disposition");
		

		response.addHeader("Access-Control-Allow-Headers",HEADER_NAME.toString());
		response.addHeader("Access-Control-Expose-Headers", HEADER_NAME.toString());
				
		if (request.getMethod().equals("OPTIONS")) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			String authorisationHeader = request.getHeader(HEADER_NAME);
			
			try {
				String token = (authorisationHeader!=null) ?this.jwtUtil.extractToken(authorisationHeader) :null;
				String email = (token!=null) ?this.jwtUtil.extractEmail(token) :null;
				
				if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					try {
						UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
						if (this.jwtUtil.validateToken(token, userDetails)) {
							UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
									userDetails, (Object) null, userDetails.getAuthorities());
							usernamePasswordAuthenticationToken
									.setDetails((new WebAuthenticationDetailsSource()).buildDetails(request));
							SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
						}
					} catch (UsernameNotFoundException e) {
						log.error(e.getMessage());
					}
				}
			} catch (ExpiredJwtException e) {
				log.info("(doFilterInternal) " + e.getMessage());
				return;
			}catch(Exception e) {
				log.error(e.getMessage());
				return;
			}

			filterChain.doFilter(request, response);
		}
	}
}