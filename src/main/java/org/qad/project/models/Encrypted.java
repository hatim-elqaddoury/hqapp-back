package org.qad.project.models;


import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Encrypted {

	String encrypted;
	

	public String ToJsonString() {
		
		JSONObject obj = new JSONObject();
		obj.put("encrypted", encrypted);
		
		return obj.toString();
	}

	public JSONObject ToJsonObject() {
		
		JSONObject obj = new JSONObject();
		obj.put("encrypted", encrypted);
		
		return obj;
	}
}
