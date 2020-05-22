package org.qad.project.security;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.qad.project.dao.LoginDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
	
	private static final Logger log = Logger.getLogger(SecurityConfigurer.class);
	
	@Autowired
	private LoginDao loginDao;

	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		try {
			return new User(this.loginDao.findByEmailOrUsername(login).getEmail(), this.loginDao.findByEmailOrUsername(login).getPassword(), new ArrayList<>());
		} catch (Exception e) {
			log.info(e);
			return null;
		}
	}
}