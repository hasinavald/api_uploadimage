package com.signal.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(	name = "typesignals", 
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "type")
		})
public class TypeSignal {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 20)
	private String type;
	
	private String couleur;

	public TypeSignal() {

	}

	public TypeSignal(String type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getcouleur() {
		return couleur;
	}

	public void setcouleur(String couleur) {
		this.couleur = couleur;
	}
	
	
}
