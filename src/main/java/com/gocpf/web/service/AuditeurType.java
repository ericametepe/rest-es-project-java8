package com.gocpf.web.service;

public enum AuditeurType {
	SALARIE("1"),
	DE("2"),
	ALL("3");
	
	private String id;
	
	private AuditeurType(String id) {
		this.id=id;
	}
	
	public String getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return this.name()+ " "+ this.id;
	}
	

}
