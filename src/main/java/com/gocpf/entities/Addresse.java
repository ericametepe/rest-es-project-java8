package com.gocpf.entities;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Addresse {
	
	@NotNull
    @Size(min=1)
	private String adresseLine1;
	private String adresseLine2;
	private String adresseLine3;
	@NotNull
	@Size(min=1)
	private String postalcode;
	@NotNull
	@Size(min=1)
	private String city;
	@NotNull
	@Size(min=1)
	private String country;
	
	
	public Addresse(String adresseLine1, String adresseLine2, String adresseLine3, String postalcode, String city,
			String country) {
		this.adresseLine1 = adresseLine1;
		this.adresseLine2 = adresseLine2;
		this.adresseLine3 = adresseLine3;
		this.postalcode = postalcode;
		this.city = city;
		this.country = country;
	}


	public Addresse() {
	}
	
	
	public String getAdresseLine1() {
		return adresseLine1;
	}
	public void setAdresseLine1(String adresseLine1) {
		this.adresseLine1 = adresseLine1;
	}
	public String getAdresseLine2() {
		return adresseLine2;
	}
	public void setAdresseLine2(String adresseLine2) {
		this.adresseLine2 = adresseLine2;
	}
	public String getAdresseLine3() {
		return adresseLine3;
	}
	public void setAdresseLine3(String adresseLine3) {
		this.adresseLine3 = adresseLine3;
	}
	public String getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}

}
