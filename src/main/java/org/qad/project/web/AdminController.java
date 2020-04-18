package org.qad.project.web;

import java.util.List;
import java.util.Optional;

import org.qad.project.business.AdminService;
import org.qad.project.models.User;
import org.qad.project.models.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@CrossOrigin(
		allowedHeaders= {"Access-Control-Allow-Origin","*", 
		"Access-Control-Allow-Headers","Content-Type","Access-Control-Expose-Headers",
		"Access-Control-Request-Method","Access-Control-Allow-Credentials","X-Request-With"},
		methods= {RequestMethod.DELETE,RequestMethod.POST,RequestMethod.GET,RequestMethod.PUT}
		)
public class AdminController {

	@Autowired
	private AdminService adminService;

	@GetMapping("/users")
	public List<User> allUsers() {
		return adminService.allUsers();
	}
	

	@GetMapping("/user")
	public Optional<User> oneUser(@RequestParam(defaultValue = "hatim") String id) {
		return adminService.oneUser(id);
	}
	
	

	@GetMapping("/secret/getSetting/{key}")
	public Setting findSetting(@PathVariable String key) {
		return adminService.findByKey(key);
	}
	

	@PostMapping("secret/setSetting")
	public Setting updateSetting(@RequestBody Setting s) {
		return adminService.updateSetting(s);
	}
	
	
	@PostMapping("/addUser")
	public Object addCollab(@RequestBody User u) {
		return adminService.addUser(u);
	}
	
	
}
