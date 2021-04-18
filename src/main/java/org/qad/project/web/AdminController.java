package org.qad.project.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.qad.project.assets.MailHtmlResponse;
import org.qad.project.business.AdminService;
import org.qad.project.models.Encrypted;
import org.qad.project.models.Setting;
import org.qad.project.models.User;
import org.qad.project.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParser;

@RestController
@RequestMapping({"/admin"})
@CrossOrigin(allowedHeaders = {"Access-Control-Allow-Origin", "*", "Access-Control-Allow-Headers", "Content-Type",
		"Access-Control-Expose-Headers", "Access-Control-Request-Method", "HQ-authorise",
		"Access-Control-Allow-Credentials", "X-Request-With"}, 
		methods = {RequestMethod.DELETE, RequestMethod.POST, RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.PUT})

@SuppressWarnings({"unused"})
public class AdminController {

	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private JwtUtil jwtTokenUtil;

	@Value("${spring.mail.username}")
    protected String SENDER;
	@Value("${spring.mail.password}")
    protected String PASSWORD;
	@Value("${spring.mail.host}")
    protected String HOST;
	@Value("${spring.mail.port}")
    protected String PORT;
	
	@Value("${server.address}")
    protected String SERVER_ADDRESS;
	@Value("${server.port}")
    protected String SERVER_PORT;
	
	
	private static final Logger log = Logger.getLogger(AdminController.class);
	@Autowired
	private AdminService adminService;
	
	private UtilsController utils = new UtilsController();

	@GetMapping({"/users"})
	public Object allUsers() {
		
		List<User> users = this.adminService.allUsers();
		
		JSONArray jsonArray = new JSONArray();
		
		users.forEach(user-> jsonArray.put(user.ToJsonObject()));

		return utils.ToEncryptedJson(jsonArray.toString());
	}

	@GetMapping({"/user"})
	public Object oneUser(@RequestParam String id) {
		
		User user = (this.adminService.oneUser(id).isPresent())?this.adminService.oneUser(id).get():null;

		return utils.ToEncryptedJson(user.ToJsonString());

	}
	
	@GetMapping({"/global/getSetting/{key}"})
	public Object findSetting(@PathVariable String key) {
		
		Setting obj = (this.adminService.findByKey(key)!=null)?this.adminService.findByKey(key):null;
		
		return utils.ToEncryptedJson(obj.ToJsonString());
	}

	@PostMapping({"/global/setSetting"})
	public Setting updateSetting(@RequestBody Setting s) {
		return this.adminService.updateSetting(s);
	}

	@PostMapping({"/addUser"})
	public Object addUser(@RequestBody User u) {
		return this.adminService.addUser(Optional.of(u));
	}

	@PostMapping({"/updateUser"})
	public Object updateUser(@RequestBody Map<String, String>  obj) {
		JSONObject jsonObject = new JSONObject(UtilsController.decrypt(obj.get("encrypted")).toString());
		User user = new User().SetFromJson(jsonObject);
		
		user = this.adminService.updateUser(Optional.of(user));
		
		List<String> out = new ArrayList<String>();
		out.add(user.getUsername());
		log.info("Updating user : "+ out );
		
		if(user.isMailVerified())
		UtilsController.sendMail(
				user, 
				"[Important] HQApp - Updating Profile infos", 
				"<p> Your profile informations have been successfully updated. </p>",
				SENDER, PASSWORD, HOST, PORT);
		
		return utils.ToEncryptedJson(user.toString());
	}
	

	@GetMapping({"/verifyEmail"})
	public Object verifyEmail(@RequestParam String id) {
		
		User user = (this.adminService.oneUser(id).isPresent())?this.adminService.oneUser(id).get():null;
		
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(user.getEmail());
		String token = this.jwtTokenUtil.generateTokenExpDate(userDetails, "1000 * 60 * 10");
		
		String link=  "http://"+SERVER_ADDRESS+":"+SERVER_PORT+"/admin/LM4el1Qp6mnMvDI/wuuqgfihmFZedBRvDMhuKzteKfr?token="+token;
		
		String content = "<p> Click in the link bellow to verify your email and get started with HQApp.<p/>"
				+ "<p> <a target=\"_blank\" href=\" "+ link +" \" /> Confirmation link </a>   </p>"
				+ "<p> note that this link is only valid for 10 min</p>";
			
		if(UtilsController.sendMail(
				user, 
				"[Important] HQApp - Verify your account", 
				content,
				SENDER, PASSWORD, HOST, PORT))
			return ResponseEntity.ok(new Encrypted("Confirmation email has been sent to your address."));
		return ResponseEntity.badRequest().body(new Encrypted("Something went wrong. <Emailing is disabled>"));
	}
	
	@GetMapping({"/LM4el1Qp6mnMvDI/wuuqgfihmFZedBRvDMhuKzteKfr"})
	public Object verifyEmailByLink(@RequestParam String token) {
		
		
		String email;
		try {
			email = this.jwtTokenUtil.extractEmail(token);
			User user = this.adminService.getUserByEmail(email).get();
			
			if(!user.isMailVerified()) {
				user = (this.adminService.oneUser(user.getIdUser()).isPresent())?this.adminService.oneUser(user.getIdUser()).get():null;
				user.setMailVerified(true);
				this.adminService.updateUser(Optional.of(user));
			
				if(UtilsController.sendMail(
					user, 
					"[Important] HQApp - Welcome "+user.getFullname(), 
					"<p> Your Email adress has been verified ;) + ((welcome message)) <p/>",
					SENDER, PASSWORD, HOST, PORT)) {
					
					log.info(user.getUsername()+" has successfully verified his email address: "+user.getEmail());
					return ResponseEntity.ok(MailHtmlResponse.GetMailHtmlResponse(
							"Your email address has been successfully verified.", 
							"https://www.pinclipart.com/picdir/big/387-3875888_verified-account-icon-twitter-verified-account-logo-clipart.png"
							));
				}
				return ResponseEntity.badRequest().body(new Encrypted("Something went wrong. <Emailing is disabled>"));
			}else {
				log.info(user.getUsername()+" tried to reverify the email address: "+user.getEmail());
				
				return ResponseEntity.ok(MailHtmlResponse.GetMailHtmlResponse(
						"Your email address has already been verified.", 
						"https://upload.wikimedia.org/wikipedia/commons/thumb/e/e4/Twitter_Verified_Badge.svg/1200px-Twitter_Verified_Badge.svg.png"
						));
			}
		} catch (Exception e) {
			log.info("link expired while trying to verify an email address.");
			return ResponseEntity.badRequest().body("Link expired.");
		}
		
		

	}

	/*@PostMapping({"/updateUserAvatar"})
	public Object updateUserAvatar(@RequestParam("imageFile") MultipartFile file) {
		try {
			System.out.println(file.getOriginalFilename() + "  - Original Image Byte Size - " + file.getBytes().length);
			Optional<User> user = this.adminService.find(file.getOriginalFilename());
			User u = new User(((User) user.get()).getIdUser(), ((User) user.get()).getFullname(),
					((User) user.get()).getRole(), this.utils.compressBytes(file.getBytes()),
					((User) user.get()).getPhone(), ((User) user.get()).getTheme());
			this.adminService.updateUser(u);
			User us = new User(((User) user.get()).getIdUser(), ((User) user.get()).getFullname(),
					((User) user.get()).getRole(), this.utils.decompressBytes(u.getAvatar()),
					((User) user.get()).getPhone(), ((User) user.get()).getTheme());
			return us;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("updateUserAvatar - " + e.getMessage());
		}
	}*/
}