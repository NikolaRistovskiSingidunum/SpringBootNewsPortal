package com.nsa.demo.penningapprovalcomments.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.nsa.demo.penningapprovalcomments.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	
	Iterable<Comment> findByAdminID(Integer adminID);
	
	Iterable<Comment> findByNewsID(Integer newsID);
	
	Comment findByCommentID(Integer commentID);
	
	
//	@Query(value = "UPDATE Komentari SET admin_id=?4,text=?2,vest_id=?3 WHERE komentar_id = ?1", nativeQuery = true)
//	void updateById(Komentar kom);

}
