package com.elearnocean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

//http://www.thymeleaf.org/doc/articles/layouts.html
@SpringBootApplication
public class WebApplication extends SpringBootServletInitializer{
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(WebApplication.class, args);
	}

}