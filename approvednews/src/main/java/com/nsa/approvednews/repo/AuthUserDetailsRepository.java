package com.nsa.approvednews.repo;

import java.util.ArrayList;
import java.util.Collection;

//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.nsa.approvednews.model.AdminDetails;
import com.nsa.approvednews.model.AuthUserDetails;


public interface AuthUserDetailsRepository extends CrudRepository<AdminDetails, Integer> {
	
    AdminDetails findByUsername(String username);

}
