package org.qad.project.web;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.qad.project.business.AdminService;
import org.qad.project.models.ActiveUser;
import org.qad.project.models.ConnectedUser;
import org.qad.project.models.Encrypted;
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
@SuppressWarnings({"static-access"})
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
	public Object getConnected(@RequestParam String authorisationHeader) {
		String token = this.jwtTokenUtil.extractToken(authorisationHeader);
		try {
			String email = this.jwtTokenUtil.extractEmail(token);
			Optional<User> user = this.adminService.getUserByEmail(email);
			
			//set Datenow as last connexion date
			user.get().setLastCnx(Instant.now());
			
			this.adminService.updateUser(user);
			
			this.utils.addActiveUser(this.activeUsers, user, token);
			
			ConnectedUser res = new ConnectedUser(user, this.activeUsers.size());
			
			return new JSONObject().put("encrypted", utils.encrypt(res.ToJsonString())).toString();
			
				
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

	@GetMapping({"/getActiveUsers"})
	public Object getAllConnected() {
		
		try {
			List<Optional<User>> res = new ArrayList<Optional<User>>();
			List<String> out = new ArrayList<String>();
			JSONArray jsonArray = new JSONArray();
			
			this.activeUsers.forEach(a -> res.add(this.adminService.oneUser(a.getIdUSer())));
			res.forEach(a-> {out.add(a.get().getUsername()); jsonArray.put(a.get().ToJsonObject());});
			
			log.info("Connected user : "+ out);
			
			return new JSONObject().put("encrypted", utils.encrypt(jsonArray.toString())).toString();
			
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@PostMapping({"/login"})
	public ResponseEntity<?> login(@RequestBody LinkedHashMap<String, String> l) throws Exception {
		try {
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(l.get("login"), UtilsController.encrypt(l.get("password"))));
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(l.get("login"));
			if(userDetails==null) return null;
			String token = this.jwtTokenUtil.generateToken(userDetails);
			String email = this.jwtTokenUtil.extractEmail(token);
			Optional<User> user = this.adminService.getUserByEmail(email);
			log.info((user.get()).getUsername() + " logged in");
			this.utils.addActiveUser(this.activeUsers, user, token);
			return ResponseEntity.ok(new LoginResponse(token));
		} catch (BadCredentialsException e) {
			log.error(e.getMessage());
			return ResponseEntity.badRequest().body("Username/Password combination is not correct, please try again.");
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.badRequest().body("Account not found.");
		}
	}
	

	@PostMapping({"/singInGoogle"})
	public ResponseEntity<?> singInGoogle(@RequestBody LinkedHashMap<String, String> obj) throws Exception {
		
		JSONObject l = new JSONObject(UtilsController.decrypt(obj.get("encrypted")).toString());

		if ( l.get("userName")!=null && l.get("fullName")!=null && l.get("login")!=null && l.get("password")!=null) {
			try {
				//register
				User u = this.adminService.register(l.get("fullName").toString(), l.get("userName").toString(), 
						l.get("login").toString(), UtilsController.encrypt(l.get("password").toString()).toString());
				
				if (u != null) {
					UserDetails userDetails = this.userDetailsService.loadUserByUsername(l.get("login").toString());
					String token = this.jwtTokenUtil.generateToken(userDetails);

					u.setJoinedDate(Instant.now());
					u.setAvatar(l.get("avatar").toString());
					
					// mail is verified by default
					u.setMailVerified(true);
					
					this.adminService.updateUser(Optional.of(u));
					
					log.info(u.getUsername() + " registred via google");
					return ResponseEntity.ok(new LoginResponse(token));
				} else {
					//login
					this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(l.get("login"), UtilsController.encrypt(l.get("password").toString())));
					UserDetails userDetails = this.userDetailsService.loadUserByUsername(l.get("login").toString());
					if(userDetails==null) return null;
					String token = this.jwtTokenUtil.generateToken(userDetails);
					String email = this.jwtTokenUtil.extractEmail(token);
					Optional<User> user = this.adminService.getUserByEmail(email);
					log.info((user.get()).getUsername() + " logged in via google");
					this.utils.addActiveUser(this.activeUsers, user, token);
					return ResponseEntity.ok(new LoginResponse(token));
					
				}
			} catch (Exception e) {
				return ResponseEntity.badRequest().body("Something went wrong");
			}
		}
		return ResponseEntity.badRequest().body("Required colomuns are null");
	}

	@PostMapping({"/logout"})
	public ResponseEntity<?> logout(@RequestParam String authorisationHeader) throws Exception {

		String token = this.jwtTokenUtil.extractToken(authorisationHeader);

		this.activeUsers.removeIf((elem) -> {
			return elem.getJwt().equals(token);
		});

		try {
			String email = this.jwtTokenUtil.extractEmail(token);
			Optional<User> user = this.adminService.getUserByEmail(email);

			log.info((user.get()).getUsername() + " logged out");
			return null;
		} catch (Exception e) {

			log.error("(logout) " + e.getMessage());
			return null;
		}
	}

	@PostMapping({"/register"})
	public ResponseEntity<?> register(@RequestBody LinkedHashMap<String, String> l) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Responded", "AdminController");
		if ( l.get("userName")!=null && (l.get("password")).equals(l.get("confirmPassword"))
				&& Boolean.parseBoolean(l.get("terms"))) {
			
			try {
				User u = this.adminService.register(l.get("fullName"), l.get("userName"), l.get("email"),
						UtilsController.encrypt(l.get("password")).toString());
				if (u != null) {
					UserDetails userDetails = this.userDetailsService.loadUserByUsername(l.get("email"));
					String token = this.jwtTokenUtil.generateToken(userDetails);

					u.setJoinedDate(Instant.now());
					this.adminService.updateUser(Optional.of(u));
					
					log.info(u.getUsername() + " registred");
					return ResponseEntity.ok(new LoginResponse(token));
				} else {
					return ResponseEntity.badRequest().body("account already exist");
				}
			} catch (Exception e) {
				return ResponseEntity.badRequest().body("account already exist");
			}
		} else if (l.get("userName")!=null) {
			return ResponseEntity.badRequest().body("Username is required");
		} else if (!(l.get("password")).equals(l.get("confirmPassword"))) {
			return ResponseEntity.badRequest().body(new Encrypted("password doesnt match"));
		} else {
			return !Boolean.parseBoolean( l.get("terms"))
					? ResponseEntity.badRequest().body(new Encrypted("Terms not accepted"))
					: ResponseEntity.badRequest().body(new Encrypted("Bad request"));
		}
	}
}