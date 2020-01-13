package com.nsa.commercial.controllers;

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

import com.nsa.commercial.model.AuthUserDetails;
import com.nsa.commercial.model.Commercial;
import com.nsa.commercial.repo.CommercialRepository;
import com.nsa.commercial.utils.NewsState;

import javafx.util.Pair;

@RestController
@CrossOrigin
public class CommercialController {

	@Autowired
	CommercialRepository commercialRepository;

	// svi mogu da vide sve reklame
	@RequestMapping(value = { "/commercial", "/reklama" }, method = RequestMethod.GET)
	public ResponseEntity<Iterable<Commercial>> getAll() {

		return new ResponseEntity(commercialRepository.findAll(), HttpStatus.OK);
	}

	// svako moze da vidi reklamu
	@RequestMapping(value = { "/commercial/{id}", "/reklama/{id}" }, method = RequestMethod.GET)
	public ResponseEntity<Commercial> get(@PathVariable(value = "id") Integer id) {
		return new ResponseEntity(commercialRepository.findById(id), HttpStatus.OK);
	}

	// samo admin moze da dodaje reklame
	@Secured({ "ROLE_ADMIN" })
	@Transactional
	@RequestMapping(value = { "/commercial", "/reklama" }, method = RequestMethod.POST)
	public ResponseEntity<Commercial> add(Authentication authentication, Commercial commercial)
			throws IllegalStateException, IOException {

		// admin koji je poslao reklamu
		AuthUserDetails userDetails = (AuthUserDetails) (authentication.getPrincipal());
		Integer adminID = userDetails.getAdminID();
		commercial.setAdminID(adminID);

		Commercial savedcommercial = commercialRepository.save(commercial);
		commercial.getImg().transferTo(Paths.get(
				System.getProperty("user.dir") + "/src/main/resources/static/" + savedcommercial.getCommercialID()));

		return new ResponseEntity(savedcommercial, HttpStatus.OK);
	}

	// samo admin moze da menja reklamu
	@Secured({ "ROLE_ADMIN" })
	@Transactional
	@RequestMapping(value = { "/commercial", "/reklama" }, method = RequestMethod.PUT)
	public ResponseEntity<Commercial> update(Authentication authentication, Commercial commercial)
			throws IllegalStateException, IOException {

		// author koji je promenio vest
		AuthUserDetails userDetails = (AuthUserDetails) (authentication.getPrincipal());
		Integer adminID = userDetails.getAdminID();
		commercial.setAdminID(adminID);

		// promenjenje vest imaju status neodobrene
		Commercial savedcommercial = commercialRepository.save(commercial);
		commercial.getImg().transferTo(Paths.get(
				System.getProperty("user.dir") + "/src/main/resources/static/" + savedcommercial.getCommercialID()));

		return new ResponseEntity(savedcommercial, HttpStatus.OK);
	}

	// samo admin moze da menja reklamu
	@Secured({ "ROLE_ADMIN" })
	@Transactional
	@RequestMapping(value = { "/commercial/{id}", "/reklama/{id}" }, method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable(value = "id") Integer id) {

		try {
			commercialRepository.deleteById(id);
			File img = new File(System.getProperty("user.dir") + "/src/main/resources/static/" + id);
			img.delete();
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity("sve je u redu", HttpStatus.OK);
	}

}
