package com.nsa.news.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nsa.news.model.AdminDetails;
import com.nsa.news.model.AuthUserDetails;
import com.nsa.news.repo.AuthUserDetailsRepository;

@Service
public class AuthService implements UserDetailsService {

	@Autowired
	AuthUserDetailsRepository authUserDetailsRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		AdminDetails adminDetails = authUserDetailsRepository.findByUsername(username);
		return new AuthUserDetails(adminDetails);
	}

}
