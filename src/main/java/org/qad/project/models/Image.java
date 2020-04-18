package org.qad.project.models;

import java.io.Serializable;
import java.sql.Blob;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image implements Serializable{

	@Id
	@GeneratedValue
	private Integer img_id;
	private String img_nom;
	private String img_taille;
	private String img_type;
	private String img_desc;
	private Blob img_blob;
}
