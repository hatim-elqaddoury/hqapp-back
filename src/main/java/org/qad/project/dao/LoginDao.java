package org.qad.project.dao;

import org.qad.project.models.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LoginDao extends JpaRepository<Login, String> {

	@Query("select l from Login l where l.email = :e ")
	Login findByEmail(@Param("e") String email);

	@Query("select l from Login l where l.email = :l or l.username = :l ")
	Login findByEmailOrUsername(@Param("l") String login);

	@Query("select l from Login l where l.email = :e and l.password = :p ")
	Login findByEmailAndPassword(@Param("e") String email, @Param("p") String password);

	@Query("delete from Login where email = :e")
	Login deleteByEmail(@Param("e") String email);
}