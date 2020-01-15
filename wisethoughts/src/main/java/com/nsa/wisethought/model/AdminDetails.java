package com.nsa.wisethought.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Entity
public class AdminDetails {


	
	@Bean
	public BCryptPasswordEncoder encoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	
	@Column(nullable=false,length=64)
	private String username;
	
	@Column(nullable=false,insertable=true, updatable=true)
	private String password;
	
	@Column(nullable=false, length=12)
	private String role;
	
	
	protected AdminDetails() {}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}





	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public AdminDetails(String username, String password, String role) {
		super();
		
		this.username = username;
		this.password = password;
		this.role = role;
	};
	
	
	public AdminDetails encode()
	{
		this.password = encoder().encode(this.password);
		return this;
	}
	
	
	
}
