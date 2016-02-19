
package com.gocpf.entities;

import java.io.Serializable;
import java.util.Date;

public class Session implements Serializable {

	private String code;
	private String description;
	private Date debutDate;
	private Date finDate;


	public Session(String code, String description, Date debutDate, Date finDate) {
		super();
		this.code = code;
		this.description = description;
		this.debutDate = debutDate;
		this.finDate = finDate;
	}




	public Session() {
	}


	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public String getCode() {
		return code;
	}




	public void setCode(String code) {
		this.code = code;
	}




	public Date getDebutDate() {
		return debutDate;
	}




	public void setDebutDate(Date debutDate) {
		this.debutDate = debutDate;
	}




	public Date getFinDate() {
		return finDate;
	}




	public void setFinDate(Date finDate) {
		this.finDate = finDate;
	}




	public String getDescription() {
		return description;
	}




	public void setDescription(String description) {
		this.description = description;
	}

	

}
