package com.robertkonrad.blog.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource securityDataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.jdbcAuthentication().dataSource(securityDataSource);
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/formUser").permitAll()
			.antMatchers("/saveUser").permitAll()
			.antMatchers("/deletePost").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
			.antMatchers("/savePost").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
			.antMatchers("/updatePost").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
			.antMatchers("/{\\d+}").permitAll()
			.anyRequest().authenticated()
		.and()
		.formLogin()
			.loginPage("/loginPage")
			.loginProcessingUrl("/authenticateUser")
			.defaultSuccessUrl("/")
			.permitAll()
		.and()
		.logout().logoutSuccessUrl("/").permitAll();
		
	}
	
}
