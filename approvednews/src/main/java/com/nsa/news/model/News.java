package com.nsa.news.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//import org.springframework.data.annotation.Transient;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nsa.news.utils.NewsState;




enum NewsCategory {
	  SPORT,
	  RAZONODA,
	  VREME,
	  SAOBRACAJ,
	  DRUSTVO
	}

@Entity
public class News {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer newsID;

	@Column(insertable = false, updatable = false, columnDefinition = "DATETIME on update CURRENT_TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP", nullable = false)
	private LocalDateTime date;

	@Column(nullable = false, updatable = true, insertable = true, length = 20000)
	private String text;

	//admin koji je odobrio vest
	@Column(nullable = true, updatable = true, insertable = true)
	private Integer adminID;
	
	@Column(nullable = true, updatable=true, insertable = true)
	private NewsCategory newsCategory;
	
	@Column(nullable = false, updatable = true, insertable = true, length = 50)
	private String title;
	
	@Column(nullable = false, updatable = true, insertable = true, length = 300)
	private String description; 
	
	//autor koje je poslao vest
	@Column(nullable = false, updatable = true, insertable = true, length = 50)
	private Integer author; 
	
	@Column(nullable = false, updatable = true, insertable = true)
	private NewsState newsState; 


	@javax.persistence.Transient
	@JsonIgnore
	private MultipartFile img;
	
	protected News() {}

	
	public News(LocalDateTime date, String text, Integer adminID,
			NewsCategory newsCategory, String title, 
			String description, Integer author, NewsState newsState) {
		super();
		this.date = date;
		this.text = text;
		this.adminID = adminID; 
		this.newsCategory = newsCategory; 
		this.title = title;
		this.description = description;
		this.author = author;
		this.newsState = newsState;
		
	}

	public NewsCategory getNewsCategory() {
		return newsCategory;
	}


	public void setNewsCategory(NewsCategory newsCategory) {
		this.newsCategory = newsCategory;
	}


	public Integer getNewsID() {
		return newsID;
	}

	public void setNewsID(Integer newsID) {
		this.newsID = newsID;
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


	public Integer getAuthor() {
		return author;
	}


	public void setAuthor(Integer author) {
		this.author = author;
	}


	public NewsState getNewsState() {
		return newsState;
	}


	public void setNewsState(NewsState newsState) {
		this.newsState = newsState;
	};
	
	

}
