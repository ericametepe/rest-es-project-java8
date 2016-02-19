package com.gocpf.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gocpf.entities.Intitule;
import com.gocpf.web.service.Rating;
@JsonIgnoreProperties
public class FormationDto {
	
	public FormationDto() {
	}
	
	private String id;
    private String niveau;
    private Intitule intitule;
    //http://www.rncp.cncp.gouv.fr/grand-public/visualisationFiche : search by code
	private String codeRNCP;
    private String codeInventaire;
    private String codeCertifInfo;
    private String codeOffreInfo;
    private String codeCPF;
    private Date debutdevalidite;
    private Date findevalidite;
    private String organismecertificateur;
    private String organismeEditeur;
    private String publicAuditeur;
    private String couvertureGeo;
    
    private List<String> codeAPEs= new ArrayList<String>();
    private List<String> organismes = new ArrayList<String>();
    private List<Rating> ratings = new ArrayList<Rating>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNiveau() {
		return niveau;
	}
	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}
	
	public String getCodeRNCP() {
		return codeRNCP;
	}
	public void setCodeRNCP(String codeRNCP) {
		this.codeRNCP = codeRNCP;
	}
	public String getCodeInventaire() {
		return codeInventaire;
	}
	public void setCodeInventaire(String codeInventaire) {
		this.codeInventaire = codeInventaire;
	}
	public String getCodeCertifInfo() {
		return codeCertifInfo;
	}
	public void setCodeCertifInfo(String codeCertifInfo) {
		this.codeCertifInfo = codeCertifInfo;
	}
	public String getCodeOffreInfo() {
		return codeOffreInfo;
	}
	public void setCodeOffreInfo(String codeOffreInfo) {
		this.codeOffreInfo = codeOffreInfo;
	}
	public String getCodeCPF() {
		return codeCPF;
	}
	public void setCodeCPF(String codeCPF) {
		this.codeCPF = codeCPF;
	}
	public Date getDebutdevalidite() {
		return debutdevalidite;
	}
	public void setDebutdevalidite(Date debutdevalidite) {
		this.debutdevalidite = debutdevalidite;
	}
	public Date getFindevalidite() {
		return findevalidite;
	}
	public void setFindevalidite(Date findevalidite) {
		this.findevalidite = findevalidite;
	}
	public String getOrganismecertificateur() {
		return organismecertificateur;
	}
	public void setOrganismecertificateur(String organismecertificateur) {
		this.organismecertificateur = organismecertificateur;
	}
	public String getOrganismeEditeur() {
		return organismeEditeur;
	}
	public void setOrganismeEditeur(String organismeEditeur) {
		this.organismeEditeur = organismeEditeur;
	}
	public String getPublicAuditeur() {
		return publicAuditeur;
	}
	public void setPublicAuditeur(String publicAuditeur) {
		this.publicAuditeur = publicAuditeur;
	}
	public String getCouvertureGeo() {
		return couvertureGeo;
	}
	public void setCouvertureGeo(String couvertureGeo) {
		this.couvertureGeo = couvertureGeo;
	}
	public List<String> getCodeAPEs() {
		return codeAPEs;
	}
	public void setCodeAPEs(List<String> codeAPEs) {
		this.codeAPEs = codeAPEs;
	}
	public List<String> getOrganismes() {
		return organismes;
	}
	public void setOrganismes(List<String> organismes) {
		this.organismes = organismes;
	}
	public List<Rating> getRatings() {
		return ratings;
	}
	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}
	public Intitule getIntitule() {
		return intitule;
	}
	public void setIntitule(Intitule intitule) {
		this.intitule = intitule;
	}

}
