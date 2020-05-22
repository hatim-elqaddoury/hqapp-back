package org.qad.project.models;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

	@Id
	private String idUser;
	@Column(unique = true)
	private String username;
	private String fullname;
	@Column(unique = true)
	private String email;
	private String role;
	private String avatar;
	private String phone;
	private String theme;
	
}