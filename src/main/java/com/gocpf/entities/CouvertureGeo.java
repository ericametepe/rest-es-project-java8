package com.gocpf.entities;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Created by kodjovi1 on 22/11/2015.
 *       { "id":1 ,"name":"alsace", "dpt": [67, 68], "comite":"COAPAREF"},

 */
@Document(indexName = "gocpf",type="couverturegeo",shards = 1, replicas = 0, refreshInterval = "-1")
public class CouvertureGeo {
    @Id
	private String id;
    private String nom;
    private List<String> dpt=new ArrayList<>();
    private List<String> comite= new ArrayList<>();
    
    public CouvertureGeo() {
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

	public List<String> getDpt() {
		return dpt;
	}

	public void setDpt(List<String> dpt) {
		this.dpt = dpt;
	}

	public List<String> getComite() {
		return comite;
	}

	public void setComite(List<String> comite) {
		this.comite = comite;
	}

	

   
}
