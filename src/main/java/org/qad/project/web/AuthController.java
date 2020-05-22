package org.qad.project.web;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.qad.project.business.AdminService;
import org.qad.project.models.ActiveUser;
import org.qad.project.models.ConnectedUser;
import org.qad.project.models.LoginResponse;
import org.qad.project.models.User;
import org.qad.project.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.ExpiredJwtException;

@RestController
@RequestMapping({"/auth"})
@CrossOrigin(allowedHeaders = {"Access-Control-Allow-Origin", "*", "Access-Control-Allow-Headers", "Content-Type",
		"Access-Control-Expose-Headers", "Access-Control-Request-Method", "HQ-authorise",
		"Access-Control-Allow-Credentials", "X-Request-With"}, methods = {RequestMethod.DELETE, RequestMethod.POST,
				RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.PUT})
public class AuthController {
	@Autowired
	private AdminService adminService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private JwtUtil jwtTokenUtil;
	private List<ActiveUser> activeUsers = new ArrayList<ActiveUser>();
	private UtilsController utils = new UtilsController();
	private static  Logger log = Logger.getLogger(AuthController.class);

	@GetMapping({"/connected"})
	public ConnectedUser getConnected(@RequestParam String authorisationHeader) {
		if (authorisationHeader != null && authorisationHeader.startsWith("HQ ")) {
			String token = authorisationHeader.substring(3);
			try {
				String email = this.jwtTokenUtil.extractEmail(token);
				Optional<User> user = this.adminService.getUserByEmail(email);
				this.utils.addActiveUser(this.activeUsers, user, token);

				return new ConnectedUser(user, this.activeUsers.size());
					
			} catch (ExpiredJwtException e) {
				this.activeUsers.removeIf((elem) -> {
					return elem.getJwt().equals(token);
				});
				log.error(e.getMessage());
				return null;
			}catch(Exception e) {
				log.error(e.getMessage());
				return null;
			}
		}
		log.error("authorisationHeader is not correct.");
		return null;

	}

	@GetMapping({"/getActiveUsers"})
	public List<Optional<User>> getAllConnected() {
		
		List<Optional<User>> res = new ArrayList<Optional<User>>();
        for ( ActiveUser a : this.activeUsers) {
             Optional<User> user = (Optional<User>)this.adminService.oneUser(a.getIdUSer());
            AuthController.log.info((Object)(user.get().getFullname() + " connected"));
            res.add(user);
        }
        return res;
	}

	@PostMapping({"/login"})
	public ResponseEntity<?> login(@RequestBody LinkedHashMap<String, String> l) throws Exception {
		try {
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(l.get("login"), UtilsController.encrypt(l.get("password"))));
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(l.get("login"));
			String token = this.jwtTokenUtil.generateToken(userDetails);
			String email = this.jwtTokenUtil.extractEmail(token);
			Optional<User> user = this.adminService.getUserByEmail(email);
			log.info((user.get()).getFullname() + " logged in");
			this.utils.addActiveUser(this.activeUsers, user, token);
			return ResponseEntity.ok(new LoginResponse(token));
		} catch (BadCredentialsException e) {
			log.error(e.getMessage());
			return null;
			//throw new Exception("incorrect username or password", e);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
			//throw new Exception("incorrect username or password", e);
		}
	}

	@PostMapping({"/logout"})
	public ResponseEntity<?> logout(@RequestParam String authorisationHeader) throws Exception {
		if (authorisationHeader != null && authorisationHeader.startsWith("HQ ")) {
			String token = authorisationHeader.substring(3);
			this.activeUsers.removeIf((elem) -> {
				return elem.getJwt().equals(token);
			});

			try {
				String email = this.jwtTokenUtil.extractEmail(token);
				Optional<User> user = this.adminService.getUserByEmail(email);
				this.activeUsers.removeIf((elem) -> {
					return elem.getIdUSer().equals((user.get()).getIdUser());
				});
				log.info((user.get()).getFullname() + " logged out");
				return null;
			} catch (Exception e) {
				log.error("(logout) " + e.getMessage());
				return null;
			}
		} else {
			return null;
		}
	}

	@PostMapping({"/register"})
	public ResponseEntity<?> register(@RequestBody LinkedHashMap<String, String> l) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Responded", "AdminController");
		if ((l.get("password")).equals(l.get("confirmPassword"))
				&& Boolean.parseBoolean(l.get("terms"))) {
			try {
				User u = this.adminService.register(l.get("fullName"), l.get("userName"), l.get("email"),
						UtilsController.encrypt(l.get("password")));
				if (u != null) {
					UserDetails userDetails = this.userDetailsService.loadUserByUsername(l.get("email"));
					String token = this.jwtTokenUtil.generateToken(userDetails);
					log.info(u.getFullname() + " registred");
					return ResponseEntity.ok(new LoginResponse(token));
				} else {
					return ResponseEntity.badRequest().body("account already exist");
				}
			} catch (Exception e) {
				return ResponseEntity.badRequest().body("account already exist");
			}
		} else if (!(l.get("password")).equals(l.get("confirmPassword"))) {
			return ResponseEntity.badRequest().body("password doesnt match");
		} else {
			return !Boolean.parseBoolean( l.get("terms"))
					? ResponseEntity.badRequest().body("Terms not accepted")
					: ResponseEntity.badRequest().body("Bad request");
		}
	}
}