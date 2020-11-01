package org.qad.project.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;
import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Login {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	@Column(unique = true)
	private String username;
	@Column(unique = true)
	private String email;
	private String password;
	private String role;
	private Boolean rememberMe;
	

	public String ToJsonString() {
		
		JSONObject obj = new JSONObject();
		obj.put("id", id);
		obj.put("username", username);
		obj.put("email", email);
		obj.put("password", password);
		obj.put("role", role);
		obj.put("rememberMe", rememberMe);
		
		return obj.toString();
	}

	public JSONObject ToJsonObject() {
		
		JSONObject obj = new JSONObject();
		obj.put("id", id);
		obj.put("username", username);
		obj.put("email", email);
		obj.put("password", password);
		obj.put("role", role);
		obj.put("rememberMe", rememberMe);
		
		return obj;
	}
	
	
}
