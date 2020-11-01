package org.qad.project.business;

import java.util.List;
import java.util.Optional;
import org.qad.project.models.Setting;
import org.qad.project.models.User;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {
	User addUser(Optional<User> user);

	User updateUser(Optional<User> user);

	Optional<User> find(String id);

	List<User> allUsers();

	Optional<User> oneUser(String id);

	Optional<User> findByUsername(String username);

	Setting findByKey(String key);

	Setting updateSetting(Setting s);

	Optional<User> login(String email, String password, Boolean rememberMe);

	User register(String fullName, String username, String email, String password);

	Optional<User> getUserByEmail(String email);
}