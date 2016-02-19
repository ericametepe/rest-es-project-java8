package com.gocpf.service;

public class BrancheAPE {
	
	private String codeAPE;
	private String intitule;
	private String branche;
	
	public BrancheAPE() {
	}
	
	
	public String getCodeAPE() {
		return codeAPE;
	}
	public void setCodeAPE(String codeAPE) {
		this.codeAPE = codeAPE;
	}
	public String getIntitule() {
		return intitule;
	}
	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}
	public String getBranche() {
		return branche;
	}
	public void setBranche(String branche) {
		this.branche = branche;
	}


	@Override
	public String toString() {
		return "BrancheAPE [codeAPE=" + codeAPE + ", intitule=" + intitule + ", branche=" + branche + "]";
	}

}
