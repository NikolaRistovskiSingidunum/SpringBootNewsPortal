package com.nsa.approvednews.controllers;

import java.io.File;
import java.util.ArrayList;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nsa.approvednews.PairPackage;
import com.nsa.approvednews.model.News;
import com.nsa.approvednews.repo.NewsRepository;

import javafx.util.Pair;

@RestController
@CrossOrigin
public class NewsController {

	@Autowired
	NewsRepository newsRepository;
	
	@RequestMapping(value = { "/news", "/vest" }, method = RequestMethod.GET)
	public ResponseEntity<Iterable<News>> getAll() {

		return new ResponseEntity(newsRepository.findAll(), HttpStatus.OK);
	}
	
	// samo admin moze da gleda komentare u recenziji
	@RequestMapping(value = { "/news/{id}", "/vest/{id}" }, method = RequestMethod.GET)
	public ResponseEntity<News> get(@PathVariable(value = "id") Integer id) {
		return new ResponseEntity(newsRepository.findById(id), HttpStatus.OK);
	}
	// svako moze da dodaje komentare
	@RequestMapping(value = { "/news", "/vest" }, method = RequestMethod.POST)
	public ResponseEntity<News> add(News news) {

		return new ResponseEntity(newsRepository.save(news), HttpStatus.OK);
	}
	// ova opcija je samo formalna, ne slaze sa logikom aplikacije
	@RequestMapping(value = { "/news", "/vest" }, method = RequestMethod.PUT)
	public ResponseEntity<News> update(News news) {

		return new ResponseEntity(newsRepository.save(news), HttpStatus.OK);
	}
	
	// samo admin moze da brise komentare
	// @Secured({"ROLE_ADMIN"})
	@RequestMapping(value = { "/news/{id}", "/vest/{id}" }, method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable(value = "id") Integer id) {

		try {
			newsRepository.deleteById(id);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity("sve je u redu", HttpStatus.OK);
	}
	
	@RequestMapping(value = { "/news-approved", "/vest-odobrena" }, method = RequestMethod.POST)
	public HttpStatus approveNews(@RequestBody PairPackage<News, File> pack) {

		try {
			News save = newsRepository.save(pack.getValue1());
			File saveimg = new File(System.getProperty("user.dir")+"/src/main/resources/static/"+save.getNewsID());
			FileCopyUtils.copy(pack.getValue2(), saveimg);
			return HttpStatus.OK;
		} catch (Exception e) {
			System.out.println(e);
			return HttpStatus.NO_CONTENT;
		}

	}

}
