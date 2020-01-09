package com.nsa.approvedcomment.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.nsa.approvedcomment.TemplateResponseEntity;
import com.nsa.approvedcomment.model.Comment;
import com.nsa.approvedcomment.repo.CommentRepository;

@RestController
//@EnableWebSecurity
@CrossOrigin
public class CommentController {

	@Autowired
	CommentRepository commentRepository;

	//svi mogu da gledaju odobrene komentare
	


	
	
	//@Secured({"ROLE_SOMEBODY"})
	//@PreAuthorize("hasIpAddress('123.0.0.1')")
	@RequestMapping(value = { "/comment", "/komentar" }, method = RequestMethod.GET)
	public ResponseEntity<Iterable<Comment>> getAll() {

		return new ResponseEntity(commentRepository.findAll(), HttpStatus.OK);
	}

	//svi mogu da gledaju odobrene komentare
	//@Secured({"ROLE_ADMIN"})
	//@PreAuthorize("hasIpAddress('137.0.0.2')")
	@RequestMapping(value = { "/comment/{id}", "/komentar/{id}" }, method = RequestMethod.GET)
	public ResponseEntity<Comment> get(@PathVariable(value = "id") Integer id) {
		return new ResponseEntity(commentRepository.findById(id), HttpStatus.OK);
	}

	// komentari mogu biti dodati samo sa odredjene IP adrese 
	@RequestMapping(value = { "/comment", "/komentar" }, method = RequestMethod.POST)
	public ResponseEntity<Comment> add(Comment comment) {

		return new ResponseEntity(commentRepository.save(comment), HttpStatus.OK);
	}

	// komentari mogu biti osvezenni samo sa odredjene IP adrese
	@RequestMapping(value = { "/comment", "/komentar" }, method = RequestMethod.PUT)
	public ResponseEntity<Comment> update(Comment comment) {

		return new ResponseEntity(commentRepository.save(comment), HttpStatus.OK);
	}

	//komentari mogu biti brisani samo sa odredjene IP adrese
	// @Secured({"ROLE_ADMIN"})
	@RequestMapping(value = { "/comment/{id}", "komentar/{id}" }, method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable(value = "id") Integer id) {

		try {
			commentRepository.deleteById(id);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity("sve je u redu", HttpStatus.OK);
	}

	// svi mogu da vide komentare za datu vest
	@RequestMapping(value = { "/comment/news/{id}", "/komentar/vest/{id}" }, method = RequestMethod.GET)
	public ResponseEntity<Iterable<Comment>> getAllCommentForGivenNews(@PathVariable(value = "id") Integer newsID) {
		return new ResponseEntity(commentRepository.findByNewsID(newsID), HttpStatus.OK);
	}

	//komentari mogu biti odobreni samo sa odredjene IP adrese
	@RequestMapping(value = { "/comment-approved", "/komentar-odobren" }, method = RequestMethod.POST)
	public HttpStatus approveComment(@RequestBody Comment comment) {

		try {
			commentRepository.save(comment);
			return HttpStatus.OK;
		} catch (Exception e) {
			return HttpStatus.NO_CONTENT;
		}

	}

}