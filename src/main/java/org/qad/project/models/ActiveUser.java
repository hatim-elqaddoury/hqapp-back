package org.qad.project.models;

import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiveUser {
	String idUSer;
	String jwt;
	

	public String ToJsonString() {
		
		JSONObject obj = new JSONObject();
		obj.put("idUSer", idUSer);
		obj.put("jwt", jwt);
		
		return obj.toString();
	}

	public JSONObject ToJsonObject() {
		
		JSONObject obj = new JSONObject();
		obj.put("idUSer", idUSer);
		obj.put("jwt", jwt);
		
		return obj;
	}
}
