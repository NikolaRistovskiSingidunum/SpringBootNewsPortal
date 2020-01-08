package com.nsa.approvednews;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public class TemplateResponseEntity<T> extends ResponseEntity<T> {

	public TemplateResponseEntity() {
		super(HttpStatus.OK);
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
	}


	
	

}
