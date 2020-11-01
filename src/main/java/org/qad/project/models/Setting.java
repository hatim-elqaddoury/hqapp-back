package org.qad.project.models;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Setting implements Serializable {

	@Id
	private String id;
	private String name;
	private String value;
	

	public String ToJsonString() {
		
		JSONObject obj = new JSONObject();
		obj.put("id", id);
		obj.put("name", name);
		obj.put("value", value);
		
		return obj.toString();
	}

	public JSONObject ToJsonObject() {
		
		JSONObject obj = new JSONObject();
		obj.put("id", id);
		obj.put("name", name);
		obj.put("value", value);
		
		return obj;
	}
	
}