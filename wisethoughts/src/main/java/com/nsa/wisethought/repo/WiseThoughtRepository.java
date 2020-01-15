package com.nsa.wisethought.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.nsa.wisethought.model.WiseThought;

public interface WiseThoughtRepository extends JpaRepository<WiseThought, Integer> {
		
	
}
