package com.nsa.commercial.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.nsa.commercial.model.Commercial;
import com.nsa.commercial.utils.NewsState;

public interface CommercialRepository extends JpaRepository<Commercial, Integer> {
		
	
}
