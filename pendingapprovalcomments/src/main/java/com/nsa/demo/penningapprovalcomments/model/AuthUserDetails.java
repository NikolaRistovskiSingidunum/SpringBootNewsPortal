package com.nsa.demo.penningapprovalcomments.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicReferenceArray;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.nsa.demo.penningapprovalcomments.repo.AuthUserDetailsRepository;


public class AuthUserDetails implements UserDetails {

	
	private String username;
	
	private String password;
	
	private String role;
	
	private Integer adminID;
	
	public AuthUserDetails(String username) {
		super();
		this.username = username;
	}

	
	public AuthUserDetails(AdminDetails adminDetails)
	{
		this.username=adminDetails.getUsername();
		this.password = adminDetails.getPassword();
		this.role = adminDetails.getRole();
		this.adminID  = adminDetails.getId();
	}
	
	public Integer getAdminID() {
		return adminID;
	}


	public void setAdminID(Integer adminID) {
		this.adminID = adminID;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return Arrays.asList(new SimpleGrantedAuthority(role));
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	

}
