package org.qad.project.business;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.qad.project.dao.LoginDao;
import org.qad.project.dao.SettingDao;
import org.qad.project.dao.UserDao;
import org.qad.project.models.Login;
import org.qad.project.models.Setting;
import org.qad.project.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private SettingDao settingsDao;
	@Autowired
	private LoginDao loginDao;

	public List<User> allUsers() {
		return this.userDao.findAll();
	}

	public Optional<User> findByUsername(String username) {
		return this.userDao.findByUsername(username);
	}

	public User addUser(Optional<User> u) {
		return (u.get()!=null) ?this.userDao.saveAndFlush(u.get()) :null;
	}

	public Setting findByKey(String key) {
		return this.settingsDao.findByKey(key);
	}

	public Setting updateSetting(Setting s) {
		return (Setting) this.settingsDao.saveAndFlush(s);
	}

	public Optional<User> oneUser(String idusername) {
		return this.userDao.findByIdOrUsername(idusername);
	}

	public Optional<User> login(String email, String password, Boolean rememberMe) {
		return this.userDao.findById(this.loginDao.findByEmailAndPassword(email, password).getId());
	}

	public User register(String fullName, String username, String email, String password) {
		if (this.loginDao.findByEmail(email) == null) {
			Login l = this.loginDao.saveAndFlush(new Login(null, username, email, password, "User",  null));
			
			return this.addUser(Optional.of(new User(l.getId(), l.getUsername(), fullName,
					l.getEmail(), l.getRole(), null,  null, "default", Instant.now(), null, false, null)));
		} else {
			return null;
		}
	}

	public Optional<User> find(String id) {
		return this.userDao.findById(id);
	}

	public Optional<User> getUserByEmail(String email) {
		return this.userDao.findById(this.loginDao.findByEmail(email).getId());
	}

	public User updateUser(Optional<User> user) {
		return (user.get()!=null) ?this.userDao.saveAndFlush(user.get()) :null;
	}


}