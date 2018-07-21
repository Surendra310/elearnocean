package com.elearnocean.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
// http://docs.spring.io/spring-boot/docs/current/reference/html/howto-security.html
// Switch off the Spring Boot security configuration
//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired 
	DataSource dataSource;

	@Autowired
	private AccessDeniedHandler accessDeniedHandler;

	// roles admin allow to access /admin/**
	// roles user allow to access /user/**
	// custom 403 access denied handler
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable().authorizeRequests()
				.antMatchers("/", "/login", "/index", "/question", "/generateQuestion", "/importQuestion",
						"/downloadQuestion")
				.permitAll().antMatchers("/admin/**").hasAnyRole("ADMIN").antMatchers("/user/**").hasAnyRole("USER")
				.anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll().and().logout()
				.permitAll().and().exceptionHandling().accessDeniedHandler(accessDeniedHandler);
	}

//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//
//		auth.inMemoryAuthentication().withUser("user").password("password").roles("USER").and().withUser("admin")
//				.password("password").roles("ADMIN");
//	}

	// Spring Boot configured this already.
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/dist/**",
				"/plugins/**");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
	
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource);
    }
	
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        // Load file: validation.properties
        messageSource.setBasename("classpath:validation");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
    
}
