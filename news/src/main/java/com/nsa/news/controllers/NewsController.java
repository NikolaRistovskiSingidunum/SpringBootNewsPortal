package com.nsa.news.controllers;

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

import com.nsa.news.model.AuthUserDetails;
import com.nsa.news.model.News;
import com.nsa.news.repo.NewsRepository;
import com.nsa.news.utils.NewsState;
import com.nsa.news.utils.PairPackage;


import javafx.util.Pair;

@RestController
@CrossOrigin
public class NewsController {

	@Autowired
	NewsRepository newsRepository;
	
	//svi mogu da vide odobrene vesti
	@RequestMapping(value = { "/news", "/vest" }, method = RequestMethod.GET)
	public ResponseEntity<Iterable<News>> getAllNewsApproved() {

		return new ResponseEntity(newsRepository.findByNewsState(NewsState.APPROVED), HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_SUPER"})
	@RequestMapping(value = { "/admin/news-pending", "/admin/vest-nacekanju" }, method = RequestMethod.GET)
	public ResponseEntity<Iterable<News>> getAllNewsPendingApprovement() {

		return new ResponseEntity(newsRepository.findByNewsState(NewsState.PENDING), HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_SUPER"})
	@RequestMapping(value = { "/admin/news", "/admin/vest" }, method = RequestMethod.GET)
	public ResponseEntity<Iterable<News>> getAll() {

		return new ResponseEntity(newsRepository.findAll(), HttpStatus.OK);
	}
	//svako moze da vidi odobrenu vest
	@RequestMapping(value = { "/news/{id}", "/vest/{id}" }, method = RequestMethod.GET)
	public ResponseEntity<News> get(@PathVariable(value = "id") Integer id) {
		return new ResponseEntity(newsRepository.findByNewsIDAndNewsState(id, NewsState.APPROVED), HttpStatus.OK);
	}
	
	//samo admin moze da vidi odobrenu ili neodobrenu vest
	@Secured({"ROLE_ADMIN","ROLE_SUPER"})
	@RequestMapping(value = { "/admin/news/{id}", "/admin/vest/{id}" }, method = RequestMethod.GET)
	public ResponseEntity<News> getAnyNews(@PathVariable(value = "id") Integer id) {
		return new ResponseEntity(newsRepository.findById(id), HttpStatus.OK);
	}
	// 

	//samo admin moze da dodaje vesti
	@Secured({ "ROLE_ADMIN" })
	@Transactional
	@RequestMapping(value = { "/admin/news", "/admin/vest" }, method = RequestMethod.POST)
	public ResponseEntity<News> add(Authentication authentication,News news) throws IllegalStateException, IOException {

		
		//autor koji je poslao vest
		AuthUserDetails userDetails = (AuthUserDetails) (authentication.getPrincipal());
		Integer adminID = userDetails.getAdminID();
		news.setAuthor(adminID);
		
		
		//autor salje vesti koji jos nisu odobrene
		news.setNewsState(NewsState.PENDING);
		News savednews = newsRepository.save(news);
		news.getImg().transferTo(
				Paths.get(System.getProperty("user.dir") + "/src/main/resources/static/" + savednews.getNewsID()));

		return new ResponseEntity(savednews, HttpStatus.OK);
	}
	//samo admin moze da menja vest 
	@Secured({ "ROLE_ADMIN" })
	@Transactional
	@RequestMapping(value = { "/admin/news", "/admin/vest" }, method = RequestMethod.PUT)
	public ResponseEntity<News> update(Authentication authentication,News news) throws IllegalStateException, IOException {

		//author koji je promenio vest
		AuthUserDetails userDetails = (AuthUserDetails) (authentication.getPrincipal());
		Integer adminID = userDetails.getAdminID();
		news.setAuthor(adminID);
		
		//promenjenje vest imaju status neodobrene
		news.setNewsState(NewsState.PENDING);
		News savednews = newsRepository.save(news);
		  news
		  .getImg()
		  .transferTo(Paths.get(System.getProperty("user.dir")+"/src/main/resources/static/"+savednews.getNewsID()));		
		  
		
		return new ResponseEntity(savednews, HttpStatus.OK);
	}
	
	//
	@Secured({"ROLE_ADMIN","ROLE_SUPER"})
	@Transactional
	@RequestMapping(value = { "/admin/news/{id}", "/admin/vest/{id}" }, method = RequestMethod.DELETE)
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
//	@Transactional
//	@RequestMapping(value = { "/news-approved", "/vest-odobrena" }, method = RequestMethod.POST)
//	public HttpStatus approveNews(@RequestBody PairPackage<News, File> pack) {
//
//		try {
//			News save = newsRepository.save(pack.getValue1());
//			File saveimg = new File(System.getProperty("user.dir")+"/src/main/resources/static/"+save.getNewsID());
//			FileCopyUtils.copy(pack.getValue2(), saveimg);
//			return HttpStatus.OK;
//		} catch (Exception e) {
//			System.out.println(e);
//			return HttpStatus.NO_CONTENT;
//		}
//
//	}

	@Secured({ "ROLE_SUPER" })
	@Transactional
	@RequestMapping(value = { "/admin/approve-news/{id}", "/admin/odobri-vest/{id}" }, method = RequestMethod.GET)
	public ResponseEntity<News> approveNews(Authentication authentication, @PathVariable(value = "id") Integer id) {

		News news = newsRepository.findById(id).get();

		//ukoliko vest ne postoji ili je vec odobrene
		if (news == null || news.getNewsState() == NewsState.APPROVED)
			return new ResponseEntity(news, HttpStatus.GONE);

		AuthUserDetails userDetails = (AuthUserDetails) (authentication.getPrincipal());
		Integer adminID = userDetails.getAdminID();
		news.setAdminID(adminID);
		news.setNewsState(NewsState.APPROVED);

		News savednews = newsRepository.save(news);
		return new ResponseEntity(savednews, HttpStatus.OK);

	}
	

}
