package com.proj.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Node")
@Table(name = "nodes")
public class Node {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name", unique = true, length = 20, nullable = false)
	private String name;

	@Column(name = "responsible", length = 20, nullable = false)
	private String responsible;

	@Column(name = "status", length = 20, nullable = false)
	private String status;

	@Column(name = "bookedUntil", length = 20)
	private String bookedUntil;

	@Column(name = "bookedBy", length = 50)
	private String bookedBy;

	@Column(name = "comments", length = 200)
	private String comments;

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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}
