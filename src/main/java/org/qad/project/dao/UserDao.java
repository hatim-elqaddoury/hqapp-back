package org.qad.project.dao;

import org.qad.project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDao extends JpaRepository<User, String>{
	
	@Query("select u from User u where u.nom = :n and u.prenom = :p")
	public User findByNomPrenom(@Param("n") String nom, @Param("p") String prenom);
	
}
