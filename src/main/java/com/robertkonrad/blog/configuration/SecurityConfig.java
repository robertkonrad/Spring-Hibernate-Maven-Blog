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
			.antMatchers("/page/{\\d+}").permitAll()
			.antMatchers("/user/save").permitAll()
			.antMatchers("/user/form").permitAll()
			.antMatchers("/post/delete/{\\d+}").hasAnyAuthority("ADMIN", "USER")
			.antMatchers("/post/save").hasAnyAuthority("ADMIN", "USER")
			.antMatchers("/post/form").hasAnyAuthority("ADMIN", "USER")
			.antMatchers("/post/update/{\\d+}").hasAnyAuthority("ADMIN", "USER")
			.anyRequest().authenticated()
		.and()
		.formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/authenticate")
			.defaultSuccessUrl("/")
			.permitAll()
		.and()
		.logout().logoutSuccessUrl("/").permitAll();
		
	}
	
}
