package com.garoto.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Event {
	@Id @GeneratedValue
	private Long id;
	
	private String name;
	
	private LocalDate createDate;
	
	private LocalDate lastUpdate;
	
	@ManyToOne
	private User allowedUser;
	
	public Event(String name, LocalDate createDate, LocalDate executionDate,
			User allowedUser) {
		super();
		this.name = name;
		this.createDate = createDate;
		this.setLastUpdate(executionDate);
		this.allowedUser = allowedUser;
	}
	
	public Event () {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

	public LocalDate getExecutionDate() {
		return getLastUpdate();
	}

	public void setExecutionDate(LocalDate executionDate) {
		this.setLastUpdate(executionDate);
	}

	public User getAllowedUser() {
		return allowedUser;
	}

	public void setAllowedUser(User allowedUser) {
		this.allowedUser = allowedUser;
	}

	public LocalDate getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(LocalDate lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	
	
}
