package com.nsa.wisethought.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nsa.wisethought.model.AuthUserDetails;
import com.nsa.wisethought.model.WiseThought;
import com.nsa.wisethought.repo.WiseThoughtRepository;

import javafx.util.Pair;

@RestController
@CrossOrigin
public class WiseThoughtController {

	@Autowired
	WiseThoughtRepository whisethoughts;

	// svi mogu da vide sve reklame
	@RequestMapping(value = { "/thought", "/misao" }, method = RequestMethod.GET)
	public ResponseEntity<Iterable<WiseThought>> getAll() {

		return new ResponseEntity(whisethoughts.findAll(), HttpStatus.OK);
	}

	// svako moze da vidi reklamu
	@RequestMapping(value = { "/thought/{id}", "/misao/{id}" }, method = RequestMethod.GET)
	public ResponseEntity<WiseThought> get(@PathVariable(value = "id") Integer id) {
		return new ResponseEntity(whisethoughts.findById(id), HttpStatus.OK);
	}

	// samo admin moze da dodaje reklame
	@Secured({ "ROLE_ADMIN" })
	@Transactional
	@RequestMapping(value = { "/thought", "/misao" }, method = RequestMethod.POST)
	public ResponseEntity<WiseThought> add(Authentication authentication, WiseThought wisethought)
			throws IllegalStateException, IOException {

		// admin koji je poslao reklamu
		AuthUserDetails userDetails = (AuthUserDetails) (authentication.getPrincipal());
		Integer adminID = userDetails.getAdminID();
		wisethought.setAdminID(adminID);
		wisethought.setWisethoughtID(-1);
		
		WiseThought savedwisethought = whisethoughts.save(wisethought);
		wisethought.getImg().transferTo(Paths.get(
				System.getProperty("user.dir") + "/src/main/resources/static/" + savedwisethought.getWisethoughtID()));

		return new ResponseEntity(savedwisethought, HttpStatus.OK);
	}

	// samo admin moze da menja reklamu
	@Secured({ "ROLE_ADMIN" })
	@Transactional
	@RequestMapping(value = { "/thought", "/misao" }, method = RequestMethod.PUT)
	public ResponseEntity<WiseThought> update(Authentication authentication, WiseThought wisethought)
			throws IllegalStateException, IOException {

		// author koji je promenio vest
		AuthUserDetails userDetails = (AuthUserDetails) (authentication.getPrincipal());
		Integer adminID = userDetails.getAdminID();
		wisethought.setAdminID(adminID);

		// promenjenje vest imaju status neodobrene
		WiseThought savedwisethought = whisethoughts.save(wisethought);
		wisethought.getImg().transferTo(Paths.get(
				System.getProperty("user.dir") + "/src/main/resources/static/" + savedwisethought.getWisethoughtID()));

		return new ResponseEntity(savedwisethought, HttpStatus.OK);
	}

	// samo admin moze da menja reklamu
	@Secured({ "ROLE_ADMIN" })
	@Transactional
	@RequestMapping(value = { "/thought/{id}", "/misao/{id}" }, method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable(value = "id") Integer id) {

		try {
			whisethoughts.deleteById(id);
			File img = new File(System.getProperty("user.dir") + "/src/main/resources/static/" + id);
			img.delete();
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity("sve je u redu", HttpStatus.OK);
	}

}
