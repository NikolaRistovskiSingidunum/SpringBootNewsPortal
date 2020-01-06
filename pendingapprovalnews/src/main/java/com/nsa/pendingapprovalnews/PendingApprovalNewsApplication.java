package com.nsa.pendingapprovalnews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
//@EnableJpaRepositories(basePackages={"com.nsa.demo"})
public class PendingApprovalNewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PendingApprovalNewsApplication.class, args);
	}

}
