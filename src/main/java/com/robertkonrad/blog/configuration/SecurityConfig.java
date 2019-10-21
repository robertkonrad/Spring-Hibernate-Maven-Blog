package com.robertkonrad.blog.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/resources/static/css/style.css").permitAll()
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
			.defaultSuccessUrl("/", true)
			.permitAll()
		.and()
		.logout().logoutSuccessUrl("/").permitAll();
		
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web
	            .ignoring()
	            .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**");
	}
	
}
