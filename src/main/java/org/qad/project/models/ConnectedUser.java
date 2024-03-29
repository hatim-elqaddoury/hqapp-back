package org.qad.project.models;

import java.util.Optional;

import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConnectedUser {

	Optional<User> user;
	Number activeUsers;
	

	public String ToJsonString() {
		
		JSONObject obj = new JSONObject();
		obj.put("user", user.get().ToJsonString());
		obj.put("activeUsers", activeUsers);
		
		return obj.toString();
	}

	public JSONObject ToJsonObject() {
		
		JSONObject obj = new JSONObject();
		obj.put("user", user.get().ToJsonString());
		obj.put("activeUsers", activeUsers);
		
		return obj;
	}
}
