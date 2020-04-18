package org.qad.project.business;

import java.util.List;
import java.util.Optional;

import org.qad.project.models.User;
import org.qad.project.models.Setting;
import org.springframework.stereotype.Service;



@Service
public interface AdminService {

	public User addUser(User user);
	
	public List<User> allUsers();

	public Optional<User> oneUser(String id);
	
	public User findbyNP(String nom, String prenom);

	public Setting findByKey(String key);
	
	public Setting updateSetting(Setting s);
	
}
