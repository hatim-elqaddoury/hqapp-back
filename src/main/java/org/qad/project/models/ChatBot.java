package org.qad.project.models;


import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatBot {
	
	@Id
	private String id;
	private String regExp;
	private String answerArray;
	private String type;
	

}
