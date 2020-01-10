package com.nsa.demo.penningapprovalcomments.controllers;

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

import com.nsa.demo.penningapprovalcomments.PairPackage;
import com.nsa.demo.penningapprovalcomments.TemplateResponseEntity;
import com.nsa.demo.penningapprovalcomments.model.AuthUserDetails;
import com.nsa.demo.penningapprovalcomments.model.Comment;
import com.nsa.demo.penningapprovalcomments.repo.CommentRepository;


/* 
 * Svrha ovog kontrolera je da se koristi kao proxy za objavnjenje komentare 
 */
@RestController
@EnableWebSecurity
@CrossOrigin
public class JumpCommentController {

	
	static private String targetServer = "http://localhost:9090/target/comment";
	
	@Autowired
	CommentRepository commentRepository;
	
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = { "/jump/comment", "/skok/komentar" }, method = RequestMethod.PUT)
	public void update(Authentication authentication,Comment comment) {

		AuthUserDetails userDetails = (AuthUserDetails) (authentication.getPrincipal());
		Integer adminID = userDetails.getAdminID();
		comment.setAdminID(adminID);
		RestTemplate template = new RestTemplate();
		template.put(targetServer,comment);
			
	}
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = { "/jump/comment", "/skok/komentar" }, method = RequestMethod.POST)
	public ResponseEntity<Comment> add(Authentication authentication,Integer id) {

		Comment comment = commentRepository.findById(id).get();
		if(comment == null)
			return new ResponseEntity<Comment>(comment, HttpStatus.NO_CONTENT);
		
		AuthUserDetails userDetails = (AuthUserDetails) (authentication.getPrincipal());
		Integer adminID = userDetails.getAdminID();
		comment.setAdminID(adminID);
		RestTemplate template = new RestTemplate();
		PairPackage<Comment,HttpStatus> response = template.postForObject(targetServer,comment, PairPackage.class);
		
		if(response.getValue2() == HttpStatus.OK)
		commentRepository.deleteById(id);
		
		System.out.println(response.getValue2());
		System.out.flush();
		return new ResponseEntity(response.getValue1(),response.getValue2());
	}
	
	@RequestMapping(value = { "/jump/comment/{id}", "/skok/komentar/{id}" }, method = RequestMethod.DELETE)
	public void delete(@PathVariable(value = "id") Integer id) {

		RestTemplate template = new RestTemplate();
		
		template.delete(targetServer+"/"+id.intValue());
		
	}


}
