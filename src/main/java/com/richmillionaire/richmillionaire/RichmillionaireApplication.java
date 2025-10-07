package com.richmillionaire.richmillionaire;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.richmillionaire.richmillionaire.dao")
@EntityScan(basePackages = "com.richmillionaire.richmillionaire.entity")
public class RichmillionaireApplication {

	public static void main(String[] args) {
		SpringApplication.run(RichmillionaireApplication.class, args);
	}

}
