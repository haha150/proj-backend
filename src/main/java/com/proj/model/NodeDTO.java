package com.proj.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NodeDTO {

	private Long id;
	private String name;
	private String responsible;
	private String status;
	private String bookedUntil;
	private String bookedBy;
	private String comments;

	@JsonCreator
	public NodeDTO(@JsonProperty("id") Long id, @JsonProperty("name") String name,
			@JsonProperty("responsible") String responsible,
			@JsonProperty("status") String status, @JsonProperty("bookedUntil") String bookedUntil,
			@JsonProperty("bookedBy") String bookedBy, @JsonProperty("comments") String comments) {
		this.id = id;
		this.name = name;
		this.responsible = responsible;
		this.status = status;
		this.bookedUntil = bookedUntil;
		this.bookedBy = bookedBy;
		this.comments = comments;
	}

	public NodeDTO() {
	}

	public Long getId() {
		return id;
	}

	public String getBookedUntil() {
		return bookedUntil;
	}

	public void setBookedUntil(String bookedUntil) {
		this.bookedUntil = bookedUntil;
	}

	public String getBookedBy() {
		return bookedBy;
	}

	public void setBookedBy(String bookedBy) {
		this.bookedBy = bookedBy;
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

	public String getResponsible() {
		return responsible;
	}

	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}
