package org.qad.project.dao;

import java.util.Optional;

import org.qad.project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDao extends JpaRepository<User, String> {
	
	@Query("select u from User u where u.id = :v or u.username = :v ")
	Optional<User> findByIdOrUsername(@Param("v") String idusername);
	
	@Query("select u from User u where u.username = :n ")
	Optional<User> findByUsername(@Param("n") String username);
}