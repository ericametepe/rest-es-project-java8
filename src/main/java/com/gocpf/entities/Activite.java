package com.gocpf.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * http://www.moncompteformation.gouv.fr/espaces-dedies/professionnels-de-lemploi-et-de-la-formation-professionnelle/formations-eligibles-les
 * 
 * http://www.isograd.com/FR/cpf.php
 * @author kodjovi1
 *
 */
@Document(indexName = "gocpf",type="activite",shards = 1, replicas = 0, refreshInterval = "-1")
public class Activite {
	
	@Id
	private String id;
	private String intitule;
	private String codeAPE;
	private String effectifSalarie;
	private String codeIDCC;
	private String intituleCodeIDCC;
	private String couvertureSalarie;
	
	public Activite() {
	}
	
	public Activite(String intitule, String codeAPE, String effectifSalarie, String codeIDCC, String intituleCodeIDCC,
			String couvertureSalarie) {
		super();
		this.intitule = intitule;
		this.codeAPE = codeAPE;
		this.effectifSalarie = effectifSalarie;
		this.codeIDCC = codeIDCC;
		this.intituleCodeIDCC = intituleCodeIDCC;
		this.couvertureSalarie = couvertureSalarie;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIntitule() {
		return intitule;
	}
	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}
	public String getCodeAPE() {
		return codeAPE;
	}
	public void setCodeAPE(String codeAPE) {
		this.codeAPE = codeAPE;
	}
	public String getEffectifSalarie() {
		return effectifSalarie;
	}
	public void setEffectifSalarie(String effectifSalarie) {
		this.effectifSalarie = effectifSalarie;
	}
	public String getCodeIDCC() {
		return codeIDCC;
	}
	public void setCodeIDCC(String codeIDCC) {
		this.codeIDCC = codeIDCC;
	}
	public String getIntituleCodeIDCC() {
		return intituleCodeIDCC;
	}
	public void setIntituleCodeIDCC(String intituleCodeIDCC) {
		this.intituleCodeIDCC = intituleCodeIDCC;
	}
	public String getCouvertureSalarie() {
		return couvertureSalarie;
	}
	public void setCouvertureSalarie(String couvertureSalarie) {
		this.couvertureSalarie = couvertureSalarie;
	}



	@Override
	public String toString() {
		return "Activite [id=" + id + ", intitule=" + intitule + ", codeAPE=" + codeAPE + ", effectifSalarie="
				+ effectifSalarie + ", codeIDCC=" + codeIDCC + ", intituleCodeIDCC=" + intituleCodeIDCC
				+ ", couvertureSalarie=" + couvertureSalarie + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codeAPE == null) ? 0 : codeAPE.hashCode());
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
		Activite other = (Activite) obj;
		if (codeAPE == null) {
			if (other.codeAPE != null)
				return false;
		} else if (!codeAPE.equals(other.codeAPE))
			return false;
		return true;
	}

	
	
	
	
	
	
	

}
