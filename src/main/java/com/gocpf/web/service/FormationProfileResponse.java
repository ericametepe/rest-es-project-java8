package com.gocpf.web.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.gocpf.entities.CouvertureGeo;
import com.gocpf.entities.Formation;
import com.gocpf.entities.Niveau;
import com.gocpf.entities.Organisme;

public class FormationProfileResponse implements Serializable {
	
	private Formation formation;
	private Niveau niveau;
	private CouvertureGeo couvertureGeo;
	private List<Organisme> organismesF = new ArrayList<Organisme>();
	


	public FormationProfileResponse(Formation formation, Niveau niveau, CouvertureGeo couvertureGeo,
			List<Organisme> organismes) {
		this.formation = formation;
		this.niveau = niveau;
		this.couvertureGeo = couvertureGeo;
		this.organismesF = organismes;
	}


	public FormationProfileResponse() {
	}

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public Niveau getNiveau() {
		return niveau;
	}


	public void setNiveau(Niveau niveau) {
		this.niveau = niveau;
	}


	public Formation getFormation() {
		return formation;
	}


	public void setFormation(Formation formation) {
		this.formation = formation;
	}


	public CouvertureGeo getCouvertureGeo() {
		return couvertureGeo;
	}


	public void setCouvertureGeo(CouvertureGeo couvertureGeo) {
		this.couvertureGeo = couvertureGeo;
	}


	public List<Organisme> getOrganismes() {
		return organismesF;
	}


	public void setOrganismes(List<Organisme> organismes) {
		this.organismesF = organismes;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((formation == null) ? 0 : formation.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormationProfileResponse other = (FormationProfileResponse) obj;
		if (formation.getCodeCPF()== null) {
			if (other.formation.getCodeCPF() != null)
				return false;
		} else if (!formation.getCodeCPF().equals(other.formation.getCodeCPF()))
			return false;
		return true;
	}


}
