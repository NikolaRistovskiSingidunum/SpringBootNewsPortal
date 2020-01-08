package com.nsa.approvednews.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nsa.approvednews.PairPackage;
import com.nsa.approvednews.TemplateResponseEntity;
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

		return new TemplateResponseEntity(newsRepository.findAll(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value = { "/news/{id}", "/vest/{id}" }, method = RequestMethod.GET)
	public ResponseEntity<News> get(@PathVariable(value = "id") Integer id) {
		return new TemplateResponseEntity(newsRepository.findById(id), HttpStatus.OK);
	}
	// 
	@Transactional
	@RequestMapping(value = { "/news", "/vest" }, method = RequestMethod.POST)
	public ResponseEntity<News> add(News news) throws IllegalStateException, IOException {

		  News savednews = newsRepository.save(news);
		  news
		  .getImg()
		  .transferTo(Paths.get(System.getProperty("user.dir")+"/src/main/resources/static/"+savednews.getNewsID()));		
		  
		
		return new TemplateResponseEntity(savednews, HttpStatus.OK);
	}
	// ova opcija je samo formalna, ne slaze sa logikom aplikacije
	@Transactional
	@RequestMapping(value = { "/news", "/vest" }, method = RequestMethod.PUT)
	public ResponseEntity<News> update(News news) throws IllegalStateException, IOException {

		News savednews = newsRepository.save(news);
		  news
		  .getImg()
		  .transferTo(Paths.get(System.getProperty("user.dir")+"/src/main/resources/static/"+savednews.getNewsID()));		
		  
		
		return new TemplateResponseEntity(savednews, HttpStatus.OK);
	}
	
	// samo admin moze da brise komentare
	// @Secured({"ROLE_ADMIN"})
	@Transactional
	@RequestMapping(value = { "/news/{id}", "/vest/{id}" }, method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable(value = "id") Integer id) {

		try {
			newsRepository.deleteById(id);
			File img = new File(System.getProperty("user.dir")+"/src/main/resources/static/"+id);
			img.delete();
		} catch (Exception e) {
			return new TemplateResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new TemplateResponseEntity("sve je u redu", HttpStatus.OK);
	}
	@Transactional
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
