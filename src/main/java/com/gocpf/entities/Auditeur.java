package com.gocpf.entities;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

import org.springframework.data.elasticsearch.annotations.Document;



/**
 * 
 * @author kodjovi1
 * Tout Public - DE - Salarie
 */
@Document(indexName="gocpf",type="auditeur", shards=1, replicas=0,refreshInterval="-1")
public class Auditeur implements Serializable  {
	@Id
	private String id;
	private String nom;
	private String intitule;
	private String description;
	
	
	



	public Auditeur(String id, String nom, String description) {
		this.id = id;
		this.nom = nom;
		this.description = description;
	}


	public Auditeur() {
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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


	@Override
	public String toString() {
		return "Auditeur [id=" + id + ", nom=" + nom + ", description=" + description + "]";
	}


	public String getIntitule() {
		return intitule;
	}


	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}
	

}
