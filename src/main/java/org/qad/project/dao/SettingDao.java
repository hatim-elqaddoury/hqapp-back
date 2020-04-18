package org.qad.project.dao;

import org.qad.project.models.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SettingDao extends JpaRepository<Setting, String>{
	
	@Query("select s from Setting s where s.name = :k ")
	public Setting findByKey(@Param("k") String name);
	
	
	

}
