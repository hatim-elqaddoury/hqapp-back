package org.qad.project.business;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.qad.project.dao.UserDao;
import org.qad.project.dao.SettingDao;
import org.qad.project.models.User;
import org.qad.project.models.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
	

	@Autowired
	private UserDao userDao;

	@Autowired
	private SettingDao settingsDao;


	@Override
	public List<User> allUsers() {
		 return userDao.findAll();
	}

	@Override
	public User findbyNP(String nom, String prenom) {
		return userDao.findByNomPrenom(nom, prenom);
	}

	@Override
	public User addUser(User u) {
		return userDao.saveAndFlush(u);
	}

	@Override
	public Setting findByKey(String key) {
		return settingsDao.findByKey(key);
	}

	@Override
	public Setting updateSetting(Setting s) {
		return settingsDao.saveAndFlush(s);
	}

	@Override
	public Optional<User> oneUser(String id) {
		return userDao.findById(id);
	}
	
	
}
