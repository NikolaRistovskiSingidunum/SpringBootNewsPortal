package com.nsa.news.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.nsa.news.model.News;

public interface NewsRepository extends JpaRepository<News, Integer> {
	
//	Iterable<Comment> findByAdminID(Integer adminID);
//	
//	Iterable<Comment> findByNewsID(Integer newsID);

}
