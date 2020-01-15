package com.nsa.wisethought;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
//@EnableJpaRepositories(basePackages={"com.nsa.demo"})
public class WiseThoughtApplication {

	public static void main(String[] args) {
		SpringApplication.run(WiseThoughtApplication.class, args);
	}

}
