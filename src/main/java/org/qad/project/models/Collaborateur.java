package org.qad.project.models;


import java.io.Serializable;

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
public class Collaborateur implements Serializable {

	@Id
	private String idCollab;
	private String nom;
	private String prenom;
	
}