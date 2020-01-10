package com.nsa.demo.penningapprovalcomments;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public class TemplateResponseEntity<T> extends ResponseEntity<T> implements Serializable {

	public TemplateResponseEntity() throws NoSuchFieldException, SecurityException {
		super(HttpStatus.OK);
		Field field = ResponseEntity.class.getDeclaredField("body");
		field.setAccessible(true);
		
		Field field1 = ResponseEntity.class.getDeclaredField("staus");
		field1.setAccessible(true);
		// TODO Auto-generated constructor stub
	}

	public TemplateResponseEntity(HttpStatus status) {
		super(status);
		// TODO Auto-generated constructor stub
	}

	public TemplateResponseEntity(MultiValueMap<String, String> headers, HttpStatus status) {
		super(headers, status);
		// TODO Auto-generated constructor stub
	}

	public TemplateResponseEntity(T body, HttpStatus status) {
		super(body, status);
		// TODO Auto-generated constructor stub
	}

	public TemplateResponseEntity(T body, MultiValueMap<String, String> headers, HttpStatus status) {
		super(body, headers, status);
		// TODO Auto-generated constructor stub
		
		//this.s
	}

	public void setStatus(HttpStatus status)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = ResponseEntity.class.getDeclaredField("satus");
		field.setAccessible(true);
		field.set(this, status);
		System.out.println("status se printa");
	}
	
	public void setBody(T body)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = ResponseEntity.class.getDeclaredField("body");
		field.setAccessible(true);
		field.set(this, body);
		System.out.println("body se printa");
	}

	
	

}
