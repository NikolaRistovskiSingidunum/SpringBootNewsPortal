package com.nsa.approvedcomment.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {
 
//	@Autowired
//	private DataSource dataSource;
	
	@Autowired
	private AuthService authService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//	    auth.inMemoryAuthentication()
//	        .withUser("admin").password(encoder().encode("adminPass")).roles("ADMIN")
//	        .and()
//	        .withUser("user").password(encoder().encode("userPass")).roles("USER");
	    auth.userDetailsService(authService);
	   
	}
	
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
        .antMatchers("/admin/find/*").hasRole("ADMIN")
//        .antMatchers(HttpMethod.POST, "/comment").hasRole("ADMIN")
//        
//        
        
        //.antMatchers("/").permitAll()
        .and().formLogin()
        .and().csrf().disable();
          //...
   }
	
	@Bean
	public BCryptPasswordEncoder  encoder() {
	    return new BCryptPasswordEncoder();
		
		//return new Ps i;
	}
}


