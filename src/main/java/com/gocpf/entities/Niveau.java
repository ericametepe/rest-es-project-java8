
package com.gocpf.entities;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
@Document(indexName="gocpf",type="niveau",replicas=1,shards=0,refreshInterval="-1")
public class Niveau implements Serializable {
	
	@Id
	private String id;
	private String nom;
	private String description;
	
	
	public Niveau() {
	}
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public Niveau(String id, String nom, String description) {
		this.id = id;
		this.nom = nom;
		this.description = description;
	}



	



	public Niveau(String nom, String description) {
		super();
		this.nom = nom;
		this.description = description;
	}



	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1233839944876552853L;
	

}
