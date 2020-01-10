package com.nsa.demo.penningapprovalcomments.controllers;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.nsa.demo.penningapprovalcomments.TemplateResponseEntity;
import com.nsa.demo.penningapprovalcomments.model.AuthUserDetails;
import com.nsa.demo.penningapprovalcomments.model.Comment;
import com.nsa.demo.penningapprovalcomments.repo.CommentRepository;

@RestController
@EnableWebSecurity
@CrossOrigin
public class CommentController {

	@Autowired
	CommentRepository commentRepository;

	// samo admin moze da gleda komentare u recenziji
	// @Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = { "/comment", "/komentar" }, method = RequestMethod.GET)
	public ResponseEntity<Iterable<Comment>> getAll() {

		return new ResponseEntity(commentRepository.findAll(), HttpStatus.OK);
		//return new ResponseEntity(commentRepository.findAll(), HttpStatus.OK);
	}

	// samo admin moze da gleda komentare u recenziji
	@RequestMapping(value = { "/comment/{id}", "/komentar/{id}" }, method = RequestMethod.GET)
	public ResponseEntity<Comment> get(@PathVariable(value = "id") Integer id) {
		return new ResponseEntity(commentRepository.findById(id), HttpStatus.OK);
	}

	// svako moze da dodaje komentare
	@RequestMapping(value = { "/comment", "/komentar" }, method = RequestMethod.POST)
	public ResponseEntity<Comment> add(Comment comment) {

		return new ResponseEntity(commentRepository.save(comment), HttpStatus.OK);
	}

	// ova opcija je samo formalna, ne slaze sa logikom aplikacije
	@RequestMapping(value = { "/comment", "/komentar" }, method = RequestMethod.PUT)
	public ResponseEntity<Comment> update(Comment comment) {

		return new ResponseEntity(commentRepository.save(comment), HttpStatus.OK);
	}

	// samo admin moze da brise komentare
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = { "/comment/{id}", "komentar/{id}" }, method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable(value = "id") Integer id) {

		try {
			commentRepository.deleteById(id);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity("sve je u redu", HttpStatus.OK);
	}

	// samo admin moze da vidi komentare u recenziji za datu vest
	@RequestMapping(value = { "/comment/news/{id}", "/komentar/vest/{id}" }, method = RequestMethod.GET)
	public ResponseEntity<Iterable<Comment>> getAllCommentForGivenNews(@PathVariable(value = "id") Integer newsID) {
		return new ResponseEntity(commentRepository.findByNewsID(newsID), HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = { "/approve-comment/{id}", "/odobri-komentar/{id}" }, method = RequestMethod.GET)
	public ResponseEntity<String> approveComment(Authentication authentication,
			@PathVariable(value = "id") Integer id) {

		AuthUserDetails userDetails = (AuthUserDetails) (authentication.getPrincipal());
		Integer adminID = userDetails.getAdminID();

		// http://localhost:9090/home
		Comment comment = null;
		try {
			Optional<Comment> c = commentRepository.findById(id);
			comment = c.get();
		} catch (Exception e) {
			return new ResponseEntity<String>("nema takvog komentara", HttpStatus.GONE);
		}

		if (comment != null) {
			//admin koji je odobrio komentar
			comment.setAdminID(adminID);
			RestTemplate template = new RestTemplate();
			HttpStatus status;
			//TemplateResponseEntity response;
			status = template.postForObject("http://localhost:9090/comment-approved", comment, HttpStatus.class);
			if (status == HttpStatus.OK) {
				try {
					commentRepository.deleteById(id);
					return new ResponseEntity<String>("sve je ok", HttpStatus.OK);
				} catch (Exception e) {
					return new ResponseEntity<String>("nema takvog komentara", HttpStatus.GONE);
				}

			} else {
				return new ResponseEntity<String>("sve je ok", HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<String>("nema takvog komentara", HttpStatus.GONE);
		}

	}
	
	@RequestMapping(value = { "/test/{id}", "/a/{id}" }, method = RequestMethod.GET)
	public ResponseEntity<Comment> rere(@PathVariable(value = "id") Integer id) {

		RestTemplate template = new RestTemplate();
		Comment comment = commentRepository.findById(id).get();
		comment.setAdminID(3);
		
		TemplateResponseEntity<Comment> resposne = 
				template.postForObject("http://localhost:9090/comment", comment, TemplateResponseEntity.class);
		
		
		return new TemplateResponseEntity(comment, HttpStatus.OK);
	}
	


}
