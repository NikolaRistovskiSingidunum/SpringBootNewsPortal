package com.nsa.comment.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nsa.comment.model.Comment;
import com.nsa.comment.repo.CommentRepository;
import com.nsa.comment.utils.PairPackage;
import com.nsa.comment.utils.TemplateResponseEntity;

@RestController
@CrossOrigin
public class TargetCommentController {

	@Autowired
	CommentRepository commentRepository;
	
//	@RequestMapping(value = { "/terget/comment", "/cilj/komentar" }, method = RequestMethod.GET)
//	public PairPackage<Iterable<Comment>,HttpStatus> getAll() {
//
//		return new PairPackage(commentRepository.findAll(), HttpStatus.OK);
//	}
	
	@RequestMapping(value = { "/target/comment/{id}", "/cilj/komentar/{id}" }, method = RequestMethod.GET)
	public PairPackage<Comment,HttpStatus> get(@PathVariable(value = "id") Integer id) {
		return new PairPackage(commentRepository.findById(id), HttpStatus.OK);
	}
	
	@RequestMapping(value = { "/target/comment", "/cilj/komentar" }, method = RequestMethod.POST)
	public PairPackage<Comment, HttpStatus> add(@RequestBody Comment comment) {

		return new PairPackage(commentRepository.save(comment), HttpStatus.OK);
	}
	
	@RequestMapping(value = { "/target/comment", "/cilj/komentar" }, method = RequestMethod.PUT)
	public void update(@RequestBody Comment comment) {

		commentRepository.save(comment);

	}

	@RequestMapping(value = { "/target/comment/{id}", "/cilj/komentar/{id}" }, method = RequestMethod.DELETE)
	public void delete(@PathVariable(value = "id") Integer id) {

		commentRepository.deleteById(id);
	}
}
