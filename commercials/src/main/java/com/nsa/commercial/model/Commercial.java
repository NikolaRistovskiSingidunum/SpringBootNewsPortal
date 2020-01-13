package com.nsa.commercial.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//import org.springframework.data.annotation.Transient;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nsa.commercial.utils.NewsState;

@Entity
public class Commercial {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer commercialID;

	@Column(insertable = false, updatable = false, columnDefinition = "DATETIME on update CURRENT_TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP", nullable = false)
	private LocalDateTime date;

	@Column(nullable = false, updatable = true, insertable = true, length = 20000)
	private String text;

	// admin koji je odobrio vest
	@Column(nullable = false, updatable = true, insertable = true)
	private Integer adminID;

	@Column(nullable = false, updatable = true, insertable = true, length = 50)
	private String title;

	@Column(nullable = false, updatable = true, insertable = true, length = 300)
	private String description;

	@javax.persistence.Transient
	@JsonIgnore
	private MultipartFile img;

	protected Commercial() {
	}

	public Commercial(Integer newsID, LocalDateTime date, String text, Integer adminID, String title,
			String description, MultipartFile img) {
		super();
		this.commercialID = newsID;
		this.date = date;
		this.text = text;
		this.adminID = adminID;
		this.title = title;
		this.description = description;
		this.img = img;
	}

	

	public Integer getCommercialID() {
		return commercialID;
	}

	public void setCommercialID(Integer commercialID) {
		this.commercialID = commercialID;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getAdminID() {
		return adminID;
	}

	public void setAdminID(Integer adminID) {
		this.adminID = adminID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MultipartFile getImg() {
		return img;
	}

	public void setImg(MultipartFile img) {
		this.img = img;
	}

}
