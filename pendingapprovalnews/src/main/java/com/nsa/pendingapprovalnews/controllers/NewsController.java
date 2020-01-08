package com.nsa.pendingapprovalnews.controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.nsa.pendingapprovalnews.PairPackage;
import com.nsa.pendingapprovalnews.model.AuthUserDetails;
import com.nsa.pendingapprovalnews.model.News;
import com.nsa.pendingapprovalnews.repo.NewsRepository;

import javafx.util.Pair;

@RestController
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
	// svako moze da dodaje vesti
	@RequestMapping(value = { "/news", "/vest" }, method = RequestMethod.POST)
	public ResponseEntity<News> add(News news) throws IOException {

		  News savednews = newsRepository.save(news);
		  news.getImg().transferTo(Paths.get(System.getProperty("user.dir")+"/src/main/resources/static/"+savednews.getNewsID()));		
		  
		  //System.out.println(Paths.get(System.getProperty("user.dir")+"aa"));
		return new ResponseEntity(savednews, HttpStatus.OK);
	}
	// admin moza da menja vest koja ceka odbrenje
	@RequestMapping(value = { "/news", "/vest" }, method = RequestMethod.PUT)
	public ResponseEntity<News> update(News news) throws IllegalStateException, IOException {

		 News updatednews = newsRepository.save(news);
		 news.getImg().transferTo(Paths.get(System.getProperty("user.dir")+"/src/main/resources/static/"+updatednews.getNewsID()));		
		 
		
		return new ResponseEntity(updatednews, HttpStatus.OK);
	}
	
	// samo admin moze da brise komentare
	// @Secured({"ROLE_ADMIN"})
	@RequestMapping(value = { "/news/{id}", "/vest/{id}" }, method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable(value = "id") Integer id) {

		try {
			newsRepository.deleteById(id);
			File img = new File(System.getProperty("user.dir")+"/src/main/resources/static/"+id);
			img.delete();
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity("sve je u redu", HttpStatus.OK);
	}
	
//	@RequestMapping(value = { "/news-approved", "/vest-odobrena" }, method = RequestMethod.POST)
//	public HttpStatus approveNews(@RequestBody News news) {
//
//		try {
//			newsRepository.save(news);
//			return HttpStatus.OK;
//		} catch (Exception e) {
//			return HttpStatus.NO_CONTENT;
//		}
//
//	}
	
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = { "/approve-news/{id}", "/odobri-vest/{id}" }, method = RequestMethod.GET)
	public ResponseEntity<String> approveNews(Authentication authentication,
			@PathVariable(value = "id") Integer id) {

		AuthUserDetails userDetails = (AuthUserDetails) (authentication.getPrincipal());
		Integer adminID = userDetails.getAdminID();

		// http://localhost:9090/home
		News news = null;
		try {
			Optional<News> n = newsRepository.findById(id);
			news = n.get(); 
		} catch (Exception e) {
			return new ResponseEntity<String>("nema takvog komentara", HttpStatus.GONE);
		}

		if (news != null) {
			//admin koji je odobrio komentar
			news.setAdminID(adminID);
			RestTemplate template = new RestTemplate();
			HttpStatus status;
			File img = new File(System.getProperty("user.dir")+"/src/main/resources/static/"+news.getNewsID());
			ArrayList<Object> list= new ArrayList<>();
			list.add(news);
			list.add(img);
			status = template.postForObject("http://localhost:9092/news-approved", new PairPackage(news, img), HttpStatus.class);
			if (status == HttpStatus.OK) {
				try {
					newsRepository.deleteById(id);
					return new ResponseEntity<String>("sve je ok", HttpStatus.OK);
				} catch (Exception e) {
					return new ResponseEntity<String>("nema takvog komentara", HttpStatus.GONE);
				}

			} else {
				return new ResponseEntity<String>("nije ok, server ne moze da skladisti vest", HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<String>("nema takvog komentara", HttpStatus.GONE);
		}

	}

}
