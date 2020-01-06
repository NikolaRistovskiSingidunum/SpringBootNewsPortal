package com.nsa.approvedcomment.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.nsa.approvedcomment.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	
	Iterable<Comment> findByAdminID(Integer adminID);
	
	Iterable<Comment> findByNewsID(Integer newsID);

}
