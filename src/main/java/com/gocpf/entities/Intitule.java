package com.gocpf.entities;

public class Intitule {
	
	private String originalText;
	private String originalTextSansAccent;
	
	public Intitule() {
	}
	
	public Intitule(String originalText, String originalTextSansAccent) {
		this.originalText = originalText;
		this.originalTextSansAccent = originalTextSansAccent;
	}

	public String getOriginalText() {
		return originalText;
	}

	public void setOriginalText(String originalText) {
		this.originalText = originalText;
	}

	public String getOriginalTextSansAccent() {
		return originalTextSansAccent;
	}

	public void setOriginalTextSansAccent(String originalTextSansAccent) {
		this.originalTextSansAccent = originalTextSansAccent;
	}

	@Override
	public String toString() {
		return "Intitule [originalText=" + originalText + ", originalTextSansAccent=" + originalTextSansAccent + "]";
	}


}
