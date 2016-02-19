package com.gocpf.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.gocpf.web.service.Rating;

@Document(indexName = "gocpf",type="organisme",shards = 1, replicas = 0, refreshInterval = "-1")
public class Organisme implements Serializable {
	@Id
	private String id;
	private String name;
//	@NestedField(type=FieldType.Object, dotSuffix = "addresse")
	@Field(type=FieldType.Nested)
	private Addresse addresse;
	private String telephoneFixe;
	private String telephoneMobile;
	private String fax;
	private String url;
	private String raisonSocial;
	private String logoUrl;
	private String siret;
	private String numeroEnregistrement;
	private String email;
	
//	@Field(type=FieldType.Nested)
	private List<Session> sessions = new ArrayList<Session>();
    private List<Rating> ratings = new ArrayList<Rating>();

	
	
	public Organisme() {
	}

	
	public Organisme(String name, Addresse addresse, String telephoneFixe, String telephoneMobile, String fax,
			String url, String raisonSocial, List<Session> sessions) {
		this.name = name;
		this.addresse = addresse;
		this.telephoneFixe = telephoneFixe;
		this.telephoneMobile = telephoneMobile;
		this.fax = fax;
		this.url = url;
		this.raisonSocial = raisonSocial;
		this.sessions = sessions;
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;







	public String getId() {
		return id;
	}







	public void setId(String id) {
		this.id = id;
	}







	public String getName() {
		return name;
	}







	public void setName(String name) {
		this.name = name;
	}







	public Addresse getAddresse() {
		return addresse;
	}







	public void setAddresse(Addresse addresse) {
		this.addresse = addresse;
	}







	public String getTelephoneFixe() {
		return telephoneFixe;
	}







	public void setTelephoneFixe(String telephoneFixe) {
		this.telephoneFixe = telephoneFixe;
	}







	public String getTelephoneMobile() {
		return telephoneMobile;
	}







	public void setTelephoneMobile(String telephoneMobile) {
		this.telephoneMobile = telephoneMobile;
	}







	public String getFax() {
		return fax;
	}







	public void setFax(String fax) {
		this.fax = fax;
	}







	public String getUrl() {
		return url;
	}







	public void setUrl(String url) {
		this.url = url;
	}







	public String getRaisonSocial() {
		return raisonSocial;
	}







	public void setRaisonSocial(String raisonSocial) {
		this.raisonSocial = raisonSocial;
	}







	public List<Session> getSessions() {
		return sessions;
	}







	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}


	public String getLogoUrl() {
		return logoUrl;
	}


	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}


	public String getSiret() {
		return siret;
	}


	public void setSiret(String siret) {
		this.siret = siret;
	}


	public String getNumeroEnregistrement() {
		return numeroEnregistrement;
	}


	public void setNumeroEnregistrement(String numeroEnregistrement) {
		this.numeroEnregistrement = numeroEnregistrement;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public List<Rating> getRatings() {
		return ratings;
	}


	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

}
