package org.qad.project.models;


import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.json.JSONObject;

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
	@Column(unique = true, nullable = false)
	private String username;
	@NotNull
	private String fullname;
	@Column(unique = true, nullable = false)
	private String email;
	@NotNull
	private String role;
	private String avatar;
	private String phone;
	@NotNull
	private String theme;
	@NotNull
	private Instant joinedDate;
	private Instant lastCnx;
	@Column(columnDefinition="bit(1) default 0", nullable = false)
	private boolean mailVerified;
	private String location;
	

	public String ToJsonString() {
		
		JSONObject obj = new JSONObject();
		obj.put("idUser", idUser);
		obj.put("username", username);
		obj.put("fullname", fullname);
		obj.put("email", email);
		obj.put("role", role);
		obj.put("avatar", avatar);
		obj.put("phone", phone);
		obj.put("theme", theme);
		obj.put("joinedDate", joinedDate);
		obj.put("lastCnx", lastCnx);
		obj.put("mailVerified", mailVerified);
		obj.put("location", location);
		
		return obj.toString();
	}

	public JSONObject ToJsonObject() {

		JSONObject obj = new JSONObject();
		obj.put("idUser", idUser);
		obj.put("username", username);
		obj.put("fullname", fullname);
		obj.put("email", email);
		obj.put("role", role);
		obj.put("avatar", avatar);
		obj.put("phone", phone);
		obj.put("theme", theme);
		obj.put("joinedDate", joinedDate);
		obj.put("lastCnx", lastCnx);
		obj.put("mailVerified", mailVerified);
		obj.put("locationlocation", location);
		
		return obj;
	}
	
	public User SetFromJson(JSONObject obj) {
		User usr  = new User();
		if(obj.has("idUser"))usr.setIdUser(obj.getString("idUser"));
		if(obj.has("username"))usr.setUsername(obj.getString("username"));
		if(obj.has("fullname"))usr.setFullname(obj.getString("fullname"));
		if(obj.has("email"))usr.setEmail(obj.getString("email"));
		if(obj.has("role"))usr.setRole(obj.getString("role"));
		if(obj.has("avatar")) usr.setAvatar(obj.getString("avatar"));
		if(obj.has("phone")) usr.setPhone(obj.getString("phone"));
		if(obj.has("theme"))usr.setTheme(obj.getString("theme"));
		if(obj.has("joinedDate"))usr.setJoinedDate(Instant.parse(obj.opt("joinedDate").toString()));
		if(obj.has("lastCnx"))usr.setLastCnx(Instant.parse(obj.opt("lastCnx").toString()));
		if(obj.has("mailVerified"))usr.setMailVerified(obj.getBoolean("mailVerified"));
		if(obj.has("location"))usr.setTheme(obj.getString("location"));
		return usr;
	}
	
}