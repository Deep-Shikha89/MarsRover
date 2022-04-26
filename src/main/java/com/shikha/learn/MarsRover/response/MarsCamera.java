package com.shikha.learn.MarsRover.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MarsCamera {
	private Long id;
	private String name;
	
	@JsonProperty("rover_id") //json used because of underscore 2 property
	private Long roverId;
	
	@JsonProperty("full_name")
	private String fullName;
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
	public Long getRover_id() {
		return roverId;
	}
	public void setRover_id(Long rover_id) {
		this.roverId = rover_id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

}
