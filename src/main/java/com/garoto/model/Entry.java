package com.garoto.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Entry {
	@Id @GeneratedValue
	private Long id;
	
	private String chocolate;
	
	private LocalDate date;
	
	public Entry () {
		
	}	

	public Entry(String chocolate) {
		super();
		this.chocolate = chocolate;
		this.date = LocalDate.now();
	}

	public String getChocolate() {
		return chocolate;
	}

	public void setChocolate(String chocolate) {
		this.chocolate = chocolate.toUpperCase();
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
		
	
	
}
