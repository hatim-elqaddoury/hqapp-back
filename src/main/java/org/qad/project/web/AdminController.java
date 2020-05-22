package org.qad.project.web;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;
import org.qad.project.business.AdminService;
import org.qad.project.models.Setting;
import org.qad.project.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping({"/admin"})
@CrossOrigin(allowedHeaders = {"Access-Control-Allow-Origin", "*", "Access-Control-Allow-Headers", "Content-Type",
		"Access-Control-Expose-Headers", "Access-Control-Request-Method", "HQ-authorise",
		"Access-Control-Allow-Credentials", "X-Request-With"}, 
		methods = {RequestMethod.DELETE, RequestMethod.POST, RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.PUT})

@SuppressWarnings("unused")
public class AdminController {
	
	private static final Logger log = Logger.getLogger(AdminController.class);
	@Autowired
	private AdminService adminService;
	
	private UtilsController utils = new UtilsController();

	@GetMapping({"/users"})
	public List<User> allUsers() {
		return this.adminService.allUsers();
	}

	@GetMapping({"/user"})
	public Optional<User> oneUser(@RequestParam String id) {
		Optional<User> user = this.adminService.oneUser(id);
		return user;
	}

	@GetMapping({"/global/getSetting/{key}"})
	public Setting findSetting(@PathVariable String key) {
		return this.adminService.findByKey(key);
	}

	@PostMapping({"/global/setSetting"})
	public Setting updateSetting(@RequestBody Setting s) {
		return this.adminService.updateSetting(s);
	}

	@PostMapping({"/addUser"})
	public Object addUser(@RequestBody User u) {
		return this.adminService.addUser(u);
	}

	@PostMapping({"/updateUser"})
	public Object updateUser(@RequestBody User u) {
		return this.adminService.updateUser(u);
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