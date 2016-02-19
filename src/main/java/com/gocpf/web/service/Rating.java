package com.gocpf.web.service;

import java.io.Serializable;

public class Rating implements Serializable {
	
	private String author;
	private String comment;
	private int star;
	
	public Rating() {
	}
	
	
	public Rating(String author, String comment, int star) {
		this.author = author;
		this.comment = comment;
		this.star = star;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getStar() {
		return star;
	}
	public void setStar(int star) {
		this.star = star;
	}

}
