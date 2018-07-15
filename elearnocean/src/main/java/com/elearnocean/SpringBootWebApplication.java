package com.elearnocean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

//http://www.thymeleaf.org/doc/articles/layouts.html
@SpringBootApplication
public class SpringBootWebApplication extends SpringBootServletInitializer{

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SpringBootWebApplication.class, args);
	}

}